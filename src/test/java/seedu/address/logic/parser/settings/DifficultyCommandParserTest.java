package seedu.address.logic.parser.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.settingcommands.DifficultyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appsettings.DifficultyEnum;

public class DifficultyCommandParserTest {
    private DifficultyCommandParser parser = new DifficultyCommandParser();

    @Test
    public void parse_invalidArgument_throwsParseException() {
        String invalidCommand = "invalid command";
        assertThrows(ParseException.class, () -> parser.parse(invalidCommand));
    }

    @Test
    public void parse_caseSensitiveArgument_success() throws ParseException {
        String upperCaseHard = "HARD";
        String lowerCaseHard = "hard";
        String mixedCaseHard = "hARd";
        DifficultyCommand result = new DifficultyCommand(DifficultyEnum.HARD);
        assertEquals(result, parser.parse(upperCaseHard));
        assertEquals(result, parser.parse(lowerCaseHard));
        assertEquals(result, parser.parse(mixedCaseHard));
    }

    @Test
    public void parse_allPossibleDifficulties_success() throws ParseException {
        String easy = "easy";
        String medium = "Medium";
        String hard = "HARD";
        DifficultyCommand easyDiff = new DifficultyCommand(DifficultyEnum.EASY);
        DifficultyCommand mediumDiff = new DifficultyCommand(DifficultyEnum.MEDIUM);
        DifficultyCommand hardDiff = new DifficultyCommand(DifficultyEnum.HARD);
        assertEquals(easyDiff, parser.parse(easy));
        assertEquals(mediumDiff, parser.parse(medium));
        assertEquals(hardDiff, parser.parse(hard));
    }

}
