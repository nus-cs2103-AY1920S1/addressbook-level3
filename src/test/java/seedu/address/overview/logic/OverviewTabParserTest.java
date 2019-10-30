package seedu.address.overview.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.overview.ui.OverviewMessages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.overview.ui.OverviewMessages.MESSAGE_INVALID_NUMBER_FORMAT;
import static seedu.address.overview.ui.OverviewMessages.MESSAGE_NO_SUCH_COMMAND;

import org.junit.jupiter.api.Test;

import seedu.address.overview.logic.commands.NotifyBudgetCommand;
import seedu.address.overview.logic.commands.NotifyExpenseCommand;
import seedu.address.overview.logic.commands.NotifySalesCommand;
import seedu.address.overview.logic.commands.SetBudgetCommand;
import seedu.address.overview.logic.commands.SetExpenseCommand;
import seedu.address.overview.logic.commands.SetSalesCommand;
import seedu.address.overview.logic.commands.exception.ParseException;

public class OverviewTabParserTest {

    private OverviewTabParser overviewTabParser = new OverviewTabParser();

    @Test
    public void setBudgetCommand_correctInput_success() {
        try {
            assertEquals(new SetBudgetCommand(500), overviewTabParser.parseCommand("set b/500"));
        } catch (ParseException e) {
            throw new AssertionError("This command should have passed");
        }
    }

    @Test
    public void setBudgetCommand_missingParams_throwParseException() {
        try {
            overviewTabParser.parseCommand("set ");
            throw new AssertionError("This command should have failed");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }

    @Test
    public void setBudgetCommand_notNumberParams_throwParseException() {
        try {
            overviewTabParser.parseCommand("set b/abcde");
            throw new AssertionError("This command should have failed");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_INVALID_NUMBER_FORMAT);
        }
    }

    @Test
    public void setExpenseCommand_correctInput_success() {
        try {
            assertEquals(new SetExpenseCommand(500), overviewTabParser.parseCommand("set e/500"));
        } catch (ParseException e) {
            throw new AssertionError("This command should have passed");
        }
    }

    @Test
    public void setExpenseCommand_missingParams_throwParseException() {
        try {
            overviewTabParser.parseCommand("set ");
            throw new AssertionError("This command should have failed");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }

    @Test
    public void setExpenseCommand_notNumberParams_throwParseException() {
        try {
            overviewTabParser.parseCommand("set e/abcde");
            throw new AssertionError("This command should have failed");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_INVALID_NUMBER_FORMAT);
        }
    }

    @Test
    public void setSalesCommand_correctInput_success() {
        try {
            assertEquals(new SetSalesCommand(500), overviewTabParser.parseCommand("set s/500"));
        } catch (ParseException e) {
            throw new AssertionError("This command should have passed");
        }
    }

    @Test
    public void setSalesCommand_missingParams_throwParseException() {
        try {
            overviewTabParser.parseCommand("set ");
            throw new AssertionError("This command should have failed");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }

    @Test
    public void setSalesCommand_notNumberParams_throwParseException() {
        try {
            overviewTabParser.parseCommand("set s/abcde");
            throw new AssertionError("This command should have failed");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_INVALID_NUMBER_FORMAT);
        }
    }

    @Test
    public void notifyBudgetCommand_correctInput_success() {
        try {
            assertEquals(new NotifyBudgetCommand(500),
                    overviewTabParser.parseCommand("notify b/500"));
        } catch (ParseException e) {
            throw new AssertionError("This command should have passed");
        }
    }

    @Test
    public void notifyBudgetCommand_missingParams_throwParseException() {
        try {
            overviewTabParser.parseCommand("notify ");
            throw new AssertionError("This command should have failed");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }

    @Test
    public void notifyBudgetCommand_notNumberParams_throwParseException() {
        try {
            overviewTabParser.parseCommand("notify b/abcde");
            throw new AssertionError("This command should have failed");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_INVALID_NUMBER_FORMAT);
        }
    }

    @Test
    public void notifyExpenseCommand_correctInput_success() {
        try {
            assertEquals(new NotifyExpenseCommand(500),
                    overviewTabParser.parseCommand("notify e/500"));
        } catch (ParseException e) {
            throw new AssertionError("This command should have passed");
        }
    }

    @Test
    public void notifyExpenseCommand_missingParams_throwParseException() {
        try {
            overviewTabParser.parseCommand("notify ");
            throw new AssertionError("This command should have failed");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }

    @Test
    public void notifyExpenseCommand_notNumberParams_throwParseException() {
        try {
            overviewTabParser.parseCommand("notify e/abcde");
            throw new AssertionError("This command should have failed");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_INVALID_NUMBER_FORMAT);
        }
    }

    @Test
    public void notifySalesCommand_correctInput_success() {
        try {
            assertEquals(new NotifySalesCommand(500), overviewTabParser.parseCommand("notify s/500"));
        } catch (ParseException e) {
            throw new AssertionError("This command should have passed");
        }
    }

    @Test
    public void notifySalesCommand_missingParams_throwParseException() {
        try {
            overviewTabParser.parseCommand("notify ");
            throw new AssertionError("This command should have failed");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }

    @Test
    public void notifySalesCommand_notNumberParams_throwParseException() {
        try {
            overviewTabParser.parseCommand("notify s/abcde");
            throw new AssertionError("This command should have failed");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_INVALID_NUMBER_FORMAT);
        }
    }

    @Test
    public void invalidCommand_notNumberParams_throwParseException() {
        try {
            overviewTabParser.parseCommand("abcde 12345 !@#$% ;'[]{}`");
            throw new AssertionError("This command should have failed");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_NO_SUCH_COMMAND);
        }
    }

}
