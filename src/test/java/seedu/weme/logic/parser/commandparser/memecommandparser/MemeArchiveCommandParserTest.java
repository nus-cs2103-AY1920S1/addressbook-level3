package seedu.weme.logic.parser.commandparser.memecommandparser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.weme.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.weme.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.weme.logic.commands.memecommand.MemeArchiveCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the MemeArchiveCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the MemeArchiveCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class MemeArchiveCommandParserTest {

    private MemeArchiveCommandParser parser = new MemeArchiveCommandParser();

    @Test
    public void parse_validArgs_returnsArchiveCommand() {
        assertParseSuccess(parser, "1", new MemeArchiveCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MemeArchiveCommand.MESSAGE_USAGE));
    }
}
