FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY target/app.jar app.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "app.jar"]
