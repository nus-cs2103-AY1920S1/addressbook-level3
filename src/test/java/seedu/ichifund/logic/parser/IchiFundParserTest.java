package seedu.ichifund.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ichifund.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.ichifund.testutil.Assert.assertThrows;
import static seedu.ichifund.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.ichifund.logic.commands.ClearCommand;
import seedu.ichifund.logic.commands.ExitCommand;
import seedu.ichifund.logic.commands.HelpCommand;
import seedu.ichifund.logic.commands.analytics.ExpenditureTrendCommand;
import seedu.ichifund.logic.commands.budget.DeleteBudgetCommand;
import seedu.ichifund.logic.commands.repeater.DeleteRepeaterCommand;
import seedu.ichifund.logic.commands.transaction.DeleteTransactionCommand;
import seedu.ichifund.logic.commands.transaction.EditTransactionCommand;
import seedu.ichifund.logic.parser.analytics.AnalyticsFeatureParser;
import seedu.ichifund.logic.parser.budget.BudgetFeatureParser;
import seedu.ichifund.logic.parser.exceptions.ParseException;
import seedu.ichifund.logic.parser.loan.LoanFeatureParser;
import seedu.ichifund.logic.parser.repeater.RepeaterFeatureParser;
import seedu.ichifund.logic.parser.transaction.TransactionFeatureParser;

public class IchiFundParserTest {

    private final IchiFundParser parser = new IchiFundParser();

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_tabSwitch() throws Exception {
        parser.parseCommand(new RepeaterFeatureParser().getTabSwitchCommandWord());
        assertEquals(parser.getCurrentFeatureParserIndex().getValue(), new RepeaterFeatureParser().getTabIndex());

        parser.parseCommand(new BudgetFeatureParser().getTabSwitchCommandWord());
        assertEquals(parser.getCurrentFeatureParserIndex().getValue(), new BudgetFeatureParser().getTabIndex());

        parser.parseCommand(new LoanFeatureParser().getTabSwitchCommandWord());
        assertEquals(parser.getCurrentFeatureParserIndex().getValue(), new LoanFeatureParser().getTabIndex());

        parser.parseCommand(new AnalyticsFeatureParser().getTabSwitchCommandWord());
        assertEquals(parser.getCurrentFeatureParserIndex().getValue(), new AnalyticsFeatureParser().getTabIndex());

        parser.parseCommand(new TransactionFeatureParser().getTabSwitchCommandWord());
        assertEquals(parser.getCurrentFeatureParserIndex().getValue(), new TransactionFeatureParser().getTabIndex());
    }

    @Test
    public void parseCommand_validFeatureCommand() throws Exception {
        parser.parseCommand(new TransactionFeatureParser().getTabSwitchCommandWord());
        assertEquals(parser.parseCommand(DeleteTransactionCommand.COMMAND_WORD + " "
                        + INDEX_FIRST.getOneBased()), new DeleteTransactionCommand(INDEX_FIRST));

        parser.parseCommand(new RepeaterFeatureParser().getTabSwitchCommandWord());
        assertEquals(parser.parseCommand(DeleteRepeaterCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased()), new DeleteRepeaterCommand(INDEX_FIRST));

        parser.parseCommand(new BudgetFeatureParser().getTabSwitchCommandWord());
        assertEquals(parser.parseCommand(DeleteBudgetCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased()), new DeleteBudgetCommand(INDEX_FIRST));

        parser.parseCommand(new AnalyticsFeatureParser().getTabSwitchCommandWord());
        assertTrue(parser.parseCommand(ExpenditureTrendCommand.COMMAND_WORD) instanceof ExpenditureTrendCommand);
    }

    @Test
    public void parseCommand_unknownFeatureCommand() throws Exception {
        parser.parseCommand(new TransactionFeatureParser().getTabSwitchCommandWord());
        assertThrows(ParseException.class, String.format(MESSAGE_UNKNOWN_COMMAND), ()
                -> parser.parseCommand(ExpenditureTrendCommand.COMMAND_WORD));

        parser.parseCommand(new RepeaterFeatureParser().getTabSwitchCommandWord());
        assertThrows(ParseException.class, String.format(MESSAGE_UNKNOWN_COMMAND), ()
                -> parser.parseCommand(ExpenditureTrendCommand.COMMAND_WORD));

        parser.parseCommand(new BudgetFeatureParser().getTabSwitchCommandWord());
        assertThrows(ParseException.class, String.format(MESSAGE_UNKNOWN_COMMAND), ()
                -> parser.parseCommand(ExpenditureTrendCommand.COMMAND_WORD));

        parser.parseCommand(new LoanFeatureParser().getTabSwitchCommandWord());
        assertThrows(ParseException.class, String.format(MESSAGE_UNKNOWN_COMMAND), ()
                -> parser.parseCommand(ExpenditureTrendCommand.COMMAND_WORD));

        parser.parseCommand(new AnalyticsFeatureParser().getTabSwitchCommandWord());
        assertThrows(ParseException.class, String.format(MESSAGE_UNKNOWN_COMMAND), ()
                -> parser.parseCommand(EditTransactionCommand.COMMAND_WORD));
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }
}
