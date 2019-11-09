package seedu.flashcard.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.flashcard.testutil.TypicalFlashcard.DAXING_AIRPORT;

import org.junit.jupiter.api.Test;

import seedu.flashcard.testutil.FlashcardBuilder;

public class McqFlashcardTest {

    @Test
    public void invalidMcqFlashcard_answerDoesNotMatchChoices_returnFalse() {
        McqFlashcard invalidMcqFlashcard = new FlashcardBuilder()
            .withChoice("Roses are red", "violets are blue", "I have five fingers")
            .withAnswer("and the middle one is for you").buildMcqFlashcard();
        assertFalse(invalidMcqFlashcard.isValidFlashcard());
    }

    @Test
    public void validMcqFlashcard_answerMatches_returnTrue() {
        assertTrue(DAXING_AIRPORT.isValidFlashcard());
    }
}
