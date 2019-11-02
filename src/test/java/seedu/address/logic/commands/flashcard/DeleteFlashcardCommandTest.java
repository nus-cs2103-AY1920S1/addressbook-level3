package seedu.address.logic.commands.flashcard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalFlashcards.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_FLASHCARD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteFlashcardCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /* Void test. Kept as reference for format. To change assertCommandSuccess usage to use CommandResult type in third
    argument.
    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }
    */

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFlashcardList().size() + 1);
        DeleteFlashcardCommand deleteFlashcardCommand = new DeleteFlashcardCommand(outOfBoundIndex);

        assertCommandFailure(deleteFlashcardCommand, model, Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }

    /* Void test. Kept as reference for format. To change assertCommandSuccess usage to use CommandResult type in third
    argument.
    @Test
    public void execute_validIndexFilteredList_success() {
        showFlashcardAtIndex(model, INDEX_FIRST_FLASHCARD);

        Flashcard flashcardToDelete = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        DeleteFlashcardCommand deleteFlashcardCommand = new DeleteFlashcardCommand(INDEX_FIRST_FLASHCARD);

        String expectedMessage = String.format(DeleteFlashcardCommand.MESSAGE_DELETE_FLASHCARD_SUCCESS,
        flashcardToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteFlashcard(flashcardToDelete);
        showNoFlashcard(expectedModel);

        assertCommandSuccess(deleteFlashcardCommand, model, expectedMessage, expectedModel);
    }
     */

    //    @Test
    //    public void execute_invalidIndexFilteredList_throwsCommandException() {
    //        showFlashcardAtIndex(model, INDEX_FIRST_FLASHCARD);
    //
    //        Index outOfBoundIndex = INDEX_SECOND_FLASHCARD;
    //        // ensures that outOfBoundIndex is still in bounds of address book list
    //        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getFlashcardList().size());
    //
    //        DeleteFlashcardCommand deleteFlashcardCommand = new DeleteFlashcardCommand(outOfBoundIndex);
    //
    //        assertCommandFailure(deleteFlashcardCommand, model, Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    //    }


    @Test
    public void equals() {
        DeleteFlashcardCommand deleteFirstCommand = new DeleteFlashcardCommand(INDEX_FIRST_FLASHCARD);
        DeleteFlashcardCommand deleteSecondCommand = new DeleteFlashcardCommand(INDEX_SECOND_FLASHCARD);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteFlashcardCommand deleteFirstCommandCopy = new DeleteFlashcardCommand(INDEX_FIRST_FLASHCARD);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoFlashcard(Model model) {
        model.updateFilteredFlashcardList(f -> false);

        assertTrue(model.getFilteredFlashcardList().isEmpty());
    }
}
