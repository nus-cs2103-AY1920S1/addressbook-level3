package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertDeleteCommandFailure;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_UNDO_SUCCESS;
import static seedu.address.logic.commands.UndoableCommand.MESSAGE_NOT_EXECUTED_BEFORE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalFridges.ALICE_FRIDGE;
import static seedu.address.testutil.TypicalIdentificationNumbers.FIRST_BODY_ID_NUM;
import static seedu.address.testutil.TypicalIdentificationNumbers.FIRST_FRIDGE_ID_NUM;
import static seedu.address.testutil.TypicalIdentificationNumbers.FIRST_WORKER_ID_NUM;
import static seedu.address.testutil.TypicalIdentificationNumbers.SECOND_BODY_ID_NUM;
import static seedu.address.testutil.TypicalIdentificationNumbers.SECOND_FRIDGE_ID_NUM;
import static seedu.address.testutil.TypicalIdentificationNumbers.SECOND_WORKER_ID_NUM;
import static seedu.address.testutil.TypicalIdentificationNumbers.THIRD_FRIDGE_ID_NUM;
import static seedu.address.testutil.TypicalUndoableCommands.TYPICAL_BODY;
import static seedu.address.testutil.TypicalUndoableCommands.TYPICAL_DELETE_COMMAND;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model.entity.fridge.FridgeStatus;
import seedu.address.model.entity.worker.Worker;
import seedu.address.model.notif.Notif;
import seedu.address.testutil.TypicalPersons;

//@@author arjavibahety
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        // Delete Body
        List<Body> bodyList = model.getFilteredBodyList();
        for (Body body : bodyList) {
            if (body.getIdNum().equals(FIRST_BODY_ID_NUM)) {
                DeleteCommand deleteBodyCommand = new DeleteCommand(
                        Index.fromZeroBased(FIRST_BODY_ID_NUM.getIdNum()), "b");
                String expectedBodyMessage = String.format(DeleteCommand.MESSAGE_DELETE_ENTITY_SUCCESS, body);

                ModelManager expectedBodyModel = new ModelManager(model.getAddressBook(), new UserPrefs());
                expectedBodyModel.deleteEntity(body);

                assertCommandSuccess(deleteBodyCommand, model, expectedBodyMessage, expectedBodyModel);
                if (!body.getFridgeId().equals(Optional.empty())) {
                    checkIsBodyRemovedFromFridge(body);
                }
                checkIsNotifRemovedFromList(body);
                break;
            }
        }


        // Delete Worker
        List<Worker> workerList = model.getFilteredWorkerList();
        for (Worker worker : workerList) {
            if (worker.getIdNum().equals(FIRST_WORKER_ID_NUM)) {
                DeleteCommand deleteWorkerCommand = new DeleteCommand(
                        Index.fromZeroBased(FIRST_WORKER_ID_NUM.getIdNum()), "w");
                String expectedWorkerMessage = String.format(DeleteCommand.MESSAGE_DELETE_ENTITY_SUCCESS, worker);

                ModelManager expectedWorkerModel = new ModelManager(model.getAddressBook(), new UserPrefs());
                expectedWorkerModel.deleteEntity(worker);

                assertCommandSuccess(deleteWorkerCommand, model, expectedWorkerMessage, expectedWorkerModel);
                break;
            }
        }

        // Delete Fridge
        List<Fridge> fridgeList = model.getFilteredFridgeList();
        for (Fridge fridge : fridgeList) {
            if (fridge.getIdNum().equals(THIRD_FRIDGE_ID_NUM)) {
                DeleteCommand deleteFridgeCommand = new DeleteCommand(
                        Index.fromZeroBased(THIRD_FRIDGE_ID_NUM.getIdNum()), "f");
                String expectedFridgeMessage = String.format(DeleteCommand.MESSAGE_DELETE_ENTITY_SUCCESS, fridge);

                ModelManager expectedFridgeModel = new ModelManager(model.getAddressBook(), new UserPrefs());
                expectedFridgeModel.deleteEntity(fridge);

                assertCommandSuccess(deleteFridgeCommand, model, expectedFridgeMessage, expectedFridgeModel);
                break;
            }
        }
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {

        // Delete Body
        IdentificationNumber outOfBoundBodyIndex = IdentificationNumber.customGenerateId("B",
                model.getFilteredEntityList("B").size() + 1);
        DeleteCommand deleteBodyCommand = new DeleteCommand(
                Index.fromZeroBased(outOfBoundBodyIndex.getIdNum()), "b");

        assertDeleteCommandFailure(deleteBodyCommand, model, Messages.MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX,
                "b");

        // Delete Worker
        IdentificationNumber outOfBoundWorkerIndex = IdentificationNumber.customGenerateId("W",
                model.getFilteredEntityList("W").size() + 1);
        DeleteCommand deleteWorkerCommand = new DeleteCommand(
                Index.fromZeroBased(outOfBoundWorkerIndex.getIdNum()), "w");

        assertDeleteCommandFailure(deleteWorkerCommand, model, Messages.MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX,
                "w");

        // Delete Fridge
        IdentificationNumber outOfBoundFridgeIndex = IdentificationNumber.customGenerateId("F",
                model.getFilteredEntityList("F").size() + 1);
        DeleteCommand deleteFridgeCommand = new DeleteCommand(
                Index.fromZeroBased(outOfBoundFridgeIndex.getIdNum()), "f");

        assertDeleteCommandFailure(deleteFridgeCommand, model, Messages.MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX,
                "f");

        // Delete Fridge when it is occupied
        IdentificationNumber aliceFridgeId = ALICE_FRIDGE.getIdNum();

        deleteFridgeCommand = new DeleteCommand(Index.fromZeroBased(aliceFridgeId.getIdNum()), "f");

        assertDeleteCommandFailure(deleteFridgeCommand, model, Messages.MESSAGE_OCCUPIED_FRIDGE_CANNOT_BE_DELETED,
                "f");
    }

    //@@author ambervoong
    @Test
    public void undo_previouslyExecuted_success() throws CommandException {
        UndoableCommand deleteCommand = TYPICAL_DELETE_COMMAND;
        Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        model.addEntity(TYPICAL_BODY);
        deleteCommand.execute(model);

        Model expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        expectedModel.addEntity(TYPICAL_BODY);
        expectedModel.addExecutedCommand(deleteCommand);

        UndoCommand undoCommand = new UndoCommand();

        String expectedMessage = String.format(MESSAGE_UNDO_SUCCESS, TYPICAL_BODY);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void undo_notExecutedBefore_undoFailureException() {
        UndoableCommand deleteCommand = TYPICAL_DELETE_COMMAND;

        Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        assertThrows(CommandException.class, MESSAGE_NOT_EXECUTED_BEFORE, () -> deleteCommand.undo(model));
    }
    //@@author

    @Test
    public void equals() {

        // BODY
        DeleteCommand deleteFirstBodyCommand = new DeleteCommand(
                Index.fromZeroBased(FIRST_BODY_ID_NUM.getIdNum()), "b");
        DeleteCommand deleteSecondBodyCommand = new DeleteCommand(
                Index.fromZeroBased(SECOND_BODY_ID_NUM.getIdNum()), "b");

        // same object -> returns true
        assertTrue(deleteFirstBodyCommand.equals(deleteFirstBodyCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(Index.fromZeroBased(FIRST_BODY_ID_NUM.getIdNum()),
                "b");
        assertTrue(deleteFirstBodyCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstBodyCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstBodyCommand.equals(null));

        // different body -> returns false
        assertFalse(deleteFirstBodyCommand.equals(deleteSecondBodyCommand));


        // WORKER
        DeleteCommand deleteFirstWorkerCommand = new DeleteCommand(
                Index.fromZeroBased(FIRST_WORKER_ID_NUM.getIdNum()), "w");
        DeleteCommand deleteSecondWorkerCommand = new DeleteCommand(
                Index.fromZeroBased(SECOND_WORKER_ID_NUM.getIdNum()), "w");

        // same object -> returns true
        assertTrue(deleteFirstWorkerCommand.equals(deleteFirstWorkerCommand));

        // same values -> returns true
        DeleteCommand deleteFirstWorkerCommandCopy =
                new DeleteCommand(Index.fromZeroBased(FIRST_WORKER_ID_NUM.getIdNum()), "w");
        assertTrue(deleteFirstWorkerCommand.equals(deleteFirstWorkerCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstWorkerCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstWorkerCommand.equals(null));

        // different worker -> returns false
        assertFalse(deleteFirstWorkerCommand.equals(deleteSecondWorkerCommand));


        // FRIDGE
        DeleteCommand deleteFirstFridgeCommand =
                new DeleteCommand(Index.fromZeroBased(FIRST_FRIDGE_ID_NUM.getIdNum()), "f");
        DeleteCommand deleteSecondFridgeCommand =
                new DeleteCommand(Index.fromZeroBased(SECOND_FRIDGE_ID_NUM.getIdNum()), "f");

        // same object -> returns true
        assertTrue(deleteFirstFridgeCommand.equals(deleteFirstFridgeCommand));

        // same values -> returns true
        DeleteCommand deleteFirstFridgeCommandCopy =
                new DeleteCommand(Index.fromZeroBased(FIRST_FRIDGE_ID_NUM.getIdNum()), "f");
        assertTrue(deleteFirstFridgeCommand.equals(deleteFirstFridgeCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstFridgeCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstFridgeCommand.equals(null));

        // different worker -> returns false
        assertFalse(deleteFirstFridgeCommand.equals(deleteSecondFridgeCommand));

    }

    /**
     * Checks if the body is removed from the fridge when the body is deleted.
     * @param body refers to the body which is deleted.
     */
    private void checkIsBodyRemovedFromFridge(Body body) {
        IdentificationNumber fridgeId = body.getFridgeId().get();
        List<Fridge> fridgeList = model.getFilteredFridgeList();
        for (Fridge fridge : fridgeList) {
            if (fridge.getIdNum().equals(fridgeId)) {
                assertEquals(fridge.getFridgeStatus(), FridgeStatus.UNOCCUPIED);
                assertEquals(fridge.getBody(), Optional.empty());
                break;
            }
        }
    }

    /**
     * Checks if the notif is removed from the notif list the body is deleted.
     * @param body refers to the body which is deleted.
     */
    private void checkIsNotifRemovedFromList(Body body) {
        List<Notif> notifList = model.getFilteredNotifList();
        ArrayList<Notif> expectedToBeDeleted = new ArrayList<>();
        for (Notif notif : notifList) {
            if (notif.getBody().equals(body)) {
                expectedToBeDeleted.add(notif);
                break;
            }
        }
        assertArrayEquals(expectedToBeDeleted.toArray(), (new ArrayList<Notif>()).toArray());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }

}
//@@author
