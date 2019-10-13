package seedu.flashcard.command;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.flashcard.model.DefaultFlashCards.KANETSU_CARD;
import static seedu.flashcard.model.DefaultFlashCards.MOUNT_BLANC_CARD;
import static seedu.flashcard.model.DefaultFlashCardList.LIST2;
import org.junit.jupiter.api.Test;
import seedu.flashcard.model.FlashcardList;
import seedu.flashcard.model.flashcard.ShortAnswerFlashcard;
import java.util.Arrays;
import java.util.List;

public class AddCommandTest {

    @Test
    public void add_card_test() {
        FlashcardList temp = new FlashcardList();
        temp.addFlashcard(KANETSU_CARD);
        temp.addFlashcard(MOUNT_BLANC_CARD);
        assertTrue(LIST2.equals(temp));
    }
}
