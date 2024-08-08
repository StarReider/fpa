FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
EXPOSE 8080
COPY --from=builder /app/target/*.jar /app/*.jar
ENTRYPOINT ["java","-XshowSettings:vm","-Xlog:os+container=trace","-Dspring.profiles.active=prod","-XX:+UseContainerSupport","-XX:MaxRAMPercentage=40.0","-Djava.security.egd=file:/dev/./urandom","-jar","/app/*.jar"]