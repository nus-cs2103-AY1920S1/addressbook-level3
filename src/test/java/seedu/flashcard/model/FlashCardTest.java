package seedu.flashcard.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.flashcard.model.DefaultFlashCards.KANETSU_CARD;

import org.junit.jupiter.api.Test;

public class FlashCardTest {

    @Test
    public void equivalent_card_test() {
        ShortAnswerFlashcard kanestuCard = new ShortAnswerFlashcard(
                new ShortAnswerQuestion("How long is Kan-etsu Tunnel?"),
                new Answer("11055m + 10926m"));
        assertFalse(KANETSU_CARD.equals(kanestuCard));
    }


}
