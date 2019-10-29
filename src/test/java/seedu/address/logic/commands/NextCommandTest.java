package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAppointmentBook;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.common.ReferenceId;
import seedu.address.model.queue.QueueManager;
import seedu.address.model.queue.Room;
import seedu.address.model.userprefs.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class NextCommandTest {

    private Model model = new ModelManager(new AddressBook(), new UserPrefs(), new QueueManager(),
            getTypicalAppointmentBook());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        model.addPerson(ALICE);
        model.addPerson(BENSON);
        model.enqueuePatient(ALICE.getReferenceId());
        model.addRoom(new Room(BENSON.getReferenceId()));
        Room roomToEdit = new Room(BENSON.getReferenceId());
        Room editedRoom = new Room(BENSON.getReferenceId(), Optional.of(ALICE.getReferenceId()));
        ReferenceId personToServe = model.getQueueList().get(INDEX_FIRST_PERSON.getZeroBased());
        NextCommand nextCommand = new NextCommand(roomToEdit, editedRoom, Index.fromOneBased(1), personToServe);

        String expectedMessage = String.format(NextCommand.MESSAGE_SUCCESS, personToServe);

        ModelManager expectedModel = new ModelManager(new AddressBook(), new UserPrefs(), new QueueManager(),
                getTypicalAppointmentBook());
        expectedModel.addPerson(ALICE);
        expectedModel.addPerson(BENSON);
        expectedModel.addRoom(editedRoom);

        assertCommandSuccess(nextCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidUnfilteredList_throwsCommandException() {
        Room roomToEdit = new Room(BENSON.getReferenceId());
        Room editedRoom = new Room(BENSON.getReferenceId(), Optional.of(ALICE.getReferenceId()));
        NextCommand nextCommand = new NextCommand(roomToEdit, editedRoom,
                Index.fromOneBased(1), ALICE.getReferenceId());

        assertCommandFailure(nextCommand, model,
                String.format(NextCommand.MESSAGE_PERSON_NOT_IN_QUEUE, ALICE.getReferenceId()));
    }

    @Test
    public void equals() {
        Room roomToEdit = new Room(BENSON.getReferenceId());
        Room editedRoom = new Room(BENSON.getReferenceId(), Optional.of(ALICE.getReferenceId()));
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
