package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseIndexSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseInvalidIndexFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseInvalidPreambleArgsFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseNegativeIndexFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseNoIndexAndFieldFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseZeroIndexFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.event.DeleteEventCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteEventCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteEventCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteEventCommandParserTest {


    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE);
    private DeleteEventCommandParser parser = new DeleteEventCommandParser();

    @Test
    void parse_emptyInvalidFormat_throwsParseException() {
        assertParseNoIndexAndFieldFailure(parser, MESSAGE_INVALID_FORMAT);
        assertParseNegativeIndexFailure(parser, MESSAGE_INVALID_FORMAT);
        assertParseZeroIndexFailure(parser, MESSAGE_INVALID_FORMAT);
        assertParseInvalidIndexFailure(parser, MESSAGE_INVALID_FORMAT);
    }

    @Test
    void parse_validArgs_returnsDeleteEventCommand() {
        assertParseIndexSuccess(parser, new DeleteEventCommand(INDEX_FIRST_EVENT));
    }

    @Test
    void parse_invalidArgs_throwsParseException() {
        assertParseInvalidPreambleArgsFailure(parser, MESSAGE_INVALID_FORMAT);
    }
}
