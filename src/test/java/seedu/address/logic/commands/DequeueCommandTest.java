//@@author wongsm7
package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.queue.DequeueCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReferenceId;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DequeueCommandTest {

    private Model model = new ModelManager();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        model.addPatient(ALICE);
        model.addPatient(BENSON);
        model.enqueuePatient(ALICE.getReferenceId());
        model.enqueuePatient(BENSON.getReferenceId());
        ReferenceId personToDelete = model.getQueueList().get(INDEX_FIRST_PERSON.getZeroBased());
        DequeueCommand dequeueCommand = new DequeueCommand(personToDelete);

        String expectedMessage = String.format(DequeueCommand.MESSAGE_DEQUEUE_SUCCESS,
                model.resolvePatient(personToDelete).getName());

        ModelManager expectedModel = new ModelManager();
        expectedModel.addPatient(ALICE);
        expectedModel.addPatient(BENSON);
        expectedModel.enqueuePatient(BENSON.getReferenceId());

        assertCommandSuccess(dequeueCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidUnfilteredList_throwsCommandException() {
        DequeueCommand dequeueCommand = new DequeueCommand(ALICE.getReferenceId());

        assertCommandFailure(dequeueCommand, model,
            String.format(DequeueCommand.MESSAGE_PERSON_NOT_IN_QUEUE, ALICE.getReferenceId()));
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
