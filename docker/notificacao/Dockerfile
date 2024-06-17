FROM openjdk:17.0.1-jdk-slim-bullseye as BUILDER

WORKDIR /run

COPY ./notification-api ./

RUN chmod +x gradlew

EXPOSE 8080

