package seedu.jarvis.logic.parser.cca;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_CCA;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.cca.DeleteCcaCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCcaCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCcaCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the CcaParserUtil, and
 * therefore should be covered by the CcaParserUtilTest.
 */
public class DeleteCcaCommandParserTest {

    private DeleteCcaCommandParser parser = new DeleteCcaCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteCcaCommand(INDEX_FIRST_CCA));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCcaCommand.MESSAGE_USAGE));
    }
}
