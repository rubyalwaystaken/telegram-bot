package de.rubyalwaystaken.telegrambot.bot.exceptions;

import org.telegram.telegrambots.meta.api.objects.Update;

public class MessageSendException extends RuntimeException {
    public MessageSendException(Throwable t, Update update) {
        super("Failed to send Message, replying to Message with ID " + update.getMessage().getMessageId(), t);
    }
}
