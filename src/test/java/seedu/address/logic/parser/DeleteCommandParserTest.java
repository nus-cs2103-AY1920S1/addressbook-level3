package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIdentificationNumbers.FIRST_BODY_ID_NUM;
import static seedu.address.testutil.TypicalIdentificationNumbers.FIRST_FRIDGE_ID_NUM;
import static seedu.address.testutil.TypicalIdentificationNumbers.FIRST_WORKER_ID_NUM;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
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
        assertParseSuccess(parser, " " + PREFIX_FLAG + "b 1",
                new DeleteCommand(Index.fromZeroBased(FIRST_BODY_ID_NUM.getIdNum()), "B"));

        assertParseSuccess(parser, " " + PREFIX_FLAG + "w 1",
                new DeleteCommand(Index.fromZeroBased(FIRST_WORKER_ID_NUM.getIdNum()),
                        "W"));

        assertParseSuccess(parser, " " + PREFIX_FLAG + "f 1",
                new DeleteCommand(Index.fromZeroBased(FIRST_FRIDGE_ID_NUM.getIdNum()), "F"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid string
        assertParseFailure(parser, " " + PREFIX_FLAG + "b a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));

        // Invalid flag
        assertParseFailure(parser, " " + PREFIX_FLAG + "k a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));

        // No input given
        assertParseFailure(parser, " " + PREFIX_FLAG + "b", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));

        // No input given
        assertParseFailure(parser, " " + PREFIX_FLAG + "b ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));

    }
}
