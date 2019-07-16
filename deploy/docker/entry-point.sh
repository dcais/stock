#!/bin/sh
java -Djava.security.egd=file:/dev/./urandom $JVM_OPTS -jar /stock.jar --spring.config.location=${CONFIG_LOCATION} ${APP_OPTS}%