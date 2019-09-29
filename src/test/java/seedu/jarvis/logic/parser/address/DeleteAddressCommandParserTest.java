package seedu.jarvis.logic.parser.address;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.address.DeleteAddressCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteAddressCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteAddressCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteAddressCommandParserTest {

    private DeleteAddressCommandParser parser = new DeleteAddressCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteAddressCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteAddressCommand.MESSAGE_USAGE));
    }
}
