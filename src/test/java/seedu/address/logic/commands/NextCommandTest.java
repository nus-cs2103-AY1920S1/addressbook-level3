//@@author wongsm7
package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReferenceId;
import seedu.address.model.queue.Room;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class NextCommandTest {

    private Model model = new ModelManager();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        model.addPatient(ALICE);
        model.enqueuePatient(ALICE.getReferenceId());
        model.addStaff(BENSON);
        model.addRoom(new Room(BENSON.getReferenceId()));
        Room roomToEdit = new Room(BENSON.getReferenceId());
        Room editedRoom = new Room(BENSON.getReferenceId(), ALICE.getReferenceId());
        ReferenceId personToServe = model.getQueueList().get(INDEX_FIRST_PERSON.getZeroBased());
        NextCommand nextCommand = new NextCommand(roomToEdit, editedRoom, Index.fromOneBased(1), personToServe);

        String expectedMessage = String.format(NextCommand.MESSAGE_SUCCESS,
                model.resolveStaff(editedRoom.getDoctor()).getName());

        ModelManager expectedModel = new ModelManager();
        expectedModel.addPatient(ALICE);
        expectedModel.addRoom(editedRoom);
        expectedModel.addStaff(BENSON);

        assertCommandSuccess(nextCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidUnfilteredList_throwsCommandException() {
        Room roomToEdit = new Room(BENSON.getReferenceId());
        Room editedRoom = new Room(BENSON.getReferenceId(), ALICE.getReferenceId());
        NextCommand nextCommand = new NextCommand(roomToEdit, editedRoom,
                Index.fromOneBased(1), ALICE.getReferenceId());

        assertCommandFailure(nextCommand, model,
                String.format(NextCommand.MESSAGE_PERSON_NOT_IN_QUEUE, ALICE.getReferenceId()));
    }

    @Test
    public void equals() {
        Room roomToEdit = new Room(BENSON.getReferenceId());
        Room editedRoom = new Room(BENSON.getReferenceId(), ALICE.getReferenceId());
        NextCommand nextFirstCommand = new NextCommand(roomToEdit, editedRoom,
                Index.fromOneBased(1), ALICE.getReferenceId());

        // same object -> returns true
        assertTrue(nextFirstCommand.equals(nextFirstCommand));

        // same values -> returns true
        assertTrue(nextFirstCommand.equals(new NextCommand(roomToEdit, editedRoom,
                Index.fromOneBased(1), ALICE.getReferenceId())));

        // different types -> returns false
        assertFalse(nextFirstCommand.equals(1));

        // null -> returns false
        assertFalse(nextFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(nextFirstCommand.equals(new NextCommand(roomToEdit, editedRoom,
                Index.fromOneBased(1), BENSON.getReferenceId())));
    }
}
