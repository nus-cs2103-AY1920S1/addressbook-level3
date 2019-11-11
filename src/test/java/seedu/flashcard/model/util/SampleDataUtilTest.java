package seedu.flashcard.model.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;



public class SampleDataUtilTest {
    @Test
    public void getSampleFlashcards_generateCards_success() {
        SampleDataUtil samples = new SampleDataUtil();
        assertTrue(samples.getSampleFlashcardList().getFlashcardList().size() == 20);
    }
}
