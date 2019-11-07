package seedu.address.logic.parser;

//@@author atharvjoshi

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTITY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SubmitCommand;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SubmitCommandParser.
 */
public class SubmitCommandParserTest {
    private SubmitCommandParser parser = new SubmitCommandParser();

    @Test
    public void parse_validArgs_returnsSubmitCommand() {
        assertParseSuccess(parser, "1", new SubmitCommand(INDEX_FIRST_ENTITY));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SubmitCommand.MESSAGE_USAGE));
    }
}
