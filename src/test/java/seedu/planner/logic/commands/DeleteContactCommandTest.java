package seedu.planner.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.planner.logic.commands.CommandTestUtil.showContactAtIndex;
import static seedu.planner.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.planner.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.planner.testutil.accommodation.TypicalAccommodations.getTypicalAccommodationManager;
import static seedu.planner.testutil.activity.TypicalActivity.getTypicalActivityManager;
import static seedu.planner.testutil.contact.TypicalContacts.getTypicalContactManager;
import static seedu.planner.testutil.day.TypicalDays.getTypicalItinerary;

import org.junit.jupiter.api.Test;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.deletecommand.DeleteCommand;
import seedu.planner.logic.commands.deletecommand.DeleteContactCommand;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.ResultInformation;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.UserPrefs;
import seedu.planner.model.contact.Contact;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteContactCommandTest {

    private Model model = new ModelManager(getTypicalAccommodationManager(), getTypicalActivityManager(),
            getTypicalContactManager(), getTypicalItinerary(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Contact contactToDelete = model.getFilteredContactList().get(INDEX_FIRST.getZeroBased());
        DeleteCommand deleteCommand = new DeleteContactCommand(INDEX_FIRST, false);

        String expectedMessage = String.format(DeleteContactCommand.MESSAGE_DELETE_CONTACT_SUCCESS, contactToDelete);

        ModelManager expectedModel = new ModelManager(model.getAccommodations(), model.getActivities(),
                model.getContacts(), model.getItinerary(), new UserPrefs());
        expectedModel.deleteContact(contactToDelete);
        CommandResult expectedCommandResult = createCommandResult(expectedMessage, contactToDelete);
        assertCommandSuccess(deleteCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredContactList().size() + 1);
        DeleteCommand deleteCommand = new DeleteContactCommand(outOfBoundIndex, false);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showContactAtIndex(model, INDEX_FIRST);

        Contact contactToDelete = model.getFilteredContactList().get(INDEX_FIRST.getZeroBased());
        DeleteContactCommand deleteContactCommand = new DeleteContactCommand(INDEX_FIRST, false);

        String expectedMessage = String.format(DeleteContactCommand.MESSAGE_DELETE_CONTACT_SUCCESS, contactToDelete);

        Model expectedModel = new ModelManager(model.getAccommodations(), model.getActivities(), model.getContacts(),
                model.getItinerary(), new UserPrefs());

        expectedModel.deleteContact(contactToDelete);
        showNoContact(expectedModel);
        CommandResult expectedCommandResult = createCommandResult(expectedMessage, contactToDelete);
        assertCommandSuccess(deleteContactCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showContactAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getContacts().getContactList().size());

        DeleteCommand deleteCommand = new DeleteContactCommand(outOfBoundIndex, false);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteContactCommand deleteFirstCommand = new DeleteContactCommand(INDEX_FIRST, false);
        DeleteContactCommand deleteSecondCommand = new DeleteContactCommand(INDEX_SECOND, false);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteContactCommand deleteFirstContactCommandCopy = new DeleteContactCommand(INDEX_FIRST, false);
        assertTrue(deleteFirstCommand.equals(deleteFirstContactCommandCopy));

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

    /**
     * Creates a CommandResult for DeleteContactCommand using the {@code expectedMessage} and {@code contactToDelete}.
     */
    private CommandResult createCommandResult(String expectedMessage, Contact contactToDelete) {
        return new CommandResult(
                expectedMessage,
                new ResultInformation[]{new ResultInformation(contactToDelete, INDEX_FIRST,
                        String.format(DeleteContactCommand.MESSAGE_DELETE_CONTACT_SUCCESS, ""))},
                new UiFocus[]{UiFocus.CONTACT, UiFocus.INFO}
        );
    }
}

