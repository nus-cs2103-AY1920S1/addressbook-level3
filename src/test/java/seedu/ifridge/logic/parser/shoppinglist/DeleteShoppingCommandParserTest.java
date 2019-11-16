package seedu.ifridge.logic.parser.shoppinglist;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ifridge.testutil.TypicalIndexes.INDEX_FIRST_FOOD;

import org.junit.jupiter.api.Test;

import seedu.ifridge.logic.commands.shoppinglist.DeleteShoppingCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteShoppingCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteShoppingCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteShoppingCommandParserTest {

    private DeleteShoppingCommandParser parser = new DeleteShoppingCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteShoppingCommand() {
        assertParseSuccess(parser, "1", new DeleteShoppingCommand(INDEX_FIRST_FOOD));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteShoppingCommand.MESSAGE_USAGE));
    }
}
