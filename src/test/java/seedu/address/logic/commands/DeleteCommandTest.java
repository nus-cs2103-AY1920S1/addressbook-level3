package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertDeleteCommandFailure;
import static seedu.address.testutil.TypicalBodies.ALICE;
import static seedu.address.testutil.TypicalBodies.BOB;
import static seedu.address.testutil.TypicalIdentificationNumbers.FIRST_BODY_ID_NUM;
import static seedu.address.testutil.TypicalIdentificationNumbers.FIRST_WORKER_ID_NUM;
import static seedu.address.testutil.TypicalIdentificationNumbers.SECOND_BODY_ID_NUM;
import static seedu.address.testutil.TypicalIdentificationNumbers.SECOND_WORKER_ID_NUM;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalWorkers.BENSON;
import static seedu.address.testutil.TypicalWorkers.CLARA;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.body.Body;
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
        model.addEntity(ALICE);
        List<Body> bodyList = model.getFilteredBodyList();
        for (Body body : bodyList) {
            if (body.getBodyIdNum().equals(FIRST_BODY_ID_NUM)) {
                DeleteCommand deleteBodyCommand = new DeleteCommand(
                        Index.fromZeroBased(FIRST_BODY_ID_NUM.getIdNum()), "b");

                String expectedBodyMessage = String.format(DeleteCommand.MESSAGE_DELETE_ENTITY_SUCCESS, body);

                ModelManager expectedBodyModel = new ModelManager(model.getAddressBook(), new UserPrefs());
                expectedBodyModel.deleteEntity(body);

                assertCommandSuccess(deleteBodyCommand, model, expectedBodyMessage, expectedBodyModel);
                break;
            }
        }


        // Delete Worker
        model.addEntity(CLARA);
        List<Worker> workerList = model.getFilteredWorkerList();
        for (Worker worker : workerList) {
            if (worker.getWorkerIdNum().equals(FIRST_WORKER_ID_NUM)) {
                DeleteCommand deleteWorkerCommand = new DeleteCommand(
                        Index.fromZeroBased(FIRST_WORKER_ID_NUM.getIdNum()), "w");

                String expectedWorkerMessage = String.format(DeleteCommand.MESSAGE_DELETE_ENTITY_SUCCESS, worker);

                ModelManager expectedWorkerModel = new ModelManager(model.getAddressBook(), new UserPrefs());
                expectedWorkerModel.deleteEntity(worker);

                assertCommandSuccess(deleteWorkerCommand, model, expectedWorkerMessage, expectedWorkerModel);
                break;
            }
        }

        // todo Delete Fridge
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

        // todo Delete Fridge
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

    @Test
    public void equals() {

        // BODY

        model.addEntity(ALICE);
        model.addEntity(BOB);
        DeleteCommand deleteFirstBodyCommand = new DeleteCommand(Index.fromZeroBased(FIRST_BODY_ID_NUM.getIdNum()),
                "b");
        DeleteCommand deleteSecondBodyCommand = new DeleteCommand(Index.fromZeroBased(SECOND_BODY_ID_NUM.getIdNum()),
                "b");

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

        // different person -> returns false
        assertFalse(deleteFirstBodyCommand.equals(deleteSecondBodyCommand));


        // WORKER

        model.addEntity(CLARA);
        model.addEntity(BENSON);
        DeleteCommand deleteFirstWorkerCommand =
                new DeleteCommand(Index.fromZeroBased(FIRST_WORKER_ID_NUM.getIdNum()), "w");
        DeleteCommand deleteSecondWorkerCommand =
                new DeleteCommand(Index.fromZeroBased(SECOND_WORKER_ID_NUM.getIdNum()), "w");

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

        // different person -> returns false
        assertFalse(deleteFirstWorkerCommand.equals(deleteSecondWorkerCommand));

        // todo FRIDGE

    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }

}
