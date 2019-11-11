package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewCommand;
import seedu.address.ui.tab.Tab;

public class ViewCommandParserTest {
    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // invalid
        assertParseFailure(parser, "invalid",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsViewCommand() {
        ViewCommand viewTransaction = new ViewCommand(Tab.TRANSACTION);
        ViewCommand viewBudget = new ViewCommand(Tab.BUDGET);
        ViewCommand viewLedger = new ViewCommand(Tab.LEDGER);
        ViewCommand viewProjection = new ViewCommand(Tab.PROJECTION);

        // no leading and trailing whitespaces
        assertParseSuccess(parser, "transaction", viewTransaction);
        assertParseSuccess(parser, "budget", viewBudget);
        assertParseSuccess(parser, "ledger", viewLedger);
        assertParseSuccess(parser, "projection", viewProjection);

        // leading and trailing whitespaces
        assertParseSuccess(parser, "   transaction   ", viewTransaction);
        assertParseSuccess(parser, "   budget      ", viewBudget);
        assertParseSuccess(parser, "        ledger         ", viewLedger);
        assertParseSuccess(parser, "     projection       ", viewProjection);

        // upper and lower case
        assertParseSuccess(parser, "TrAnSaCtIoN", viewTransaction);
        assertParseSuccess(parser, "bUdGeT", viewBudget);
        assertParseSuccess(parser, "   lEdGer   ", viewLedger);
        assertParseSuccess(parser, "   PrOjEcTiOn        ", viewProjection);
    }
}
