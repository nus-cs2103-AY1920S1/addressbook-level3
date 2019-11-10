//@@author wongsm7
package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.STAFF_ALICE;
import static seedu.address.testutil.TypicalPersons.STAFF_BENSON;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.queue.BreakCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.queue.Room;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class BreakCommandTest {

    private Model model = new ModelManager();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        model.addStaff(STAFF_BENSON);
        Room roomToEdit = new Room(STAFF_BENSON.getReferenceId(), Optional.empty(), false);
        Room editedRoom = new Room(STAFF_BENSON.getReferenceId(), Optional.empty(), true);
        model.addRoom(roomToEdit);
        BreakCommand breakCommand = new BreakCommand(roomToEdit, editedRoom);

        String expectedMessage = String.format(BreakCommand.MESSAGE_SUCCESS,
                model.resolveStaff(editedRoom.getDoctor()).getName());

        ModelManager expectedModel = new ModelManager();
        expectedModel.addRoom(editedRoom);
        expectedModel.addStaff(STAFF_BENSON);
        assertCommandSuccess(breakCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidUnfilteredList_throwsCommandException() {
        model.addStaff(STAFF_BENSON);
        Room roomToEdit = new Room(STAFF_BENSON.getReferenceId(), Optional.empty(), true);
        Room editedRoom = new Room(STAFF_BENSON.getReferenceId(), Optional.empty(), true);
        BreakCommand breakCommand = new BreakCommand(roomToEdit, editedRoom);

        assertCommandFailure(breakCommand, model,
                String.format(BreakCommand.MESSAGE_ALREADY_ON_BREAK));
    }

    @Test
    public void equals() {
        Room roomToEdit = new Room(STAFF_BENSON.getReferenceId(), Optional.empty(), false);
        Room editedRoom = new Room(STAFF_BENSON.getReferenceId(), Optional.empty(), true);
        Room roomToEdit2 = new Room(STAFF_ALICE.getReferenceId(), Optional.empty(), false);
        Room editedRoom2 = new Room(STAFF_ALICE.getReferenceId(), Optional.empty(), true);
        BreakCommand breakFirstCommand = new BreakCommand(roomToEdit, editedRoom);

        // same object -> returns true
        assertTrue(breakFirstCommand.equals(breakFirstCommand));

        // same values -> returns true
        assertTrue(breakFirstCommand.equals(new BreakCommand(roomToEdit, editedRoom)));

        // different types -> returns false
        assertFalse(breakFirstCommand.equals(1));

        // null -> returns false
        assertFalse(breakFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(breakFirstCommand.equals(new BreakCommand(roomToEdit2, editedRoom2)));
    }
}
