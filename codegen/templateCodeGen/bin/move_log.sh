#!/bin/bash
cd `dirname $0`
BASE_BIN_DIR=`pwd`
DATE=`date +%Y%m%d`

cd ../logs
mkdir -p back

for FILE in `ls *log`
do
  TARGET_FILE=`echo ${FILE} | sed "s/\(\.log\)$/_${DATE}\1/g"`
  mv $FILE back/$TARGET_FILE
done
