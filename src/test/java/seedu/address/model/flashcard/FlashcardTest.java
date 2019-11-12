package seedu.address.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;

//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_INTELLIJ;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_ONE;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_TWO;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_ONE;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_TWO;
import static seedu.address.testutil.Assert.assertThrows;
//import static seedu.address.testutil.TypicalFlashcards.CS_ONE;
//import static seedu.address.testutil.TypicalFlashcards.MATH_ONE;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.FlashcardBuilder;

public class FlashcardTest {

    //Todo test constructors, check toString method

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Flashcard flashcard = new FlashcardBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> flashcard.getTags().remove(0));
    }

    @Test
    public void updateStatistics() {
        Flashcard flashcard = new FlashcardBuilder().build();
        flashcard.updateStatistics();
        Flashcard updatedFlashcard = new FlashcardBuilder().withStatistics(new Statistics(LocalDate.now(),
                LocalDate.now().plusDays(1), ScheduleIncrement.SECOND)).build();
        assertEquals(flashcard, updatedFlashcard);
    }

    /*
    @Test
    public void isSameFlashcard() {
        // same object -> returns true
        assertTrue(CS_ONE.isSameFlashcard(CS_ONE));

        // null -> returns false
        assertFalse(CS_ONE.isSameFlashcard(null));

        // different title and tags -> returns false
        Flashcard editedCsOne = new FlashcardBuilder(CS_ONE)
                .withTitle(VALID_TITLE_ONE).withTags(VALID_TAG_INTELLIJ).build();
        assertFalse(CS_ONE.isSameFlashcard(editedCsOne));

        // different title -> returns false
        editedCsOne = new FlashcardBuilder(CS_ONE).withTitle(VALID_TITLE_ONE).build();
        assertFalse(CS_ONE.isSameFlashcard(editedCsOne));

        // same title, different tags -> returns true
        editedCs6 = new CheatSheetBuilder(CS6)
                .withTitle(VALID_TITLE_MATH)
                .withTags(VALID_TAG_IMPORTANT).build();
        assertTrue(CS6.isSameCheatSheet(editedCs6));
    }

    @Test
    public void equals() {
        // same values -> returns true
        CheatSheet cs6Copy = new CheatSheetBuilder(CS6).build();
        assertTrue(CS6.equals(cs6Copy));

        // same object -> returns true
        assertTrue(CS6.equals(CS6));

        // null -> returns false
        assertFalse(CS6.equals(null));

        // different type -> returns false
        assertFalse(CS6.equals(5));

        // different cheatsheet -> returns false
        assertFalse(CS6.equals(CS7));

        // different title -> returns false
        CheatSheet editedCs6 = new CheatSheetBuilder(CS6).withTitle(VALID_TITLE_GEM).build();
        assertFalse(CS6.equals(editedCs6));

        // different tags -> returns false
        editedCs6 = new CheatSheetBuilder(CS6).withTags(VALID_TAG_IMPORTANT).build();
        assertFalse(CS6.equals(editedCs6));
    }
    */
}
