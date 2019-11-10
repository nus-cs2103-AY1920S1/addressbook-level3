package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.UndoableCommand.MESSAGE_NOT_EXECUTED_BEFORE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalUndoableCommands.TYPICAL_ADD_COMMAND;
import static seedu.address.testutil.TypicalWorkers.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.worker.Worker;
import seedu.address.testutil.BodyBuilder;
import seedu.address.ui.GuiUnitTest;

/**
 * Contains integration tests (interaction with the Model and UndoCommand) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest extends GuiUnitTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Body validBody = new BodyBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addEntity(validBody);

        assertCommandSuccess(new AddCommand(validBody), model,
                String.format(AddCommand.MESSAGE_SUCCESS, "body", validBody.getIdNum()), expectedModel);
    }

    @Test
    public void undo_notExecutedBefore_undoFailureException() {
        UndoableCommand addCommand = TYPICAL_ADD_COMMAND;

        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertThrows(CommandException.class, MESSAGE_NOT_EXECUTED_BEFORE, () -> addCommand.undo(model));
    }

    //@@author

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Worker workerInList = model.getAddressBook().getWorkerList().get(0);
        assertCommandFailure(new AddCommand(workerInList), model, AddCommand.MESSAGE_DUPLICATE_ENTITY);
    }

}
