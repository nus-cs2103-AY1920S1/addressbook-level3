package seedu.flashcard;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.flashcard.testutil.TypicalFlashcard.DAXING_AIRPORT;

import org.junit.jupiter.api.Test;

public class ValidFlashcardTest {

    @Test
    public void validMcqFlashcard() {
        assertTrue(DAXING_AIRPORT.isValidFlashcard());
    }
}
