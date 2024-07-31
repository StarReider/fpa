FROM openjdk:17-jdk-alpine
MAINTAINER StarReider
COPY target/fpa-0.0.1.jar fpa-0.0.1.jar
COPY .adaptable /.adaptable
ENTRYPOINT ["java","-jar","/fpa-0.0.1.jar"]