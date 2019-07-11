#!/bin/bash
cd `dirname $0`

if [ $# -lt 1 ]; then
  echo "arguments $# passed" 1>&2
  echo "at lest 1 param should be passed" 1>&2
  exit 1
fi
#if [ -f "env.sh" ] 
#then
#  source env.sh
#fi

CONFIG_PATH=$1

BASE_BIN_DIR=`pwd`
DATE=`date +%Y%m%d_%H%M%S`
MAIN_CLASS="com.tqmall.river.guardian.RiverGuardian"

cd ..
CLASS_PATH=`find libs -type f | tr "\n" ":"`
java -classpath "${CLASS_PATH}" $MAIN_CLASS -c $CONFIG_PATH
