FROM openjdk:8-jdk-alpine

# RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.ustc.edu.cn/g' /etc/apk/repositories

ENV APP_HOME=/app
ENV ARG_EXEC_TARGET_JAR=${APP_HOME}/stock.jar

ENV APP_USER=app APP_GROUP=app APP_HOME=${APP_HOME}
ENV EXEC_TARGET_JAR ${ARG_EXEC_TARGET_JAR}

RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories \
    && apk add su-exec \
    && addgroup -S -g 2015 app \
    && adduser -S -G app -h ${APP_HOME} -s /bin/sh -u 2015 app

USER app
RUN mkdir -p ${APP_HOME}/logs

COPY ./jar/stock.jar ${ARG_EXEC_TARGET_JAR}
COPY ./jar/jar_ver /${APP_HOME}/jar_ver
COPY entry-point.sh /entry-point.sh

USER root
RUN chown -R app:app $APP_HOME
ENTRYPOINT ["./entry-point.sh"]
