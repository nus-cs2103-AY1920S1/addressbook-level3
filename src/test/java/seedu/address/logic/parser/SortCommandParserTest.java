package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.comparator.AmountComparator;
import seedu.address.logic.comparator.DateComparator;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSortCommand() {
        SortCommand expectedAmountSortCommand = new SortCommand(new AmountComparator());
        SortCommand expectedDateSortCommand = new SortCommand(new DateComparator());

        // no leading and trailing whitespaces
        assertParseSuccess(parser, "amount/a", expectedAmountSortCommand);
        assertParseSuccess(parser, "date/a", expectedDateSortCommand);

        // leading and trailing whitespaces
        assertParseSuccess(parser, "   amount/a   ", expectedAmountSortCommand);
        assertParseSuccess(parser, "   date/a   ", expectedDateSortCommand);
    }

    @Test
    public void parse_validArgs_returnsReverseSortCommand() {
        SortCommand expectedAmountSortCommand = new SortCommand(new AmountComparator().reversed());
        SortCommand expectedDateSortCommand = new SortCommand(new DateComparator().reversed());

        // no leading and trailing whitespaces
        assertParseSuccess(parser, "amount/d", expectedAmountSortCommand);
        assertParseSuccess(parser, "date/d", expectedDateSortCommand);

        // leading and trailing whitespaces
        assertParseSuccess(parser, "   amount/d   ", expectedAmountSortCommand);
        assertParseSuccess(parser, "   date/d   ", expectedDateSortCommand);
    }
}
