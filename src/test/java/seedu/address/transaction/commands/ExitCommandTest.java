package seedu.address.transaction.commands;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.transaction.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.ModelManager;

import org.junit.jupiter.api.Test;

class ExitCommandTest {
    private Model model = new ModelManager(TypicalTransactions.getTypicalTransactionList());
    private seedu.address.person.model.Model personModel = new seedu.address.person.model.ModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, true);
        Model expectedModel = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel, personModel);
    }
}