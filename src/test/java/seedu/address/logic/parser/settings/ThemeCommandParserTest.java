package seedu.address.logic.parser.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.settingcommands.ThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appsettings.ThemeEnum;

public class ThemeCommandParserTest {
    private ThemeCommandParser parser = new ThemeCommandParser();

    @Test
    public void parse_invalidArgument_throwsParseException() {
        String invalidCommand = "invalid command";
        assertThrows(ParseException.class, () -> parser.parse(invalidCommand));
    }

    @Test
    public void parse_caseSensitiveArgument_success() throws ParseException {
        String upperCaseLight = "LIGHT";
        String lowerCaseLight = "light";
        String mixedCaseLight = "LigHT";
        ThemeCommand result = new ThemeCommand(ThemeEnum.LIGHT);
        assertEquals(result, parser.parse(upperCaseLight));
        assertEquals(result, parser.parse(lowerCaseLight));
        assertEquals(result, parser.parse(mixedCaseLight));
    }

    @Test
    public void parse_allPossibleDifficulties_success() throws ParseException {
        String dark = "dArk";
        String light = "light";
        ThemeCommand themeDark = new ThemeCommand(ThemeEnum.DARK);
        ThemeCommand themeLight = new ThemeCommand(ThemeEnum.LIGHT);
        assertEquals(themeDark, parser.parse(dark));
        assertEquals(themeLight, parser.parse(light));
    }

}
