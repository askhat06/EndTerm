FROM eclipse-temurin:21-jdk

LABEL maintainer="Askhat"

COPY college-backend.jar college-backend-endterm.jar

ENTRYPOINT ["java", "-jar", "college-backend-endterm.jar"]
