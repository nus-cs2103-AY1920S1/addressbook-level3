package seedu.address.logic.parser.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiMode;
import seedu.address.logic.commands.gui.ChangeModeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

class ChangeModeCommandParserTest {
    private static final String NON_EXISTENT_GUI_MODE = "notexistingguimode";

    @Test
    public void parse_nullSemesterName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ChangeModeCommandParser().parse(null));
    }

    @Test
    public void parse_invalidSemesterName_throwsParseException() {
        assertThrows(ParseException.class, () -> new ChangeModeCommandParser().parse(NON_EXISTENT_GUI_MODE));
        assertThrows(ParseException.class, () -> new ChangeModeCommandParser().parse(""));
    }

    @Test
    public void parse_helpWithCommand_success() throws ParseException {
        ChangeModeCommand expectedChangeModeCommand = new ChangeModeCommand(GuiMode.LIGHT);
        assertEquals(new ChangeModeCommandParser().parse(GuiMode.LIGHT.getModeName()), expectedChangeModeCommand);

        expectedChangeModeCommand = new ChangeModeCommand(GuiMode.DARK);
        assertEquals(new ChangeModeCommandParser().parse(GuiMode.DARK.getModeName()), expectedChangeModeCommand);
    }
}
