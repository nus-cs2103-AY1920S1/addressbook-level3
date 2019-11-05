package seedu.guilttrip.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.ui.util.Theme;

/**
 * Sets the theme to the dark theme.
 */
public class SetDarkThemeCommand extends Command {

    public static final String COMMAND_WORD = "setDarkTheme";

    public static final String MESSAGE_SUCCESS = "Changed to dark theme";

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, true, Theme.DARK);
    }

}
