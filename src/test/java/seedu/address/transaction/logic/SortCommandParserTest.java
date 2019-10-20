package seedu.address.transaction.logic;

import static seedu.address.transaction.logic.CommandParserTestUtil.assertCommandParseFailure;
import static seedu.address.transaction.logic.CommandParserTestUtil.assertCommandParseSuccess;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_NO_SUCH_SORT_COMMAND;

import seedu.address.transaction.commands.SortAmountCommand;
import seedu.address.transaction.commands.SortDateCommand;
import seedu.address.transaction.commands.SortNameCommand;
import seedu.address.transaction.commands.SortResetCommand;

import org.junit.jupiter.api.Test;

class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_success() {
        assertCommandParseSuccess(parser, " date", new SortDateCommand());

        assertCommandParseSuccess(parser, " date    ", new SortDateCommand());

        assertCommandParseSuccess(parser, " amount", new SortAmountCommand());

        assertCommandParseSuccess(parser, " amount    ", new SortAmountCommand());

        assertCommandParseSuccess(parser, " name", new SortNameCommand());

        assertCommandParseSuccess(parser, " name    ", new SortNameCommand());

        assertCommandParseSuccess(parser, " reset", new SortResetCommand());

        assertCommandParseSuccess(parser, " reset    ", new SortResetCommand());
    }

    @Test
    public void parse_noSuchSort_failure() {
        assertCommandParseFailure(parser, "cec", MESSAGE_NO_SUCH_SORT_COMMAND);

        assertCommandParseFailure(parser, " jknkcec", MESSAGE_NO_SUCH_SORT_COMMAND);

        assertCommandParseFailure(parser, " ecwefc     ", MESSAGE_NO_SUCH_SORT_COMMAND);
    }
}
