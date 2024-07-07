package de.ruby.taken.telegrambot.application

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import de.ruby.taken.telegrambot.application.dispatchers.CommandDispatcher
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class BotStartingService(
    @Value("\${TELEGRAM_BOT_TOKEN}") val botToken: String,
    val dispatchers: List<CommandDispatcher>,
) {
    @PostConstruct
    fun start() {
        val bot =
            bot {
                token = botToken
                dispatch {
                    dispatchers.forEach { it.register(this) }
                }
            }

        bot.startPolling()
    }
}
