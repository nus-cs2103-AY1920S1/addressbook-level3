package budgetbuddy.logic.commands.accountcommands;

import static budgetbuddy.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.model.Model;
import budgetbuddy.model.ModelManager;
import budgetbuddy.model.account.Account;
import budgetbuddy.testutil.accountutil.TypicalAccounts;

public class AccountListCommandTest {

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @BeforeEach
    public void initialize() {
        model = new ModelManager();
        expectedModel = new ModelManager();
        for (Account account : TypicalAccounts.ACCOUNT_LIST) {
            model.getAccountsManager().addAccount(account);
            expectedModel.getAccountsManager().addAccount(account);
        }
    }

    @Test
    public void execute_normalList_listSuccess() {
        AccountListCommand accountListCommand = new AccountListCommand();
        String expectedMessage = AccountListCommand.MESSAGE_SUCCESS;
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, CommandCategory.ACCOUNT);
        assertCommandSuccess(accountListCommand, model, expectedCommandResult, expectedModel);
    }
}
