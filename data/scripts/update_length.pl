#!/usr/bin/perl -w

use strict;
use diagnostics;
use MP3::Info qw();
use File::Glob ':glob';
use DBI;

# this script fills out lengths for audio and video works...
# it goes to the database and iterates all works that DO NOT have a length associated with
# them...
# for each such work it determines the folder where the work is and summs the length of all
# mp3s or videos in that folder writing the data back to the database using an update...

my($dbh)=DBI->connect('dbi:mysql:myworld','','',{
	RaiseError => 1,
	PrintWarn => 1,
	PrintError => 1,
	AutoCommit => 0,
});

my($debug)=0;
my($prog)=1;
# do you want to update all lengths ?
my($do_all)=0;

my($sql);
if($do_all) {
	$sql="select * from TbWkWork";
} else {
	$sql="select * from TbWkWork where length is NULL or size is NULL or chapters is NULL";
}
my($sth)=$dbh->prepare($sql);
$sth->execute() or die "SQL Error: $DBI::errstr\n";
my($rowhashref);
while($rowhashref=$sth->fetchrow_hashref()) {
	my($row_id)=$rowhashref->{'id'};
	my($name)=$rowhashref->{'name'};
	if($prog) {
		print "doing $name\n";
	}
	my($folder)="/home/mark/links/topics_archive/audio/abooks/by_title_name/$name";
	if(! -d $folder) {
		die("'$folder' is not a folder!");
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
		my($res)=MP3::Info::get_mp3info($filename);
		if($debug) {
			while(my($key,$value)=each(%$res)) {
				print "$key -> $value\n";
			}
		}
		if(exists($res->{"SECS"})) {
			$stat_secs+=$res->{"SECS"};
		} else {
			die("have not found SECS for $filename");
		}
		if(exists($res->{"SIZE"})) {
			$stat_size+=$res->{"SIZE"};
		} else {
			die("have not found SIZE for $filename");
		}
		$res=undef;
	}
	if($prog) {
		print "found size $stat_size and secs $stat_secs\n";
	}
	# now update the database...
	$dbh->do("update TbWkWork set length=? where id=?",undef,$stat_secs,$row_id);
	$dbh->do("update TbWkWork set size=? where id=?",undef,$stat_size,$row_id);
	$dbh->do("update TbWkWork set chapters=? where id=?",undef,scalar(@file_list),$row_id);
}
$dbh->commit();

$dbh->disconnect();
