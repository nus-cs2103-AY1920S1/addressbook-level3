package seedu.address.logic.parser.itinerary.dayview;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.itinerary.days.EnterCreateDayCommand;

class EnterCreateDayParserTest {
    private EnterCreateDayParser parser = new EnterCreateDayParser();

    @Test
    public void parse_validUserInput_success() {
        assertParseSuccess(parser, "1", new EnterCreateDayCommand());
    }
}
