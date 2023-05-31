FROM eclipse-temurin:17.0.7_7-jre-alpine
WORKDIR /
COPY ["telegrambot.jar", "."]
CMD ["java", "-jar", "telegrambot.jar"]