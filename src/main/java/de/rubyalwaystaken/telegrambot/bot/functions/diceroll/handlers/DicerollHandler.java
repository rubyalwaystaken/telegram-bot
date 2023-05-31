package de.rubyalwaystaken.telegrambot.bot.functions.diceroll.handlers;

import de.rubyalwaystaken.telegrambot.bot.functions.FunctionHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class DicerollHandler extends FunctionHandler {

    static final int LENGTH_OF_GENERATED_NUMBER_EXCLUSIVE = 6;

    private final Map<Integer, String> possibleReplies;

    public DicerollHandler() {
        super("roll");

        this.possibleReplies = Map.of(
                0, "Loser",
                1, "Dubs, congratz",
                2, "Trips, we're getting there",
                3, "Quads, you can be proud",
                4, "Quints, almost there buddy",
                5, "Sexts, you have bragging rights for 24 hours"
        );
    }

    @Override
    public List<SendMessage> generateResponses(Update update) {
        String randomNumber = generateRandomNumber();
        SendMessage randomNumberMessage = generateMessageToSend(update, randomNumber);
        randomNumberMessage.setReplyToMessageId(update.getMessage().getMessageId());

        int repeatingOccurrencesOfFirstDigit = countRepeatingOccurrencesOfFirstDigit(randomNumber);
        String comment = generateComment(repeatingOccurrencesOfFirstDigit);
        SendMessage commentMessage = generateMessageToSend(update, comment);

        return List.of(randomNumberMessage, commentMessage);
    }

    String generateRandomNumber() {
        Random random = new Random();

        return IntStream.range(0, LENGTH_OF_GENERATED_NUMBER_EXCLUSIVE)
                .mapToObj(i -> String.valueOf(random.nextInt(10)))
                .collect(Collectors.joining());
    }

    int countRepeatingOccurrencesOfFirstDigit(String generatedNumber) {
        char firstDigit = generatedNumber.charAt(0);

        return (int) IntStream.range(1, LENGTH_OF_GENERATED_NUMBER_EXCLUSIVE)
                .takeWhile(i -> generatedNumber.charAt(i) == firstDigit)
                .count();
    }

    String generateComment(int repeatingOccurrencesOfFirstDigit) {
        return possibleReplies.getOrDefault(repeatingOccurrencesOfFirstDigit, "We should not end up here");
    }

    // TODO: Persist result per User in database
}
