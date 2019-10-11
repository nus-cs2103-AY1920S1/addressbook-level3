package tagline.logic.parser;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tagline.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tagline.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tagline.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.contact.DeleteContactCommand;
import tagline.logic.parser.contact.DeleteContactParser;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteContactCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteContactCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ContactParserUtil, and
 * therefore should be covered by the ContactParserUtilTest.
 */
public class DeleteContactParserTest {

    private DeleteContactParser parser = new DeleteContactParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteContactCommand(INDEX_FIRST_CONTACT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteContactCommand.MESSAGE_USAGE));
    }
}
