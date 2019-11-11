package seedu.pluswork.logic.commands.settings;

import static java.util.Objects.requireNonNull;

import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.settings.Theme;

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
