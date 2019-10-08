package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIdentificationNumbers.FIRST_BODY_ID_NUM;
import static seedu.address.testutil.TypicalIdentificationNumbers.FIRST_WORKER_ID_NUM;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "B00000000", new DeleteCommand(FIRST_BODY_ID_NUM));
        assertParseSuccess(parser, "W00000", new DeleteCommand(FIRST_WORKER_ID_NUM));

        // todo add test for fridge!!
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid string
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        // Invalid Id length
        assertParseFailure(parser, "B0000", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        // Invalid First Char
        assertParseFailure(parser, "K000", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        // Invalid Id
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        // No input given
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

    }
}
