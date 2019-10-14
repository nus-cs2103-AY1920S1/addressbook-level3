package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTransactions.getTypicalBankAccount;

import org.junit.jupiter.api.Test;

import seedu.address.model.BankAccount;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


public class ClearCommandTest {

    @Test
    public void execute_emptyBankAccount_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyBankAccount_success() {
        Model model = new ModelManager(getTypicalBankAccount(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalBankAccount(), new UserPrefs());
        expectedModel.setBankAccount(new BankAccount());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
