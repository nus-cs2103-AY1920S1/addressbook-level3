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

public class UndoCommandTest {

    private final Model model = new ModelManager(getTypicalUserState(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalUserState(), new UserPrefs());
    private final CommandResult expectedCommandResult =
            new CommandResult(UndoCommand.MESSAGE_SUCCESS, false, false, Tab.TRANSACTION);

    @BeforeEach
    public void setUp() {
        // set up of models' undo/redo history
        deleteFirstTransaction(model);
        deleteFirstTransaction(model);

        deleteFirstTransaction(expectedModel);
        deleteFirstTransaction(expectedModel);
    }

    @Test
    public void execute() {
        // multiple undoable states in model
        expectedModel.undoUserState();
        assertCommandSuccess(new UndoCommand(), model, expectedCommandResult, expectedModel);

        // single undoable state in model
        expectedModel.undoUserState();
        assertCommandSuccess(new UndoCommand(), model, expectedCommandResult, expectedModel);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_FAILURE);
    }
}
