#FROM ubuntu:latest
#LABEL authors="seoseunghyeon"
#
#ENTRYPOINT ["top", "-b"]

FROM openjdk:17-alpine

WORKDIR /usr/src/app

ARG JAR_PATH=./build/libs

COPY ${JAR_PATH}/community-0.0.1-SNAPSHOT.jar ${JAR_PATH}/community-0.0.1-SNAPSHOT.jar

CMD ["java","-jar","./build/libs/community-0.0.1-SNAPSHOT.jar"]