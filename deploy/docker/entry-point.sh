#!/bin/sh
LOCAL_IP=$(hostname -i);
export LOCAL_IP

chown -R ${APP_USER}:${APP_GROUP} ${APP_HOME}
umask 0002
su-exec ${APP_USER}:${APP_GROUP} java -Djava.security.egd=file:/dev/./urandom $JVM_OPTS -jar /${EXEC_TARGET_JAR} --spring.config.location=${CONFIG_LOCATION} ${APP_OPTS}