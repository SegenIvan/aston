FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/Aston-1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]