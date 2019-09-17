#!/bin/bash
source deploy/bin/common.sh

DOCKER_BUILD_PATH=deploy/docker

mvn -U --settings deploy/travis/settings.xml  -Dsettings.security=deploy/travis/security-settings.xml -Dmaven.test.skip=true clean package

JAR_FILE=$(ls -al target/ | grep 'stock.*\.jar$'  | awk '{print $9}')
JAR_VER=$(ls -al target/ | grep 'stock.*\.jar$'  | awk '{print $9}' | sed 's/stock-//g' | sed 's/\.jar//g')

if [[ "x${JAR_FILE}" =  "x" ]]
then
    bred ======================================
    bred "[ERROR] jar file not found"
    bred ======================================
    exit 1
fi


rm -fr ${DOCKER_BUILD_PATH}/jar/*.jar
cp ./target/${JAR_FILE} ${DOCKER_BUILD_PATH}/jar/stock.jar
echo "docker.jar ver=$JAR_VER" > ${DOCKER_BUILD_PATH}/jar/jar_ver

cd ${DOCKER_BUILD_PATH}

IMAGE_NAME=stock
blue "start building docker image $IMAGE_NAME"
docker build -t $IMAGE_NAME .
