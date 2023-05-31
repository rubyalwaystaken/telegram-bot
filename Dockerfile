FROM eclipse-temurin:17.0.7_7-jre-alpine
WORKDIR /
COPY ["build/libs/telegrambot.jar", "."]
CMD ["java", "-jar", "telegrambot.jar"]