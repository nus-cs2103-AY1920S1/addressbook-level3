package seedu.address.logic.parser.itinerary.dayview.edit;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.itinerary.days.edit.CancelEditDayCommand;

class CancelEditDayParserTest {
    private CancelEditDayParser parser = new CancelEditDayParser();

    @Test
    public void parse_validIndex_success() {
        assertParseSuccess(parser, "1", new CancelEditDayCommand());
    }
}
