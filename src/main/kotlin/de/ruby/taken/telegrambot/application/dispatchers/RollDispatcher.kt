package de.ruby.taken.telegrambot.application.dispatchers

import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.command
import org.springframework.stereotype.Component

@Component
class RollDispatcher : CommandDispatcher {
    override fun register(dispatcher: Dispatcher) {
        dispatcher.command("roll") {
            val randomNumber = generateRandomNumber()
            val result = sendMessage(this, randomNumber)

            val response =
                generateResponseToStartingDigits(randomNumber).let {
                    if (it.isNotBlank()) "$it, congrats ${update.message?.from?.username}" else it
                }

            sendMessage(
                this,
                response,
                result.get().messageId,
            )
        }
    }

    private fun generateRandomNumber(): String =
        (1..10)
            .map { (0..9).random() }
            .joinToString("")

    private fun generateResponseToStartingDigits(randomNumber: String): String {
        val uninterruptedCount =
            randomNumber
                .takeWhile { it == randomNumber.first() }
                .length

        return when (uninterruptedCount) {
            2 -> "Dubs"
            3 -> "Trips"
            4 -> "Quads"
            5 -> "Quints"
            6 -> "Sexes"
            7 -> "Septs"
            8 -> "Octs"
            9 -> "Nons"
            10 -> "Decs"
            else -> ""
        }
    }
}
