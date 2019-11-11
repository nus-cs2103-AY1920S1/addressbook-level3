package seedu.pluswork.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.pluswork.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pluswork.logic.commands.settings.ThemeCommand.SHOWING_THEME_MESSAGE;
import static seedu.pluswork.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.pluswork.logic.commands.settings.ThemeCommand;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.ModelManager;
import seedu.pluswork.model.settings.Theme;

class ThemeCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    void constructor_nullTheme_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ThemeCommand(null));
    }

    @Test
    void execute_theme_success() {
        // ensures default is DARK
        CommandResult expectedCommandResult = new CommandResult(SHOWING_THEME_MESSAGE + Theme.DARK, false, false, true);
        assertCommandSuccess(new ThemeCommand(Theme.DARK), model, expectedCommandResult, expectedModel);
    }

    @Test
    void execute_validTheme_changesDefaultFormatSuccess() {
        // since default is DARK
        expectedModel.setCurrentTheme(Theme.LIGHT);
        CommandResult expectedCommandResult = new CommandResult(SHOWING_THEME_MESSAGE + Theme.LIGHT);
        assertCommandSuccess(new ThemeCommand(Theme.LIGHT), model, expectedCommandResult, expectedModel);
        // ensure default theme of +Work is changed
        assertEquals(Theme.LIGHT, model.getCurrentTheme());
    }

}
