package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstTransaction;
import static seedu.address.testutil.TypicalTransactions.getTypicalBankAccount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalBankAccount(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalBankAccount(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstTransaction(model);
        deleteFirstTransaction(model);
        model.undoBankAccount();
        model.undoBankAccount();

        deleteFirstTransaction(expectedModel);
        deleteFirstTransaction(expectedModel);
        expectedModel.undoBankAccount();
        expectedModel.undoBankAccount();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoBankAccount();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoBankAccount();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_FAILURE);
    }
}
