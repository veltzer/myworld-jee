#!/usr/bin/perl -w

use strict;
use diagnostics;
use Date::Manip qw();

# this string gets string dates from the database and reinserts them as real mysql datetime
# objects...

sub unixdate_to_mysql($) {
	my($string)=@_;
	my($object)=Date::Manip::UnixDate($string,'%Y-%m-%d %T');
	return($object);
}

use DBI;
my($dbh)=DBI->connect('dbi:mysql:myworld','','',{ RaiseError => 1 }) or die "Connection Error: $DBI::errstr\n";

my($sql)="select id,viewdate from TbWkWork";
my($sth)=$dbh->prepare($sql);
$sth->execute() or die "SQL Error: $DBI::errstr\n";
my($rowhashref);
while($rowhashref=$sth->fetchrow_hashref()) {
	my($id)=$rowhashref->{'id'};
	my($viewdate)=$rowhashref->{'viewdate'};
	print "got date $viewdate\n";
	my($newdate)=unixdate_to_mysql($viewdate);
	print "newdate is $newdate\n";
	# now update the database...
	$dbh->do("update TbWkWork set viewdatesub=? where id=?",undef,$newdate,$id);
}

$dbh->disconnect();
