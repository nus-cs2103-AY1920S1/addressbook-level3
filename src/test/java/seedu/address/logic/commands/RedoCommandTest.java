package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstTransaction;
import static seedu.address.testutil.TypicalTransactions.getTypicalUserState;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.tab.Tab;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalUserState(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalUserState(), new UserPrefs());
    private final CommandResult expectedCommandResult =
            new CommandResult(RedoCommand.MESSAGE_SUCCESS, false, false, Tab.TRANSACTION);

    @BeforeEach
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstTransaction(model);
        deleteFirstTransaction(model);
        model.undoUserState();
        model.undoUserState();

        deleteFirstTransaction(expectedModel);
        deleteFirstTransaction(expectedModel);
        expectedModel.undoUserState();
        expectedModel.undoUserState();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoUserState();
        assertCommandSuccess(new RedoCommand(), model, expectedCommandResult, expectedModel);

        // single redoable state in model
        expectedModel.redoUserState();
        assertCommandSuccess(new RedoCommand(), model, expectedCommandResult, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_FAILURE);
    }
}
