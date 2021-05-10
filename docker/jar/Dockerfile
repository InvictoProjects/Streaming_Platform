FROM maven:3.8-jdk-11 AS builder
COPY . .
RUN mvn package

FROM openjdk:11-jdk
COPY --from=builder /target/streamingPlatform-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
