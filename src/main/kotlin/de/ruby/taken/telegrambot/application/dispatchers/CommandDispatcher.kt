package de.ruby.taken.telegrambot.application.dispatchers

import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.handlers.CommandHandlerEnvironment
import com.github.kotlintelegrambot.entities.ChatId

interface CommandDispatcher {
    fun register(dispatcher: Dispatcher)

    fun sendMessage(
        environment: CommandHandlerEnvironment,
        messageText: String,
        replyToMessageId: Long? = null,
    ) = environment.bot.sendMessage(
        chatId =
            ChatId.fromId(
                environment.update.message!!
                    .chat.id,
            ),
        text = messageText,
        replyToMessageId = replyToMessageId,
    )
}
