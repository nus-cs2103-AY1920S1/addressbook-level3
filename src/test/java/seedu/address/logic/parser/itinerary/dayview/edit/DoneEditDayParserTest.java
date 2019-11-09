package seedu.address.logic.parser.itinerary.dayview.edit;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.itinerary.days.edit.DoneEditDayCommand;

class DoneEditDayParserTest {
    private DoneEditDayParser parser = new DoneEditDayParser();

    @Test
    public void parse_validUserInput_success() {
        assertParseSuccess(parser, "1", new DoneEditDayCommand());
    }
}
