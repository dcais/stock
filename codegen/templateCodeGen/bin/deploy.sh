#!/bin/bash
cd `dirname $0`
BASE_BIN_DIR=`pwd`
DATE=`date +%Y%m%d_%H%M%S`
MANFILE="MANIFEST.MF"
JAR_NAME="templateCodeGen.jar"
MAIN_CLASS="com.hive.codegen.GenBatch"
TARGET_DIR="templateCodeGen"

cd ../../
mkdir -p target/dependency
mvn dependency:copy-dependencies -DoutputDirectory=target/dependency

cd target
rm -fr ${TARGET_DIR}
mkdir -p ${TARGET_DIR}
cd ${TARGET_DIR}
mkdir -p libs
mkdir -p config
mkdir -p logs

cp -r -p ../../docs/bin .
cp -p ../../velocity.properties .
cp -r -p ../dependency/* ./libs

cp -p ../template-code-gen*.jar ./libs

CLASSPATH=`find libs -type f -name "*.jar"`;
cat /dev/null > ${MANFILE}
echo "Main-Class: "${MAIN_CLASS} >> ${MANFILE}
echo "Manifest-Version: 1.0" >> ${MANFILE}
printf "Class-Path: " >> ${MANFILE}
for jar in ${CLASSPATH}
do
  #echo "  ./"${jar}" " | sed "s/\//\\\\\\\/g">> ${MANFILE};
  echo "  ./"${jar}" ">> ${MANFILE};
done


jar cvfm ${JAR_NAME} ${MANFILE} ./*.java
cd ..
#tar czvf riverGuardian_${MOD}_${DATE}.tgz riverGuardian
zip -9 -r templateCodeGen.zip templateCodeGen
