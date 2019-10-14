package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAppointmentBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.common.ReferenceId;
import seedu.address.model.queue.QueueManager;
import seedu.address.model.userprefs.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DequeueCommandTest {

    private Model model = new ModelManager(new AddressBook(), new UserPrefs(), new QueueManager(),
            getTypicalAppointmentBook());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        model.addPerson(ALICE);
        model.addPerson(BENSON);
        model.enqueuePatient(ALICE.getReferenceId());
        model.enqueuePatient(BENSON.getReferenceId());
        ReferenceId personToDelete = model.getQueueList().get(INDEX_FIRST_PERSON.getZeroBased());
        DequeueCommand dequeueCommand = new DequeueCommand(personToDelete);

        String expectedMessage = String.format(DequeueCommand.MESSAGE_DEQUEUE_SUCCESS, personToDelete);

        ModelManager expectedModel = new ModelManager(new AddressBook(), new UserPrefs(), new QueueManager(),
            getTypicalAppointmentBook());
        expectedModel.addPerson(ALICE);
        expectedModel.addPerson(BENSON);
        expectedModel.enqueuePatient(BENSON.getReferenceId());

        assertCommandSuccess(dequeueCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidUnfilteredList_throwsCommandException() {
        DequeueCommand dequeueCommand = new DequeueCommand(ALICE.getReferenceId());

        assertCommandFailure(dequeueCommand, model,
            String.format(DequeueCommand.MESSAGE_PERSON_NOT_IN_QUEUE, ALICE.getReferenceId()));

        model.enqueuePatient(ALICE.getReferenceId());
        assertCommandFailure(dequeueCommand, model,
                String.format(DequeueCommand.MESSAGE_DEQUEUE_PERSON_NOT_FOUND, ALICE.getReferenceId()));
    }

    @Test
    public void equals() {
        DequeueCommand dequeueFirstCommand = new DequeueCommand(ALICE.getReferenceId());

        // same object -> returns true
        assertTrue(dequeueFirstCommand.equals(dequeueFirstCommand));

        // same values -> returns true
        assertTrue(dequeueFirstCommand.equals(new DequeueCommand(ALICE.getReferenceId())));

        // different types -> returns false
        assertFalse(dequeueFirstCommand.equals(1));

        // null -> returns false
        assertFalse(dequeueFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(dequeueFirstCommand.equals(new DequeueCommand(BOB.getReferenceId())));
    }

}
