package seedu.address.logic.parser.itinerary.eventview;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.itinerary.events.EnterCreateEventCommand;

class EnterCreateEventParserTest {
    private EnterCreateEventParser parser = new EnterCreateEventParser();

    @Test
    public void parse_validUserInput_success() {
        assertParseSuccess(parser, "", new EnterCreateEventCommand());
    }

}
