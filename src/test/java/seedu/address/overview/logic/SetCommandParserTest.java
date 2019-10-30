package seedu.address.overview.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.overview.ui.OverviewMessages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.overview.ui.OverviewMessages.MESSAGE_INVALID_NUMBER_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.address.overview.logic.commands.SetBudgetCommand;
import seedu.address.overview.logic.commands.SetExpenseCommand;
import seedu.address.overview.logic.commands.SetSalesCommand;
import seedu.address.overview.logic.commands.exception.ParseException;


public class SetCommandParserTest {

    @Test
    public void setBudgetCommand_correctInput_success() {
        try {
            assertEquals(new SetBudgetCommand(500), SetCommandParser.parse(" b/500"));
        } catch (ParseException e) {
            throw new AssertionError("This command should have passed");
        }
    }

    @Test
    public void setBudgetCommand_missingParams_throwParseException() {
        try {
            SetCommandParser.parse(" ");
            throw new AssertionError("This command should have failed");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }

    @Test
    public void setBudgetCommand_notNumberParams_throwParseException() {
        try {
            SetCommandParser.parse(" b/abcde");
            throw new AssertionError("This command should have failed");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_INVALID_NUMBER_FORMAT);
        }
    }

    @Test
    public void setExpenseCommand_correctInput_success() {
        try {
            assertEquals(new SetExpenseCommand(500), SetCommandParser.parse(" e/500"));
        } catch (ParseException e) {
            throw new AssertionError("This command should have passed");
        }
    }

    @Test
    public void setExpenseCommand_missingParams_throwParseException() {
        try {
            SetCommandParser.parse(" ");
            throw new AssertionError("This command should have failed");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }

    @Test
    public void setExpenseCommand_notNumberParams_throwParseException() {
        try {
            SetCommandParser.parse(" e/abcde");
            throw new AssertionError("This command should have failed");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_INVALID_NUMBER_FORMAT);
        }
    }

    @Test
    public void setSalesCommand_correctInput_success() {
        try {
            assertEquals(new SetSalesCommand(500), SetCommandParser.parse(" s/500"));
        } catch (ParseException e) {
            throw new AssertionError("This command should have passed");
        }
    }

    @Test
    public void setSalesCommand_missingParams_throwParseException() {
        try {
            SetCommandParser.parse(" ");
            throw new AssertionError("This command should have failed");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }

    @Test
    public void setSalesCommand_notNumberParams_throwParseException() {
        try {
            SetCommandParser.parse(" s/abcde");
            throw new AssertionError("This command should have failed");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_INVALID_NUMBER_FORMAT);
        }
    }
}
