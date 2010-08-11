#!/bin/bash
USER='mark';
PASS='';
DB="myworld";

QUERY="SELECT table_name FROM information_schema.TABLES WHERE table_schema = '$DB';";
TABELAS=$(mysql -u $USER --password=$PASS $DB --execute="$QUERY" | sed 's/|//g' | tail -n +2)
var=0;
for tables in $TABELAS; do
	var=`expr $var + 1`
	echo "ALTER TABLE $tables ......"
	mysql -u $USER --password=$PASS $DB -e "ALTER TABLE $tables CONVERT TO CHARSET utf8"
	#mysql -u $USER --password=$PASS $DB -e "ALTER TABLE $tables CONVERT TO CHARSET utf8 COLLATE utf8_unicode_ci"
done
