package seedu.address.logic.commands.flashcard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFlashcardAtIndex;
import static seedu.address.testutil.TypicalFlashcards.getTypicalStudyBuddyPro;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_FLASHCARD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.flashcard.Flashcard;

public class ViewFlashcardCommandTest {
    private Model model = new ModelManager(getTypicalStudyBuddyPro(), new UserPrefs());

    @Test
    public void equals() {
        ViewFlashcardCommand firstViewCommand = new ViewFlashcardCommand(INDEX_FIRST_FLASHCARD);
        ViewFlashcardCommand secondViewCommand = new ViewFlashcardCommand(INDEX_SECOND_FLASHCARD);

        // same object -> returns true
        assertTrue(firstViewCommand.equals(firstViewCommand));

        // same values -> returns true
        ViewFlashcardCommand firstViewFlashcardCommandCopy = new ViewFlashcardCommand(INDEX_FIRST_FLASHCARD);
        assertTrue(firstViewCommand.equals(firstViewFlashcardCommandCopy));

        // different types -> returns false
        assertFalse(firstViewCommand.equals(1));

        // null -> returns false
        assertFalse(firstViewCommand.equals(null));

        // different person -> returns false
        assertFalse(firstViewCommand.equals(secondViewCommand));
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFlashcardList().size() + 1);
        ViewFlashcardCommand viewFlashcardCommand = new ViewFlashcardCommand(outOfBoundIndex);

        assertCommandFailure(viewFlashcardCommand, model, Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        Model expectedModel = new ModelManager(getTypicalStudyBuddyPro(), new UserPrefs());
//        showFlashcardAtIndex(expectedModel, INDEX_FIRST_FLASHCARD);
        Flashcard flashcardToView = expectedModel.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        ViewFlashcardCommand viewFlashcardCommand = new ViewFlashcardCommand(INDEX_FIRST_FLASHCARD);

        String expectedMessage = String.format(ViewFlashcardCommand.VIEW_FLASHCARD_SUCCESS, flashcardToView);

        assertCommandSuccess(viewFlashcardCommand, model, expectedMessage, expectedModel);
    }
}
