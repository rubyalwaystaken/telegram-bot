FROM azul/zulu-openjdk-alpine:21.0.3
COPY ../kotlin-telegram-bot-0.0.1.jar .
ENTRYPOINT ["java", "-jar", "kotlin-telegram-bot-0.0.1.jar"]