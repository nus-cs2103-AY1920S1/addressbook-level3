package seedu.address.logic.parser.settings;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.settingcommands.HintsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AvatarCommandParserTest {
    private HintsCommandParser parser = new HintsCommandParser();

    @Test
    public void parse_invalidArgument_throwsParseException() {
        String invalidCommand = "invalid command";
        assertThrows(ParseException.class, () -> parser.parse(invalidCommand));
    }

    @Test
    public void parse_caseSensitiveArgument_success() throws ParseException {
        String upperCaseOff = "OFF";
        String lowerCaseOff = "off";
        String mixedCaseOff = "oFf";
        HintsCommand result = new HintsCommand(false);
        assertEquals(result, parser.parse(upperCaseOff));
        assertEquals(result, parser.parse(lowerCaseOff));
        assertEquals(result, parser.parse(mixedCaseOff));
    }

    @Test
    public void parse_allPossibleDifficulties_success() throws ParseException {
        String on = "ON";
        String off = "off";
        HintsCommand hintsOn = new HintsCommand(true);
        HintsCommand hintsOff = new HintsCommand(false);
        assertEquals(hintsOn, parser.parse(on));
        assertEquals(hintsOff, parser.parse(off));
    }

}
