package seedu.address.overview.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.overview.ui.OverviewMessages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.overview.ui.OverviewMessages.MESSAGE_INVALID_NUMBER_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.address.overview.logic.commands.NotifyBudgetCommand;
import seedu.address.overview.logic.commands.NotifyExpenseCommand;
import seedu.address.overview.logic.commands.NotifySalesCommand;
import seedu.address.overview.logic.commands.exception.ParseException;


public class NotifyCommandParserTest {

    @Test
    public void notifyBudgetCommand_correctInput_success() {
        try {
            assertEquals(new NotifyBudgetCommand(500), NotifyCommandParser.parse(" b/500"));
        } catch (ParseException e) {
            throw new AssertionError("This command should have passed");
        }
    }

    @Test
    public void notifyBudgetCommand_missingParams_throwParseException() {
        try {
            NotifyCommandParser.parse(" ");
            throw new AssertionError("This command should have failed");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }

    @Test
    public void notifyBudgetCommand_notNumberParams_throwParseException() {
        try {
            NotifyCommandParser.parse(" b/abcde");
            throw new AssertionError("This command should have failed");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_INVALID_NUMBER_FORMAT);
        }
    }

    @Test
    public void notifyExpenseCommand_correctInput_success() {
        try {
            assertEquals(new NotifyExpenseCommand(500), NotifyCommandParser.parse(" e/500"));
        } catch (ParseException e) {
            throw new AssertionError("This command should have passed");
        }
    }

    @Test
    public void notifyExpenseCommand_missingParams_throwParseException() {
        try {
            NotifyCommandParser.parse(" ");
            throw new AssertionError("This command should have failed");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }

    @Test
    public void notifyExpenseCommand_notNumberParams_throwParseException() {
        try {
            NotifyCommandParser.parse(" e/abcde");
            throw new AssertionError("This command should have failed");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_INVALID_NUMBER_FORMAT);
        }
    }

    @Test
    public void notifySalesCommand_correctInput_success() {
        try {
            assertEquals(new NotifySalesCommand(500), NotifyCommandParser.parse(" s/500"));
        } catch (ParseException e) {
            throw new AssertionError("This command should have passed");
        }
    }

    @Test
    public void notifySalesCommand_missingParams_throwParseException() {
        try {
            NotifyCommandParser.parse(" ");
            throw new AssertionError("This command should have failed");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }

    @Test
    public void notifySalesCommand_notNumberParams_throwParseException() {
        try {
            NotifyCommandParser.parse(" s/abcde");
            throw new AssertionError("This command should have failed");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_INVALID_NUMBER_FORMAT);
        }
    }
}
