package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ViewCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.ui.tab.Tab;


public class ViewCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_viewBudget_success() {
        CommandResult expectedCommandResult =
            new CommandResult(MESSAGE_SUCCESS, false, false, Tab.BUDGET);
        assertCommandSuccess(new ViewCommand(Tab.BUDGET), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_viewTransaction_success() {
        CommandResult expectedCommandResult =
            new CommandResult(MESSAGE_SUCCESS, false, false, Tab.TRANSACTION);
        assertCommandSuccess(new ViewCommand(Tab.TRANSACTION), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        ViewCommand viewTransaction = new ViewCommand(Tab.TRANSACTION);
        ViewCommand viewBudget = new ViewCommand(Tab.BUDGET);
        ViewCommand viewLedger = new ViewCommand(Tab.LEDGER);

        // same object -> returns true
        assertTrue(viewTransaction.equals(viewTransaction));
        assertTrue(viewBudget.equals(viewBudget));

        // same values -> returns true
        assertTrue(viewTransaction.equals(new ViewCommand(Tab.TRANSACTION)));

        // different types -> returns false
        assertFalse(viewTransaction.equals(viewBudget));

        // null -> returns false
        assertFalse(viewTransaction.equals(null));

        // different view -> returns false
        assertFalse(viewBudget.equals(viewLedger));
        assertFalse(viewTransaction.equals(viewBudget));
    }
}
