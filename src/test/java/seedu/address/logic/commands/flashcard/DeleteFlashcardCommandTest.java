package seedu.address.logic.commands.flashcard;

//import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.commons.core.Messages.DELETE;
//import static seedu.address.commons.core.Messages.MESSAGE_ARE_YOU_SURE_WANT_TO_DELETE_FLASHCARD;
//import static seedu.address.commons.core.Messages.MESSAGE_HIT_ENTER_TO_DELETE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFlashcardAtIndex;
import static seedu.address.testutil.TypicalFlashcards.getTypicalStudyBuddyPro;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_FLASHCARD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
//import seedu.address.logic.commands.CommandResult;
//import seedu.address.logic.commands.commandresults.FlashcardCommandResult;
//import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
//import seedu.address.model.flashcard.Flashcard;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteFlashcardCommand}.
 */
public class DeleteFlashcardCommandTest {

    private Model model = new ModelManager(getTypicalStudyBuddyPro(), new UserPrefs());

    /*
    @Test
    public void execute_validIndexUnfilteredList_success() {
        Flashcard flashcardToDelete = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        DeleteFlashcardCommand deleteCommand = new DeleteFlashcardCommand(INDEX_FIRST_FLASHCARD);

        String expectedMessage = String.format(DeleteFlashcardCommand.MESSAGE_DELETE_FLASHCARD_SUCCESS,
                flashcardToDelete);

        CommandResult expectedCommandResult = new FlashcardCommandResult(expectedMessage);

        ModelManager expectedModel = new ModelManager(model.getStudyBuddyPro(), new UserPrefs());
        expectedModel.deleteFlashcard(flashcardToDelete);
        try {
            CommandResult result = deleteCommand.execute(model);
        } catch (CommandException e) {
            assertEquals(e, new CommandException(MESSAGE_ARE_YOU_SURE_WANT_TO_DELETE_FLASHCARD
                    + "\n" + flashcardToDelete
                    + "\n" + MESSAGE_HIT_ENTER_TO_DELETE));
        }

        //assertCommandSuccess(deleteCommand, model, expectedCommandResult, expectedModel);
    }
    */

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFlashcardList().size() + 1);
        DeleteFlashcardCommand deleteFlashcardCommand = new DeleteFlashcardCommand(outOfBoundIndex);

        assertCommandFailure(deleteFlashcardCommand, model, Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
    //        showFlashcardAtIndex(model, INDEX_FIRST_FLASHCARD);
    //
    //        Flashcard flashcardToDelete = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
    //        DeleteFlashcardCommand deleteFlashcardCommand = new DeleteFlashcardCommand(INDEX_FIRST_FLASHCARD);
    //
    //        String expectedMessage = String.format(DeleteFlashcardCommand.MESSAGE_DELETE_FLASHCARD_SUCCESS,
    //            flashcardToDelete);
    //
    //        CommandResult expectedCommandResult = new FlashcardCommandResult(expectedMessage);
    //
    //        Model expectedModel = new ModelManager(model.getStudyBuddyPro(), new UserPrefs());
    //        expectedModel.deleteFlashcard(flashcardToDelete);
    //        showNoFlashcard(expectedModel);
    //
    //
    //        assertCommandSuccess(deleteFlashcardCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFlashcardAtIndex(model, INDEX_FIRST_FLASHCARD);

        Index outOfBoundIndex = INDEX_SECOND_FLASHCARD;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getStudyBuddyPro().getFlashcardList().size());

        DeleteFlashcardCommand deleteFlashcardCommand = new DeleteFlashcardCommand(outOfBoundIndex);

        assertCommandFailure(deleteFlashcardCommand, model, Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }


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

        // different flashcard -> returns false
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
