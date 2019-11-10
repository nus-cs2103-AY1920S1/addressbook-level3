package seedu.address.financialtracker.logic.parser;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.financialtracker.logic.commands.AddFinCommand;
import seedu.address.financialtracker.logic.commands.DeleteFinCommand;
import seedu.address.financialtracker.logic.commands.EditFinCommand;
import seedu.address.financialtracker.model.expense.Amount;
import seedu.address.financialtracker.model.expense.Country;
import seedu.address.financialtracker.model.expense.Date;
import seedu.address.financialtracker.model.expense.Description;
import seedu.address.financialtracker.model.expense.Expense;
import seedu.address.financialtracker.model.expense.Time;
import seedu.address.financialtracker.model.expense.Type;
import seedu.address.logic.parser.exceptions.ParseException;

class FinancialTrackerParserTest {

    @Test
    public void parseInvalidCommand() {
        assertThrows(ParseException.class, () -> new FinancialTrackerParser().parseCommand("randomstring"));
    }

    @Test
    public void parseSuccess() throws ParseException {
        Expense ep1 = new Expense(new Date("27102016"), new Time("1720"), new Amount("4"), new Description("breakfast"),
                new Type("FOOD"));
        String userInput = "add a/4 d/breakfast t/Food date/27102016 time/1720";
        assertEquals(new AddFinCommand(ep1), new FinancialTrackerParser().parseCommand(userInput));

        String userInput2 = "delete 1";
        assertEquals(new DeleteFinCommand(Index.fromOneBased(1)),
                new FinancialTrackerParser().parseCommand(userInput2));
    }
}