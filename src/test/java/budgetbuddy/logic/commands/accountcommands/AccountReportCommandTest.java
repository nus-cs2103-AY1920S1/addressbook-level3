package budgetbuddy.logic.commands.accountcommands;

import budgetbuddy.commons.core.index.Index;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.model.Model;
import budgetbuddy.model.ModelManager;
import budgetbuddy.testutil.TypicalIndexes;
import budgetbuddy.testutil.accountutil.TypicalAccounts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static budgetbuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AccountReportCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @BeforeEach
    public void initialize() {
        model = new ModelManager();
        expectedModel = new ModelManager();
        model.getAccountsManager().addAccount(TypicalAccounts.FOOD);
    }

    @Test
    public void execute_invalidTargetIndex_throwsCommandException() throws CommandException {
        Index targetIndex = TypicalIndexes.INDEX_THIRD_ITEM;
        AccountReportCommand accountReportCommand = new AccountReportCommand(targetIndex);

        assertThrows(CommandException.class, () -> accountReportCommand.execute(model));
    }

    @Test
    public void execute_validTargetIndex_reportSuccess() throws CommandException {
        Index targetIndex = TypicalIndexes.INDEX_SECOND_ITEM;
        CommandResult commandResult = new AccountReportCommand(targetIndex).execute(model);

        StringBuilder expectedMessage = new StringBuilder();
        expectedMessage.append("Report of Account food (For food.)\n")
                       .append("\n")
                       .append("Total balance: $0.00\n")
                       .append("Income: $0.00\n")
                       .append("Expenses: $0.00\n")
                       .append("Categories: []\n");
        assertEquals(expectedMessage.toString(), commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() throws CommandException {
        AccountReportCommand firstAccountReportCommand = new AccountReportCommand(TypicalIndexes.INDEX_FIRST_ITEM);
        AccountReportCommand secondAccountReportCommand = new AccountReportCommand(TypicalIndexes.INDEX_SECOND_ITEM);

        // same object -> returns true
        assertEquals(firstAccountReportCommand, firstAccountReportCommand);

        // same values -> returns true
        AccountReportCommand firstAccountReportCommandCopy = new AccountReportCommand(TypicalIndexes.INDEX_FIRST_ITEM);
        assertEquals(firstAccountReportCommand, firstAccountReportCommandCopy);

        // different types -> returns false
        assertNotEquals(firstAccountReportCommand, 2);

        // null -> returns false
        assertNotEquals(firstAccountReportCommand, null);

        // different targets -> returns false
        assertNotEquals(firstAccountReportCommand, secondAccountReportCommand);
    }
}
