#!/usr/bin/perl -w

use strict;
use diagnostics;
use MP3::Info qw();
use Video::Info qw();
use File::Glob ':glob';
use DBI;

# this script fills out length,size and chapters for audio works...
# it goes to the database and iterates all works that DO NOT have a length associated with
# them...
# for each such work it determines the folder where the work is and summs the length of all
# mp3s, the size and number of chapters in that folder writing the data back to the database
# using an update...

my($dbh)=DBI->connect('dbi:mysql:myworld','','',{
	RaiseError => 1,
	PrintWarn => 1,
	PrintError => 1,
	AutoCommit => 0,
});

# print debug messages ?
my($debug)=0;
# print progress while working ?
my($prog)=1;
# print stats at the end ?
my($stats)=1;
# do you want to update all lengths ?
my($do_all)=0;

# TODO: write some SQL to find the next numbers and get ridd of the hardcoded values.
my($sql);
#$sql='select * from TbWkWork';
# selecting only works which are audio (selecting all as above will NOT work...)
$sql='select TbWkWork.id,TbWkWork.name,TbWkWork.typeId from TbWkWork,TbWkWorkType where TbWkWork.typeId=TbWkWorkType.id and TbWkWorkType.name in (\'audio book\',\'audio course\',\'audio show\',\'video course\')';
if($do_all) {
} else {
	$sql.=' and ( TbWkWork.length is NULL or TbWkWork.size is NULL or TbWkWork.chapters is NULL )';
}
if($debug) {
	print 'sql is ['.$sql.']'."\n";
}
my($counter)=0;
my($sth)=$dbh->prepare($sql);
$sth->execute() or die 'SQL Error: ['.$DBI::errstr.']'."\n";
my($rowhashref);
while($rowhashref=$sth->fetchrow_hashref()) {
	my($f_id)=$rowhashref->{'id'};
	my($f_name)=$rowhashref->{'name'};
	my($f_typeId)=$rowhashref->{'typeId'};
	my($type);
	if($f_typeId==2 || $f_typeId==3 || $f_typeId==5) {
		$type='audio';
	} else {
		$type='video';
	}
	if($prog) {
		print 'doing ['.$f_name.']'."\n";
	}
	my($folder);
	if($type eq 'audio') {
		$folder='/home/mark/links/topics_archive/audio/abooks/by_title_name/'.$f_name;
	} else {
		$folder='/home/mark/links/topics_archive/video/emovies/by_title_name/'.$f_name;
	}
	if(! -d $folder) {
		die('['.$folder.'] is not a folder');
	}
	# now calculate the length
	my(@file_list)=<$folder/*>;
	my($stat_secs)=0;
	my($stat_size)=0.0;
	for(my($i)=0;$i<@file_list;$i++) {
		my($filename)=$file_list[$i];
		if($debug) {
			print $filename."\n";
		}
		if($type eq 'audio') {
			my($res)=MP3::Info::get_mp3info($filename);
			if($debug) {
				while(my($key,$value)=each(%$res)) {
					print $key.' -> '.$value."\n";
				}
			}
			if(!exists($res->{'SECS'})) {
				die('have not found SECS for ['.$filename.']');
			}
			if(!exists($res->{'SIZE'})) {
				die('have not found SIZE for ['.$filename.']');
			}
			$stat_secs+=$res->{'SECS'};
			$stat_size+=$res->{'SIZE'};
		}
		if($type eq 'video') {
			my($info)=Video::Info->new(-file=>$filename);
			my($curr_secs)=$info->duration();
			my($curr_size)=$info->filesize();
			if($debug) {
				print 'curr secs is ['.$curr_secs.']'."\n";
				print 'curr size is ['.$curr_size.']'."\n";
			}
			$stat_secs+=$curr_secs;
			$stat_size+=$curr_size;
		}
	}
	my($chapters)=scalar(@file_list);
	if($prog) {
		print 'found size='.$stat_size.', secs='.$stat_secs.',chapters='.$chapters."\n";
	}
	# now update the database...
	$dbh->do('update TbWkWork set length=? where id=?',undef,$stat_secs,$f_id);
	$dbh->do('update TbWkWork set size=? where id=?',undef,$stat_size,$f_id);
	$dbh->do('update TbWkWork set chapters=? where id=?',undef,scalar(@file_list),$f_id);
	$counter++;
}
$dbh->commit();

$dbh->disconnect();

if($stats) {
	print 'did ['.$counter.'] works...'."\n";
}
