package seedu.weme.logic.parser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.weme.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.weme.testutil.TypicalIndexes.INDEX_FIRST_MEME;

import org.junit.jupiter.api.Test;

import seedu.weme.logic.commands.MemeDeleteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the MemeDeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the MemeDeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class MemeDeleteCommandParserTest {

    private MemeDeleteCommandParser parser = new MemeDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new MemeDeleteCommand(INDEX_FIRST_MEME));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MemeDeleteCommand.MESSAGE_USAGE));
    }
}
