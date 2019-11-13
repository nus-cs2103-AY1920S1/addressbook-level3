package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.planner.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.planner.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.planner.logic.commands.deletecommand.DeleteCommand;
import seedu.planner.logic.commands.deletecommand.DeleteContactCommand;

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
    public void parse_validArgs_returnsDeleteContactCommand() {
        assertParseSuccess(parser, DeleteContactCommand.SECOND_COMMAND_WORD + " " + "1",
                new DeleteContactCommand(INDEX_FIRST, false));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, DeleteContactCommand.SECOND_COMMAND_WORD + " " + "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
