package seedu.pluswork.logic.parser.task;

import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pluswork.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.pluswork.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.pluswork.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import org.junit.jupiter.api.Test;

import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.logic.commands.task.DeleteTaskCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteTaskCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteTaskCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteTaskCommandParserTest {

    private DeleteTaskCommandParser parser = new DeleteTaskCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteTaskCommand() throws CommandException {
        assertParseSuccess(parser, "delete-task ti/1", new DeleteTaskCommand(INDEX_FIRST_TASK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws CommandException {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE));
    }
}
