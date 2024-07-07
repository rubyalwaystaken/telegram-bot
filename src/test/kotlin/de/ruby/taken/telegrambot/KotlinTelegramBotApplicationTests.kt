package de.ruby.taken.telegrambot

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(properties = ["TELEGRAM_BOT_TOKEN=sometoken"])
class KotlinTelegramBotApplicationTests {
    @Test
    fun contextLoads() {
    }
}
