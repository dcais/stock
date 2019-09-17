#!/bin/bash
sqEcho(){
   echo -e $@
}
## blue to echo
function blue(){
    sqEcho "\033[34m $@\033[0m"
}

## green to echo
function green(){
    sqEcho "\033[32m $@\033[0m"
}

## Error to warning with blink
function bred(){
    sqEcho "\033[31m\033[01m\033[05m $@\033[0m"
}

## Error to warning with blink
function byellow(){
    sqEcho "\033[33m\033[01m\033[05m $@\033[0m"
}


## Error
function red(){
    sqEcho "\033[31m\033[01m $@\033[0m"
}

## warning
function yellow(){
    sqEcho "\033[33m\033[01m $@\033[0m"
}

DOCKER_BUILD_PATH=deploy/docker

mvn -U --settings deploy/travis/settings.xml -Dmaven.javadoc.skip=true -Dsettings.security=deploy/travis/settings-security.xml -Dmaven.test.skip=true clean package

JAR_FILE=$(ls -al target/ | grep 'stock.*\.jar$'  | awk '{print $9}')
JAR_VER=$(ls -al target/ | grep 'stock.*\.jar$'  | awk '{print $9}' | sed 's/stock-//g' | sed 's/\.jar//g')

if [[ "x${JAR_FILE}" =  "x" ]]
then
    bred ======================================
    bred "[ERROR] jar file not found"
    bred ======================================
    exit 1
fi

mkdir -p ${DOCKER_BUILD_PATH}/jar
rm -fr ${DOCKER_BUILD_PATH}/jar/*.jar
cp ./target/${JAR_FILE} ${DOCKER_BUILD_PATH}/jar/stock.jar
echo "docker.jar ver=$JAR_VER" > ${DOCKER_BUILD_PATH}/jar/jar_ver

cd ${DOCKER_BUILD_PATH}

IMAGE_NAME=stock
blue "start building docker image $IMAGE_NAME"
docker build -t $IMAGE_NAME:latest .
docker push $IMAGE_NAME:latest

if [[ "x${TRAVIS_TAG}" = "x" ]]
then
  docker tag $IMAGE_NAME:latest $IMAGE_NAME:$TRAVIS_TAG
  docker push   $IMAGE_NAME:$TRAVIS_TAG
fi
