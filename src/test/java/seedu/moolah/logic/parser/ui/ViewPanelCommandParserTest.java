package seedu.moolah.logic.parser.ui;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.moolah.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.ui.ViewPanelCommand;
import seedu.moolah.logic.parser.exceptions.ParseException;
import seedu.moolah.ui.panel.PanelName;

class ViewPanelCommandParserTest {

    private ViewPanelCommandParser parser = new ViewPanelCommandParser();

    @Test
    void parse_invalid_throwsException() {
        assertThrows(ParseException.class, () -> parser.parse("##$$"));
        assertThrows(ParseException.class, () -> parser.parse(" "));
    }

    @Test
    void parse_valid() {
        assertParseSuccess(parser, " panel", new ViewPanelCommand(new PanelName("panel")));
        assertParseSuccess(parser, " panel123", new ViewPanelCommand(new PanelName("panel123")));
        //ignore trailing and leading spaces
        assertParseSuccess(parser, "    panel    ", new ViewPanelCommand(new PanelName("panel")));
        assertParseSuccess(parser, " pan   el123", new ViewPanelCommand(new PanelName("pan   el123")));
    }

}
