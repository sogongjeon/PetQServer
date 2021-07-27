FROM openjdk:16-jdk-alpine
ARG DEBIAN_FRONTEND=noninteractive
ENV TZ=Asia/Seoul
COPY ./petq-apiserver.jar /
WORKDIR /
ENTRYPOINT ["java","-jar","./petq-apiserver.jar"]
