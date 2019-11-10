package seedu.address.logic.parser.sidebar;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.sidebar.EnterDayPageCommand;

class EnterDayPageParserTest {
    private EnterDayPageParser parser = new EnterDayPageParser();

    @Test
    public void parse_validUserInput_success() {
        assertParseSuccess(parser, "", new EnterDayPageCommand());
    }

}
