package budgetbuddy.logic.commands.accountcommands;

import static budgetbuddy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static budgetbuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.Model;
import budgetbuddy.model.ModelManager;
import budgetbuddy.testutil.TypicalIndexes;
import budgetbuddy.testutil.accountutil.TypicalAccounts;

public class AccountDeleteCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void initialize() {
        model = new ModelManager();
        expectedModel = new ModelManager();

        model.getAccountsManager().addAccount(TypicalAccounts.FOOD);
    }

    @Test
    public void execute_invalidTargetIndex_throwsCommandException() throws CommandException {
        Index targetIndex = TypicalIndexes.INDEX_THIRD_ITEM;
        AccountDeleteCommand accountDeleteCommand = new AccountDeleteCommand(targetIndex);

        assertThrows(CommandException.class, () -> accountDeleteCommand.execute(model));
    }

    @Test
    public void execute_validTargetIndex_deleteSuccess() throws CommandException {
        Index targetIndex = TypicalIndexes.INDEX_SECOND_ITEM;
        AccountDeleteCommand accountDeleteCommand = new AccountDeleteCommand(targetIndex);


        String expectedMessage = String.format(
                "Deleted Account: food (For food.)");
        CommandResult expectedCommandResult =
                new CommandResult(expectedMessage, CommandCategory.ACCOUNT);

        assertCommandSuccess(accountDeleteCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() throws CommandException {
        AccountDeleteCommand firstAccountDeleteCommand = new AccountDeleteCommand(TypicalIndexes.INDEX_FIRST_ITEM);
        AccountDeleteCommand secondAccountDeleteCommand = new AccountDeleteCommand(TypicalIndexes.INDEX_SECOND_ITEM);

        // same object -> returns true
        assertEquals(firstAccountDeleteCommand, firstAccountDeleteCommand);

        // same values -> returns true
        AccountDeleteCommand firstAccountDeleteCommandCopy = new AccountDeleteCommand(TypicalIndexes.INDEX_FIRST_ITEM);
        assertEquals(firstAccountDeleteCommand, firstAccountDeleteCommandCopy);

        // different types -> returns false
        assertNotEquals(firstAccountDeleteCommand, 2);

        // null -> returns false
        assertNotEquals(firstAccountDeleteCommand, null);

        // different targets -> returns false
        assertNotEquals(firstAccountDeleteCommand, secondAccountDeleteCommand);
    }
}
