package seedu.flashcard.command;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.flashcard.model.DefaultFlashCardList.LIST1;
import static seedu.flashcard.model.DefaultFlashCardList.LIST2;

import org.junit.jupiter.api.Test;

import seedu.flashcard.model.FlashcardList;

public class DeleteCommandTest {

    @Test
    public void delete_card_test() {
        FlashcardList temp = new FlashcardList(LIST2.getAllFlashcards());
        temp.deleteFlashcard(temp.getAllFlashcards().get(0).getId().getIdentityNumber());
        assertTrue(LIST1.equals(temp));
    }
}
