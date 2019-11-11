package seedu.eatme.logic.parser;

import static seedu.eatme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eatme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.eatme.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.eatme.testutil.TypicalIndexes.INDEX_FIRST_EATERY;

import org.junit.jupiter.api.Test;

import seedu.eatme.logic.commands.SaveTodoCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the SaveTodoCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the SaveTodoCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class SaveTodoCommandParserTest {

    private SaveTodoCommandParser parser = new SaveTodoCommandParser();

    @Test
    public void parse_validArgs_returnSaveTodoCommand() {
        assertParseSuccess(parser, "1", new SaveTodoCommand(INDEX_FIRST_EATERY));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveTodoCommand.MESSAGE_USAGE));
    }
}
