package de.rubyalwaystaken.telegrambot.bot.initialization;

import de.rubyalwaystaken.telegrambot.bot.exceptions.MessageSendException;
import de.rubyalwaystaken.telegrambot.bot.functions.FunctionHandler;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${BOT_TOKEN}")
    private static String botToken;

    @Value("${BOT_NAME}")
    private static String botName;

    private final Map<String, FunctionHandler> functionHandlers;

    @Autowired
    public TelegramBot(List<FunctionHandler> allFunctionHandlers) {
        super(botToken);

        this.functionHandlers = allFunctionHandlers
                .stream()
                .collect(Collectors.toMap(FunctionHandler::getCommand, Function.identity(), (a, b) -> a));
    }

    // TODO: Need to write test cases

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.getMessage().hasText()) {
            log.warn("No message supplied, aborting");
            return;
        }

        var messageText = update.getMessage().getText();
        var command = messageText.split("/")[1];

        if (!StringUtils.hasText(command)) {
            log.warn("No command supplied, aborting");
            return;
        }

        if (!functionHandlers.containsKey(command)) {
            log.warn("Command not known, aborting");
            return;
        }

        List<SendMessage> responses = functionHandlers.get(command).generateResponses(update);

        try {
            for (SendMessage response : responses) {
                execute(response);
            }
        } catch (TelegramApiException e) {
            throw new MessageSendException(e, update);
        }
    }
}
