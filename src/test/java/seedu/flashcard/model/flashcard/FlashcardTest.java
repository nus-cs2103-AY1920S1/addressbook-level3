package seedu.flashcard.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.flashcard.testutil.Assert.assertThrows;
import static seedu.flashcard.testutil.TypicalFlashcard.MOUNT_BLANC;

import org.junit.jupiter.api.Test;

import seedu.flashcard.testutil.FlashcardBuilder;

public class FlashcardTest {

    @Test
    public void asObservableList_modify_throwsUnsupportedOperationException() {
        Flashcard flashcard = new FlashcardBuilder().buildShortAnswerFlashcard();
        assertThrows(UnsupportedOperationException.class, () -> flashcard.getTags().remove(0));
    }

    @Test
    public void isSameFlashcard() {
        assertTrue(MOUNT_BLANC.isSameFlashcard(MOUNT_BLANC));
        assertFalse(MOUNT_BLANC.isSameFlashcard(null));

        // Different question means different flashcard.
        McqFlashcard editedMountBlanc =
            new FlashcardBuilder((McqFlashcard) MOUNT_BLANC).withQuestion("Smells good").buildMcqFlashcard();
        assertFalse(MOUNT_BLANC.isSameFlashcard(editedMountBlanc));

        // Same question means same flashcard.
        editedMountBlanc = new FlashcardBuilder((McqFlashcard) MOUNT_BLANC).withTag("Smells good").buildMcqFlashcard();
        assertTrue(MOUNT_BLANC.isSameFlashcard(editedMountBlanc));
    }

    @Test
    public void equalsFlashcard() {
        McqFlashcard mountBlancCopy = new FlashcardBuilder((McqFlashcard) MOUNT_BLANC).buildMcqFlashcard();
        assertTrue(MOUNT_BLANC.equals(mountBlancCopy));
        assertFalse(MOUNT_BLANC == null);

        Flashcard editedMountBlanc =
            new FlashcardBuilder((McqFlashcard) MOUNT_BLANC).withDefinition("Smells good").buildMcqFlashcard();
        assertFalse(MOUNT_BLANC.equals(editedMountBlanc));
    }
}
