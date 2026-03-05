FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

RUN mkdir -p /app/logs

COPY target/bank-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]