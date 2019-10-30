package seedu.address.logic.parser.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.settingcommands.AvatarCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AvatarCommandParserTest {
    private AvatarCommandParser parser = new AvatarCommandParser();

    @Test
    public void parse_invalidArgument_throwsParseException() {
        String invalidCommand = "invalid command";
        assertThrows(ParseException.class, () -> parser.parse(invalidCommand));
    }

    @Test
    public void parse_boundaryValues_throwsParseException() {
        String lowerBound = "-1";
        String upperBound = "152";
        assertThrows(ParseException.class, () -> parser.parse(lowerBound));
        assertThrows(ParseException.class, () -> parser.parse(upperBound));
    }

    @Test
    public void parse_boundaryValues_success() throws ParseException {
        String lowerBound = "0";
        String upperBound = "151";
        AvatarCommand avatar1 = new AvatarCommand(0);
        AvatarCommand avatar151 = new AvatarCommand(151);
        assertEquals(avatar1, parser.parse(lowerBound));
        assertEquals(avatar151, parser.parse(upperBound));
    }

    @Test
    public void parse_easterEgg_success() throws ParseException {
        String easterEgg = "29126";
        AvatarCommand avatar29126 = new AvatarCommand(29126);
        assertEquals(avatar29126, parser.parse(easterEgg));
    }

}
