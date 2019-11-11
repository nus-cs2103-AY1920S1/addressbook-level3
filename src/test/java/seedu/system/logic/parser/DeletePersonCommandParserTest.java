package seedu.system.logic.parser;

import static seedu.system.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.system.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.system.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.system.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.system.logic.commands.outofsession.DeletePersonCommand;
import seedu.system.logic.parser.outofsession.DeletePersonCommandParser;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeletePersonCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeletePersonCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeletePersonCommandParserTest {

    private DeletePersonCommandParser parser = new DeletePersonCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeletePersonCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            DeletePersonCommand.MESSAGE_USAGE));
    }
}
