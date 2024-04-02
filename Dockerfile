FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/TelBotBank-0.0.1-SNAPSHOT.jar tel_bot_bank.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "tel_bot_bank.jar"]