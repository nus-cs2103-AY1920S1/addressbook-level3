package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalActivities.getTypicalActivityBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.ActivityBook;
import seedu.address.model.InternalState;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(
            getTypicalAddressBook(), new UserPrefs(), new InternalState(), new ActivityBook());
    private Model model2 = new ModelManager(
            getTypicalAddressBook(), new UserPrefs(), new InternalState(), new ActivityBook(getTypicalActivityBook()));

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        ModelManager expectedModel = new ModelManager(
                model.getAddressBook(), new UserPrefs(), new InternalState(), new ActivityBook());

        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAY_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(
                model.getAddressBook(), new UserPrefs(), new InternalState(), new ActivityBook());

        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deletePersonOutsideActivity_success() {
        showPersonAtIndex(model2, INDEX_FIFTH);
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST);
        Person personToDelete = model2.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(
                getTypicalAddressBook(), new UserPrefs(), new InternalState(), getTypicalActivityBook());
        expectedModel.deletePerson(personToDelete);
        expectedModel.updateFilteredPersonList(x -> false);

        assertCommandSuccess(deleteCommand, model2, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deletePersonPreviouslyInActivity_success() {
        showPersonAtIndex(model2, INDEX_FIRST);
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST);
        Person personToDelete = model2.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(getTypicalAddressBook(),
                new UserPrefs(), new InternalState(), new ActivityBook(getTypicalActivityBook()));
        expectedModel.getActivityBook().getActivityList().get(0).disinvite(personToDelete);
        expectedModel.deletePerson(personToDelete);
        expectedModel.updateFilteredPersonList(x -> false);

        assertCommandSuccess(deleteCommand, model2, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deletePersonInActivity_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST);
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST);
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        String errmsg = String.format(DeleteCommand.MESSAGE_PERSON_INVOLVED_ACTIVITY,
                personToDelete.getName());

        Model tempmodel = new ModelManager(
                getTypicalAddressBook(), new UserPrefs(), new InternalState(), getTypicalActivityBook());

        assertCommandFailure(deleteCommand, tempmodel, errmsg);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAY_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST);
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
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
