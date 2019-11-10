package seedu.guilttrip.logic.commands.uicommands;

import static java.util.Objects.requireNonNull;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.ui.util.Theme;

/**
 * Sets the theme to the light theme.
 */
public class SetLightThemeCommand extends Command {

    public static final String COMMAND_WORD = "setLightTheme";
    public static final String ONE_LINER_DESC = COMMAND_WORD + ": Sets the theme to the light theme.";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC;

    public static final String MESSAGE_SUCCESS = "Changed to light theme";

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, true, Theme.LIGHT);
    }

}
