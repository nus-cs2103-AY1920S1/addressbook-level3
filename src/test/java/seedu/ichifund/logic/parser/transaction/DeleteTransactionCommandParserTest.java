package seedu.ichifund.logic.parser.transaction;

import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ichifund.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ichifund.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ichifund.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.ichifund.logic.commands.transaction.DeleteTransactionCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteTransactionCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteTransactionCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteTransactionCommandParserTest {

    private DeleteTransactionCommandParser parser = new DeleteTransactionCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteTransactionCommand() {
        assertParseSuccess(parser, "1", new DeleteTransactionCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTransactionCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTransactionCommand.MESSAGE_USAGE));
    }
}
