package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EventCommand;
import seedu.address.model.performance.Event;

public class EventCommandParserTest {

    private EventCommandParser parser = new EventCommandParser();

    @Test
    public void parse_validArgs_returnsEventCommand() {
        assertParseSuccess(parser, VALID_EVENT, new EventCommand(new Event(VALID_EVENT)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventCommand.MESSAGE_USAGE));
    }

}
