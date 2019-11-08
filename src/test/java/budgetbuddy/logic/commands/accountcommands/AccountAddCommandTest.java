package budgetbuddy.logic.commands.accountcommands;

import static budgetbuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.Model;
import budgetbuddy.model.ModelManager;
import budgetbuddy.model.account.Account;
import budgetbuddy.testutil.accountutil.AccountBuilder;
import budgetbuddy.testutil.accountutil.TypicalAccounts;

public class AccountAddCommandTest {

    private Model model = new ModelManager();

    @Test
    public void constructor_nullLoan_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AccountAddCommand(null));
    }

    @Test
    public void execute_validAccount_addSuccessful() throws CommandException {
        Account validAccount = new AccountBuilder().build();
        CommandResult commandResult = new AccountAddCommand(validAccount).execute(model);

        assertEquals(String.format(AccountAddCommand.MESSAGE_SUCCESS, validAccount), commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        Account food = TypicalAccounts.FOOD;
        Account schoolFees = TypicalAccounts.SCHOOL_FEES;
        AccountAddCommand foodAddCommand = new AccountAddCommand(food);
        AccountAddCommand schoolFeesAddCommand = new AccountAddCommand(schoolFees);

        // same object -> returns true
        assertEquals(foodAddCommand, foodAddCommand);

        // same values -> returns true
        AccountAddCommand foodAddCommandCopy = new AccountAddCommand(food);
        assertEquals(foodAddCommandCopy, foodAddCommandCopy);

        // different types -> returns false
        assertNotEquals(foodAddCommandCopy, 0);

        // null -> returns false
        assertNotEquals(foodAddCommandCopy, null);

        // different account -> returns false
        assertNotEquals(foodAddCommandCopy, schoolFeesAddCommand);
    }
}
