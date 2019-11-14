package seedu.address.logic.parser.sidebar;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.sidebar.EnterItineraryPageCommand;

class EnterItineraryPageParserTest {
    private EnterItineraryPageParser parser = new EnterItineraryPageParser();

    @Test
    public void parse_validUserInput_success() {
        assertParseSuccess(parser, "", new EnterItineraryPageCommand());
    }
}
