package seedu.address.financialtracker.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import seedu.address.financialtracker.model.Model;
import seedu.address.financialtracker.model.expense.Amount;
import seedu.address.financialtracker.model.expense.Country;
import seedu.address.financialtracker.model.expense.Date;
import seedu.address.financialtracker.model.expense.Description;
import seedu.address.financialtracker.model.expense.Expense;
import seedu.address.financialtracker.model.expense.Time;
import seedu.address.financialtracker.model.expense.Type;
import seedu.address.logic.commands.CommandResult;

class ClearCommandTest {

    @Test
    public void execute_clearSuccessful() throws Exception {

        Expense ep1 = new Expense(new Date("27102016"), new Time("1720"), new Amount("4"), new Description("breakfast"),
                new Type("Food"), new Country("Singapore"));
        Model model = new Model();
        new AddFinCommand(ep1).execute(model);
        CommandResult commandResult = new ClearCommand().execute(model);
        assertEquals(ClearCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
        assertTrue(model.getExpenseList().isEmpty());
    }
}