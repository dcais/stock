FROM openjdk:8-jdk-alpine
ARG STOCK_VER=0.0.1-SNAPSHOT
COPY ./jar/stock-${STOCK_VER}.jar /stock.jar
COPY ./entry-point.sh /entry-point.sh
ENTRYPOINT ["./entry-point.sh"]