package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAppointmentBook;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.queue.QueueManager;
import seedu.address.model.queue.Room;
import seedu.address.model.userprefs.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class BreakCommandTest {

    private Model model = new ModelManager(new AddressBook(), new UserPrefs(), new QueueManager(),
            getTypicalAppointmentBook());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        model.addPerson(BENSON);
        Room roomToEdit = new Room(BENSON.getReferenceId(), Optional.empty(), false);
        Room editedRoom = new Room(BENSON.getReferenceId(), Optional.empty(), true);
        model.addRoom(roomToEdit);
        BreakCommand breakCommand = new BreakCommand(roomToEdit, editedRoom, Index.fromOneBased(1));

        String expectedMessage = String.format(BreakCommand.MESSAGE_SUCCESS,
                model.resolve(editedRoom.getDoctor()).getName());

        ModelManager expectedModel = new ModelManager(new AddressBook(), new UserPrefs(), new QueueManager(),
                getTypicalAppointmentBook());
        expectedModel.addRoom(editedRoom);
        expectedModel.addPerson(BENSON);
        assertCommandSuccess(breakCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidUnfilteredList_throwsCommandException() {
        Room roomToEdit = new Room(BENSON.getReferenceId(), Optional.empty(), true);
        Room editedRoom = new Room(BENSON.getReferenceId(), Optional.empty(), true);
        BreakCommand breakCommand = new BreakCommand(roomToEdit, editedRoom, Index.fromOneBased(1));

        assertCommandFailure(breakCommand, model,
                String.format(BreakCommand.MESSAGE_ALREADY_ON_BREAK));
    }

    @Test
    public void equals() {
        Room roomToEdit = new Room(BENSON.getReferenceId(), Optional.empty(), false);
        Room editedRoom = new Room(BENSON.getReferenceId(), Optional.empty(), true);
        Room roomToEdit2 = new Room(ALICE.getReferenceId(), Optional.empty(), false);
        Room editedRoom2 = new Room(ALICE.getReferenceId(), Optional.empty(), true);
        BreakCommand breakFirstCommand = new BreakCommand(roomToEdit, editedRoom, Index.fromOneBased(1));

        // same object -> returns true
        assertTrue(breakFirstCommand.equals(breakFirstCommand));

        // same values -> returns true
        assertTrue(breakFirstCommand.equals(new BreakCommand(roomToEdit, editedRoom, Index.fromOneBased(1))));

        // different types -> returns false
        assertFalse(breakFirstCommand.equals(1));

        // null -> returns false
        assertFalse(breakFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(breakFirstCommand.equals(new BreakCommand(roomToEdit2, editedRoom2, Index.fromOneBased(1))));
    }
}
