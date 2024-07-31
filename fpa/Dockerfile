FROM openjdk:17-jdk-alpine
MAINTAINER StarReider
COPY target/fpa-*.jar fpa.jar
COPY .adaptable /.adaptable
ENTRYPOINT ["java","-jar","/fpa.jar"]