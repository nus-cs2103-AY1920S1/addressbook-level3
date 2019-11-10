package seedu.savenus.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.savenus.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.CommandResult;
import seedu.savenus.logic.commands.ThemeCommand;
import seedu.savenus.logic.parser.exceptions.ParseException;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;

//@@author robytanama
/**
 * Contain tests for {@code ThemeCommandParser}.
 */
public class ThemeCommandParserTest {

    private static final String VALID_COMMAND_INPUT = "dark";
    private static final String INVALID_COMMAND_MULTIPLE_INPUT = "dark light dark";
    private static final String EMPTY_INPUT = "";

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private ThemeCommandParser themeCommandParser = new ThemeCommandParser();

    @Test
    public void parse_validInput_noExceptionThrown() throws ParseException {
        ThemeCommand validThemeCommand = themeCommandParser.parse(VALID_COMMAND_INPUT);
        CommandResult expectedCommandResult =
                new CommandResult(ThemeCommand.MESSAGE_SUCCESS_DARK, false, false, false);
        assertCommandSuccess(validThemeCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void parse_invalidInput_exceptionThrown() throws ParseException {
        assertThrows(ParseException.class, () -> themeCommandParser.parse(EMPTY_INPUT));
        assertThrows(ParseException.class, () -> themeCommandParser.parse(INVALID_COMMAND_MULTIPLE_INPUT));
    }
}
