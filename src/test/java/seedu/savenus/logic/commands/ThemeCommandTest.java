package seedu.savenus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.savenus.logic.commands.CommandTestUtil.assertCommandSuccess;
import org.junit.jupiter.api.Test;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;

//@@author robytanama
/**
 * Contains tests for {@code ThemeCommand}.
 */
public class ThemeCommandTest {

    private static Model model = new ModelManager();
    private static Model expectedModel = new ModelManager();

    private static final String VALID_THEME_DARK = "dark";
    private static final String VALID_THEME_LIGHT = "light";
    private static final String VALID_THEME_DARK_CAPITAL = "DARK";
    private static final String INVALID_THEME = "asdasd";

    private static final ThemeCommand darkThemeCommand = new ThemeCommand("dark");
    private static final ThemeCommand lightThemeCommand = new ThemeCommand("light");

    @Test
    public void equals() {
        // Same object, should return true
        assertEquals(true, darkThemeCommand.equals(darkThemeCommand));

        // Same theme, should return true
        assertEquals(true, darkThemeCommand.equals(new ThemeCommand("dark")));

        // Different object, should return false
        assertEquals(false, darkThemeCommand.equals(lightThemeCommand));

        // Different theme, should return false
        assertEquals(false, darkThemeCommand.equals(new ThemeCommand("light")));

        // Different capitalization, should return false
        assertEquals(false, darkThemeCommand.equals(new ThemeCommand("Dark")));
    }

    @Test
    public void darkTheme_correctString_returnTrue() {
        assertEquals(true, ThemeCommand.DARK_THEME.equals("dark"));
    }

    @Test
    public void darkTheme_wrongString_returnFalse() {
        assertEquals(false, ThemeCommand.DARK_THEME.equals("asdad"));
    }

    @Test
    public void lightTheme_correctString_returnTrue() {
        assertEquals(ThemeCommand.LIGHT_THEME, "light");
    }

    @Test
    public void getTheme_correctThemeReturned() {
        assertEquals("dark", darkThemeCommand.getTheme());
    }

    @Test
    public void execute_correctReturnType() throws CommandException {
        assertEquals(true, darkThemeCommand.execute(model) instanceof CommandResult);
    }

    @Test
    public void execute_validInput_noExceptionThrown() {
        CommandResult expectedCommandResult =
                new CommandResult(ThemeCommand.MESSAGE_SUCCESS_DARK, false, false, false);
        assertCommandSuccess(new ThemeCommand(VALID_THEME_DARK), model, expectedCommandResult, expectedModel);

        assertCommandSuccess(new ThemeCommand(VALID_THEME_DARK_CAPITAL), model, expectedCommandResult, expectedModel);

        expectedCommandResult =
                new CommandResult(ThemeCommand.MESSAGE_SUCCESS_LIGHT, false, false, false);
        assertCommandSuccess(new ThemeCommand(VALID_THEME_LIGHT), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidInput_exceptionThrown() {
        assertThrows(CommandException.class, () -> new ThemeCommand(INVALID_THEME).execute(model));
    }
}
