package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.mark.testutil.TypicalIndexes.INDEX_FIRST_BOOKMARK;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.CacheCommand;

class CacheCommandParserTest {
    private CacheCommandParser parser = new CacheCommandParser();

    @Test
    public void parse_validArgs_returnsGotoCommand() {
        assertParseSuccess(parser, "1", new CacheCommand(INDEX_FIRST_BOOKMARK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CacheCommand.MESSAGE_USAGE));
    }
}
