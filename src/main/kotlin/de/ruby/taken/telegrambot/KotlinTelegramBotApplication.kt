package de.ruby.taken.telegrambot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinTelegramBotApplication

fun main(args: Array<String>) {
    runApplication<KotlinTelegramBotApplication>(*args)
}
