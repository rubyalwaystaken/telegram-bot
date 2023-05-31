package de.rubyalwaystaken.telegrambot.bot.functions.diceroll.handlers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import(DicerollHandler.class)
class DicerollHandlerTest {

    @Autowired
    private DicerollHandler underTest;

    @Test
    @DisplayName("Random number has defined count of digits and is returned as String")
    void randomNumberReturnedAsStringAndSpecifiedLength() {
        var actualGeneratedString = underTest.generateRandomNumber();

        var expectedLength = DicerollHandler.LENGTH_OF_GENERATED_NUMBER_EXCLUSIVE;

        assertThat(actualGeneratedString).matches("^[0-9]{"+ expectedLength +"}$");
    }

    @Test
    @DisplayName("SendMessage objects are created")
    void createSendMessageObject() {
        var givenUpdate = new Update();
        var givenMessage = new Message();
        var givenChat = new Chat();
        givenChat.setId(1234L);
        givenMessage.setText("/roll");
        givenMessage.setChat(givenChat);
        givenUpdate.setMessage(givenMessage);

        var actualResponses = underTest.generateResponses(givenUpdate);

        assertThat(actualResponses).hasSize(2);
    }


    @ParameterizedTest(name = "{index} => number = {0}, count = {1}")
    @MethodSource("numberAndExpectedCountProvider")
    @DisplayName("Repeated occurrences are counted correctly")
    void repeatedOccurrencesAreCountedCorrectly(String givenGeneratedNumber, int expectedRepeatingOccurrences) {
        var actualRepeatingOccurrences = underTest.countRepeatingOccurrencesOfFirstDigit(givenGeneratedNumber);

        assertThat(actualRepeatingOccurrences).isEqualTo(expectedRepeatingOccurrences);
    }

    private static Stream<Arguments> numberAndExpectedCountProvider() {
        return Stream.of(
                Arguments.of("123456", 0),
                Arguments.of("334564", 1),
                Arguments.of("000456", 2),
                Arguments.of("111154", 3),
                Arguments.of("999994", 4),
                Arguments.of("777777", 5)
        );
    }
}