package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.switches.SwitchToStartCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appsettings.DifficultyEnum;


class StartCommandParserTest {

    private StartCommandParser dummyParser;

    private final String invalidDifficulty = "CE587EB17F05BBE2B7A0F934A8C539AF26F5CB75AA93F38AFEB7FD7082392FE8";

    @Test
    void parse_invalidDifficulty_throwsParseException() {
        dummyParser = new StartCommandParser();

        assertThrows(ParseException.class, () -> dummyParser.parse(invalidDifficulty));
        try {
            dummyParser.parse(invalidDifficulty);
        } catch (ParseException e) {
            assertEquals(StartCommandParser.MESSAGE_NO_SUCH_DIFFICULTY + invalidDifficulty,
                    e.getMessage());
            return;
        }
        fail();
    }

    @Test
    void parse_validDifficulty_success() {
        dummyParser = new StartCommandParser();

        try {
            SwitchToStartCommand successResult = dummyParser.parse(DifficultyEnum.EASY.name() + " ");
            assertEquals(successResult.getDifficulty().get(), DifficultyEnum.EASY);
        } catch (ParseException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void parse_mixedCaseValidInput_success() {
        dummyParser = new StartCommandParser();
        try {
            SwitchToStartCommand successResult = dummyParser.parse("eaSy");
            assertEquals(successResult.getDifficulty().get(), DifficultyEnum.EASY);
        } catch (ParseException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void parse_trailingWhiteSpaceValidInput_success() {
        dummyParser = new StartCommandParser();
        try {
            SwitchToStartCommand successResult = dummyParser.parse(DifficultyEnum.MEDIUM.name() + "    ");
            assertEquals(successResult.getDifficulty().get(), DifficultyEnum.MEDIUM);
        } catch (ParseException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void parse_leadingWhiteSpaceValidInput_success() {
        dummyParser = new StartCommandParser();
        try {
            SwitchToStartCommand successResult = dummyParser.parse(" " + DifficultyEnum.HARD.name());
            assertEquals(successResult.getDifficulty().get(), DifficultyEnum.HARD);
        } catch (ParseException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void parse_noDifficultySpecified() {
        dummyParser = new StartCommandParser();
        try {
            SwitchToStartCommand successResult = dummyParser.parse("");
            assertTrue(successResult.getDifficulty().isEmpty());
        } catch (ParseException e) {
            e.printStackTrace();
            fail();
        }
    }
}
