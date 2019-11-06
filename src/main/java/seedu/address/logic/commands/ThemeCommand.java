package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.settings.Theme;

/**
 * Allows user to set the theme of +Work.
 */
public class ThemeCommand extends Command {
    public static final String COMMAND_WORD = "theme";
    public static final String PREFIX_USAGE = "";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Toggles theme between light or dark."
            + "Parameters: [light/dark]\n"
            + "Example: " + COMMAND_WORD + " light";

    public static final String SHOWING_THEME_MESSAGE = "Theme now set to: ";

    private final Theme theme;

    public ThemeCommand(Theme theme) {
        requireNonNull(theme);
        this.theme = theme;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.setCurrentTheme(this.theme);
        return new CommandResult(SHOWING_THEME_MESSAGE + theme, false, false, true);
    }
}
