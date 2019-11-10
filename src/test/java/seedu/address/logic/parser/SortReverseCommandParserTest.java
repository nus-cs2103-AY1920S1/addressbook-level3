package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Filters.FIRST_FILTER;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortReverseCommand;
import seedu.address.logic.parser.exceptions.ParseException;

class SortReverseCommandParserTest {

    private SortReverseCommandParser parser = new SortReverseCommandParser();

    @Test
    public void parse_validArgs_returnsSortCommand() throws ParseException {
        assertParseSuccess(parser, "name", new SortReverseCommand(FIRST_FILTER));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format("Filter is not recognised",
                SortReverseCommand.MESSAGE_USAGE));
    }
}
