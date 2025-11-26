# Dockerfile
FROM openjdk:17.0.8-jdk-slim-bullseye
WORKDIR /app

# Build jar faylini konteynerga nusxalash
COPY target/app.jar app.jar

# Spring Boot konfiguratsiyasini nusxalash
COPY src/main/resources/application.properties /app/application.properties

# Portni ochish
EXPOSE 80

# Spring Boot jar faylini ishga tushirish
ENTRYPOINT ["java", "-jar", "app.jar"]
