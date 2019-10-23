package seedu.address.logic.commands.patients;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.testutil.TestUtil;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class UnregisterPatientCommandTest {

    private Model model = TestUtil.getTypicalModelManager();

    @Test
    public void execute_validUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnregisterPatientCommand unregisterPatientCommand = new UnregisterPatientCommand(personToDelete);

        String expectedMessage = String.format(UnregisterPatientCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        ModelManager expectedModel = TestUtil.getTypicalModelManager();
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(unregisterPatientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidUnfilteredList_throwsCommandException() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.deletePerson(personToDelete);
        UnregisterPatientCommand unregisterPatientCommand = new UnregisterPatientCommand(personToDelete);

        assertCommandFailure(unregisterPatientCommand, model,
            String.format(Messages.MESSAGE_PERSON_NOT_FOUND, personToDelete));
    }

    @Test
    public void equals() {
        UnregisterPatientCommand deleteFirstCommand = new UnregisterPatientCommand(ALICE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        assertTrue(deleteFirstCommand.equals(new UnregisterPatientCommand(ALICE)));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(new UnregisterPatientCommand(BOB)));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
