package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showContactAtIndex;
import static seedu.address.testutil.TypicalContacts.getTypicalPlanner;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CONTACT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalPlanner(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Contact contactToDelete = model.getFilteredContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_CONTACT);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_CONTACT_SUCCESS, contactToDelete);

        ModelManager expectedModel = new ModelManager(model.getPlanner(), new UserPrefs());
        expectedModel.deleteContact(contactToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredContactList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showContactAtIndex(model, INDEX_FIRST_CONTACT);

        Contact contactToDelete = model.getFilteredContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_CONTACT);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_CONTACT_SUCCESS, contactToDelete);

        Model expectedModel = new ModelManager(model.getPlanner(), new UserPrefs());
        expectedModel.deleteContact(contactToDelete);
        showNoContact(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showContactAtIndex(model, INDEX_FIRST_CONTACT);

        Index outOfBoundIndex = INDEX_SECOND_CONTACT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPlanner().getContactList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_CONTACT);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_CONTACT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_CONTACT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different contacts -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoContact(Model model) {
        model.updateFilteredContactList(p -> false);

        assertTrue(model.getFilteredContactList().isEmpty());
    }
}
