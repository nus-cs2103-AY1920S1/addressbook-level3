package seedu.address.logic.parser.itinerary.eventview.edit;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.itinerary.events.edit.CancelEditEventCommand;

class CancelEditEventParserTest {
    private CancelEditEventParser parser = new CancelEditEventParser();

    @Test
    public void parse_validIndex_success() {
        assertParseSuccess(parser, "", new CancelEditEventCommand());
    }
}
