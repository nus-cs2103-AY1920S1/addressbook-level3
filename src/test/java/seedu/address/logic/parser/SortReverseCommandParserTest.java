package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortReverseCommand;

class SortReverseCommandParserTest {

    private SortReverseCommandParser parser = new SortReverseCommandParser();

    //    @Test
    //    public void parse_validArgs_returnsSortCommand() throws ParseException {
    //        assertParseSuccess(parser, "name", new SortReverseCommandParser().parse("name"));
    //    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format("Filter is not recognised",
                SortReverseCommand.MESSAGE_USAGE));
    }
}
