package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertDeleteCommandFailure;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_UNDO_SUCCESS;
import static seedu.address.logic.commands.UndoableCommand.MESSAGE_NOT_EXECUTED_BEFORE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBodies.ALICE;
import static seedu.address.testutil.TypicalBodies.BOB;
import static seedu.address.testutil.TypicalFridges.ALICE_FRIDGE;
import static seedu.address.testutil.TypicalFridges.BOB_FRIDGE;
import static seedu.address.testutil.TypicalIdentificationNumbers.FIRST_BODY_ID_NUM;
import static seedu.address.testutil.TypicalIdentificationNumbers.FIRST_FRIDGE_ID_NUM;
import static seedu.address.testutil.TypicalIdentificationNumbers.FIRST_WORKER_ID_NUM;
import static seedu.address.testutil.TypicalIdentificationNumbers.SECOND_BODY_ID_NUM;
import static seedu.address.testutil.TypicalIdentificationNumbers.SECOND_FRIDGE_ID_NUM;
import static seedu.address.testutil.TypicalIdentificationNumbers.SECOND_WORKER_ID_NUM;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalUndoableCommands.TYPICAL_BODY;
import static seedu.address.testutil.TypicalUndoableCommands.TYPICAL_DELETE_COMMAND;
import static seedu.address.testutil.TypicalWorkers.BENSON;
import static seedu.address.testutil.TypicalWorkers.CLARA;

import java.util.List;

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
import seedu.address.model.entity.worker.Worker;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {

        // Delete Body
        // model.addEntity(ALICE);
        Body bodyToDelete = model.getFilteredBodyList().get(FIRST_BODY_ID_NUM.getIdNum());
        DeleteCommand deleteBodyCommand = new DeleteCommand(FIRST_BODY_ID_NUM);
      
        String expectedBodyMessage = String.format(DeleteCommand.MESSAGE_DELETE_ENTITY_SUCCESS, bodyToDelete);

        ModelManager expectedBodyModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedBodyModel.deleteEntity(bodyToDelete);

        assertCommandSuccess(deleteBodyCommand, model, expectedBodyMessage, expectedBodyModel);

        // Delete Worker
        // model.addEntity(CLARA);
        Worker workerToDelete = model.getFilteredWorkerList().get(FIRST_WORKER_ID_NUM.getIdNum());
        DeleteCommand deleteWorkerCommand = new DeleteCommand(FIRST_WORKER_ID_NUM);

        String expectedWorkerMessage = String.format(DeleteCommand.MESSAGE_DELETE_ENTITY_SUCCESS, workerToDelete);

        ModelManager expectedWorkerModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedWorkerModel.deleteEntity(workerToDelete);

        assertCommandSuccess(deleteWorkerCommand, model, expectedWorkerMessage, expectedWorkerModel);
        
        // Delete Fridge
       
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
    }

    /*
    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ENTITY_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEntity(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX);
    }

     */

    //@@author ambervoong
    @Test
    public void undo_previouslyExecuted_success() throws CommandException {
        UndoableCommand deleteCommand = TYPICAL_DELETE_COMMAND;
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.addEntity(TYPICAL_BODY);
        deleteCommand.execute(model);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addEntity(TYPICAL_BODY);
        expectedModel.addExecutedCommand(deleteCommand);

        UndoCommand undoCommand = new UndoCommand();

        String expectedMessage = String.format(MESSAGE_UNDO_SUCCESS, TYPICAL_BODY);
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void undo_notExecutedBefore_undoFailureException() {
        UndoableCommand deleteCommand = TYPICAL_DELETE_COMMAND;

        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertThrows(CommandException.class, MESSAGE_NOT_EXECUTED_BEFORE, () -> deleteCommand.undo(model));
    }


    //@@author

    @Test
    public void equals() {

        // BODY
        // model.addEntity(ALICE);
        // model.addEntity(BOB);
        DeleteCommand deleteFirstBodyCommand = new DeleteCommand(FIRST_BODY_ID_NUM);
        DeleteCommand deleteSecondBodyCommand = new DeleteCommand(SECOND_BODY_ID_NUM);

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
        // model.addEntity(CLARA);
        // model.addEntity(BENSON);
        DeleteCommand deleteFirstWorkerCommand = new DeleteCommand(FIRST_WORKER_ID_NUM);
        DeleteCommand deleteSecondWorkerCommand = new DeleteCommand(SECOND_WORKER_ID_NUM);

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

        // todo FRIDGE
        model.addEntity(ALICE_FRIDGE);
        model.addEntity(BOB_FRIDGE);
        DeleteCommand deleteFirstFridgeCommand =
                new DeleteCommand(Index.fromZeroBased(FIRST_FRIDGE_ID_NUM.getIdNum()), "w");
        DeleteCommand deleteSecondFridgeCommand =
                new DeleteCommand(Index.fromZeroBased(SECOND_FRIDGE_ID_NUM.getIdNum()), "w");

        // same object -> returns true
        assertTrue(deleteFirstFridgeCommand.equals(deleteFirstFridgeCommand));

        // same values -> returns true
        DeleteCommand deleteFirstFridgeCommandCopy =
                new DeleteCommand(Index.fromZeroBased(FIRST_FRIDGE_ID_NUM.getIdNum()), "w");
        assertTrue(deleteFirstFridgeCommand.equals(deleteFirstFridgeCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstFridgeCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstFridgeCommand.equals(null));

        // different worker -> returns false
        assertFalse(deleteFirstFridgeCommand.equals(deleteSecondFridgeCommand));

    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }

}
