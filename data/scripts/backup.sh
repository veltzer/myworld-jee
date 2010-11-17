#!/bin/sh
x=`date +%y%m%d%H%M%S`
DIR=~/Dropbox/db/$x
mkdir $DIR
OPTS="--complete-insert --skip-dump-date"
mysqldump $OPTS myworld > $DIR/data_and_schema.sql
mysqldump $OPTS myworld --no-create-info > $DIR/data_only.sql
mysqldump $OPTS myworld --no-data > $DIR/schema_only.sql
./scripts/stats.sh > $DIR/stats.txt
