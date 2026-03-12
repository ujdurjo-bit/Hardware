FROM eclipse-temurin:21-jdk-alpine
LABEL authors="Josip"
COPY target/Hardware-0.0.1-SNAPSHOT.jar proba.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/proba.jar"]