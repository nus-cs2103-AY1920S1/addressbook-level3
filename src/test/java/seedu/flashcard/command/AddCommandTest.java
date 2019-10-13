package seedu.flashcard.command;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.flashcard.model.DefaultFlashCardList.LIST2;
import static seedu.flashcard.model.DefaultFlashCards.KANETSU_CARD;
import static seedu.flashcard.model.DefaultFlashCards.MOUNT_BLANC_CARD;

import org.junit.jupiter.api.Test;

import seedu.flashcard.model.FlashcardList;

public class AddCommandTest {

    @Test
    public void add_card_test() {
        FlashcardList temp = new FlashcardList();
        temp.addFlashcard(KANETSU_CARD);
        temp.addFlashcard(MOUNT_BLANC_CARD);
        assertTrue(LIST2.equals(temp));
    }
}
