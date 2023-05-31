package de.rubyalwaystaken.telegrambot.bot.functions;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Data
@RequiredArgsConstructor
@Component
public abstract class FunctionHandler {
    private final String command;

    public abstract List<SendMessage> generateResponses(Update update);

    protected SendMessage generateMessageToSend(Update update, String messageToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText(messageToSend);
        return sendMessage;
    }
}
