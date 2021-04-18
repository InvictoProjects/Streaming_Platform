FROM maven:3.8-jdk-11
COPY . .
RUN mvn package -DskipTests
CMD mvn spring-boot:run
