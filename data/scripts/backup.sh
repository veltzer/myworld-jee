#!/bin/sh
#x=`date +%y%m%d%H%M%S`
OPTS=--complete-insert
mysqldump $OPTS myworld > backup/data_and_schema.sql
mysqldump $OPTS myworld --no-create-info > backup/data_only.sql
mysqldump $OPTS myworld --no-data > backup/schema_only.sql
./scripts/stats.sh > backup/stats.txt
