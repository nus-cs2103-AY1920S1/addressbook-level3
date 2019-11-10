package seedu.address.logic.parser.itinerary.eventview;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.itinerary.events.EnterEditEventCommand;

class EnterEditEventParserTest {
    private EnterEditEventParser parser = new EnterEditEventParser();

    @Test
    public void parse_validIndex_success() {
        assertParseSuccess(parser, "1", new EnterEditEventCommand(Index.fromOneBased(1)));
    }

    @Test
    public void parse_invalidIndex_success() {
        // Index 0
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EnterEditEventCommand.MESSAGE_USAGE));

        // Index -1
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EnterEditEventCommand.MESSAGE_USAGE));
    }
}
