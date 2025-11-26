FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/app.jar app.jar
COPY src/main/resources/application.properties /app/application.properties
EXPOSE 80
ENTRYPOINT ["java", "-jar", "app.jar"]
