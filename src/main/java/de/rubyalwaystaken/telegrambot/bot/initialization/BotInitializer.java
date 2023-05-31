package de.rubyalwaystaken.telegrambot.bot.initialization;

import de.rubyalwaystaken.telegrambot.bot.exceptions.InitializationException;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BotInitializer {
    
    private final TelegramBot telegramBot;

    @PostConstruct
    public void initialize(){
        try {
            var telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            throw new InitializationException(e);
        }
    }
}
