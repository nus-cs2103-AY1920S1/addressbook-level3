package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPerformance.getTypicalPerformance;
import static seedu.address.testutil.TypicalPersons.getTypicalAthletick;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TrainingManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.history.HistoryManager;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeletePersonCommandTest {

    private Model model = new ModelManager(getTypicalAthletick(), getTypicalPerformance(),
            new TrainingManager(), new UserPrefs(), new HistoryManager());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeletePersonCommand deletePersonCommand = new DeletePersonCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeletePersonCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        ModelManager expectedModel = new ModelManager(model.getAthletick(), model.getPerformance(),
                model.getTrainingManager(), new UserPrefs(), model.getHistory());

        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deletePersonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeletePersonCommand deletePersonCommand = new DeletePersonCommand(outOfBoundIndex);

        assertCommandFailure(deletePersonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeletePersonCommand deletePersonCommand = new DeletePersonCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeletePersonCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getAthletick(), model.getPerformance(),
                model.getTrainingManager(), new UserPrefs(), model.getHistory());

        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deletePersonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAthletick().getPersonList().size());

        DeletePersonCommand deletePersonCommand = new DeletePersonCommand(outOfBoundIndex);

        assertCommandFailure(deletePersonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeletePersonCommand deletePersonFirstCommand = new DeletePersonCommand(INDEX_FIRST_PERSON);
        DeletePersonCommand deletePersonSecondCommand = new DeletePersonCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deletePersonFirstCommand.equals(deletePersonFirstCommand));

        // same values -> returns true
        DeletePersonCommand deleteFirstCommandCopy = new DeletePersonCommand(INDEX_FIRST_PERSON);
        assertTrue(deletePersonFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deletePersonFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deletePersonFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deletePersonFirstCommand.equals(deletePersonSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
