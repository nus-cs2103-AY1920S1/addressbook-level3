package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.settings.Theme;

/**
 * Allows user to set the theme of +Work.
 */
public class ThemeCommand extends Command {
    public static final String COMMAND_WORD = "theme";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Toggles theme between light or dark."
            + "Parameters: [light/dark]\n"
            + "Example: " + COMMAND_WORD + " light";

    private final Theme theme;

    public ThemeCommand(Theme theme) {
        this.theme = theme;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.setCurrentTheme(this.theme);
        return new CommandResult("Theme now set to: " + theme, false, false, true);
    }
}
