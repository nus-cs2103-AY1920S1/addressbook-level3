package seedu.address.logic.parser;

//@@author atharvjoshi

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTITY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FillCommand;
import seedu.address.model.incident.CallerNumber;
import seedu.address.model.incident.Description;

/**
 * Contains integration tests (interaction with the Model) and unit tests for FillCommandParser.
 */
public class FillCommandParserTest {
    private FillCommandParser parser = new FillCommandParser();

    @Test
    public void parse_validArgs_returnsFillCommand() {
        assertParseSuccess(parser, "1 p/89403948 desc/test", new FillCommand(INDEX_FIRST_ENTITY,
                new CallerNumber("89403948"), new Description("test")));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "x p/89403948 desc/test", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FillCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidCallerNumberPrefix_throwsParseException() {
        assertParseFailure(parser, "2 x/89403948 desc/test", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FillCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDescriptionPrefix_throwsParseException() {
        assertParseFailure(parser, "2 p/89403948 d/test", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FillCommand.MESSAGE_USAGE));
    }
}
