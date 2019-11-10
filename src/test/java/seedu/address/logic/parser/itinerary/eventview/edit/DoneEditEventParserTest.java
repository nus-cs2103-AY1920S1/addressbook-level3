package seedu.address.logic.parser.itinerary.eventview.edit;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.itinerary.events.edit.DoneEditEventCommand;

class DoneEditEventParserTest {
    private DoneEditEventParser parser = new DoneEditEventParser();

    @Test
    public void parse_validUserInput_success() {
        assertParseSuccess(parser,
                "1", new DoneEditEventCommand());
    }

}
