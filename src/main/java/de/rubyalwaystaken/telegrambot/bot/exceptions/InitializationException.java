package de.rubyalwaystaken.telegrambot.bot.exceptions;

public class InitializationException extends RuntimeException {
    public InitializationException(Throwable t) {
        super("Failed to initialize Telegram Bot", t);
    }
}
