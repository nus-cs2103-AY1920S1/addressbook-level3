package seedu.address.logic.parser.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiTheme;
import seedu.address.logic.commands.gui.ChangeThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

class ChangeThemeCommandParserTest {
    private static final String NON_EXISTENT_GUI_MODE = "notexistingguimode";

    @Test
    public void parse_nullSemesterName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ChangeThemeCommandParser().parse(null));
    }

    @Test
    public void parse_invalidSemesterName_throwsParseException() {
        assertThrows(ParseException.class, () -> new ChangeThemeCommandParser().parse(NON_EXISTENT_GUI_MODE));
        assertThrows(ParseException.class, () -> new ChangeThemeCommandParser().parse(""));
    }

    @Test
    public void parse_helpWithCommand_success() throws ParseException {
        ChangeThemeCommand expectedChangeThemeCommand = new ChangeThemeCommand(GuiTheme.LIGHT);
        assertEquals(new ChangeThemeCommandParser().parse(GuiTheme.LIGHT.getModeName()), expectedChangeThemeCommand);

        expectedChangeThemeCommand = new ChangeThemeCommand(GuiTheme.DARK);
        assertEquals(new ChangeThemeCommandParser().parse(GuiTheme.DARK.getModeName()), expectedChangeThemeCommand);
    }
}
