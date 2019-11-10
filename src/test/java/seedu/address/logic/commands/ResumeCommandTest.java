//@@author wongsm7
package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.STAFF_BENSON;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.queue.ResumeCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.queue.Room;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class ResumeCommandTest {

    private Model model = new ModelManager();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        model.addStaff(STAFF_BENSON);
        Room roomToEdit = new Room(STAFF_BENSON.getReferenceId(), Optional.empty(), true);
        Room editedRoom = new Room(STAFF_BENSON.getReferenceId(), Optional.empty(), false);
        model.addRoom(roomToEdit);
        ResumeCommand resumeCommand = new ResumeCommand(roomToEdit, editedRoom);

        String expectedMessage = String.format(ResumeCommand.MESSAGE_SUCCESS,
                model.resolveStaff(editedRoom.getDoctor()).getName());

        ModelManager expectedModel = new ModelManager();
        expectedModel.addRoom(editedRoom);
        expectedModel.addStaff(STAFF_BENSON);
        assertCommandSuccess(resumeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidUnfilteredList_throwsCommandException() {
        Room roomToEdit = new Room(BENSON.getReferenceId(), Optional.empty(), false);
        Room editedRoom = new Room(BENSON.getReferenceId(), Optional.empty(), false);
        ResumeCommand resumeCommand = new ResumeCommand(roomToEdit, editedRoom);

        assertCommandFailure(resumeCommand, model,
                String.format(ResumeCommand.MESSAGE_NOT_ON_BREAK));
    }

    @Test
    public void equals() {
        Room roomToEdit = new Room(BENSON.getReferenceId(), Optional.empty(), true);
        Room editedRoom = new Room(BENSON.getReferenceId(), Optional.empty(), false);
        Room roomToEdit2 = new Room(ALICE.getReferenceId(), Optional.empty(), true);
        Room editedRoom2 = new Room(ALICE.getReferenceId(), Optional.empty(), false);
        ResumeCommand resumeFirstCommand = new ResumeCommand(roomToEdit, editedRoom);

        // same object -> returns true
        assertTrue(resumeFirstCommand.equals(resumeFirstCommand));

        // same values -> returns true
        assertTrue(resumeFirstCommand.equals(new ResumeCommand(roomToEdit, editedRoom)));

        // different types -> returns false
        assertFalse(resumeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(resumeFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(resumeFirstCommand.equals(new ResumeCommand(roomToEdit2, editedRoom2)));
    }
}
