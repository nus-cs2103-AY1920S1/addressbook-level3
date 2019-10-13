package seedu.flashcard.command;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.flashcard.model.DefaultFlashCardList.LIST1_EMPTY;
import static seedu.flashcard.model.DefaultFlashCardList.LIST2;
import org.junit.jupiter.api.Test;
import seedu.flashcard.model.FlashcardList;

public class DeleteCommandTest {

    @Test
    public void delete_card_test() {
        FlashcardList temp = new FlashcardList(LIST2.getAllFlashcards());
        temp.deleteFlashcard(0);
        temp.deleteFlashcard(1);
        assertTrue(LIST1_EMPTY.equals(temp));
    }
}