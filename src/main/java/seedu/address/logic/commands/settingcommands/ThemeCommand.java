package seedu.address.logic.commands.settingcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.appsettings.ThemeEnum;

/**
 * Class that represents a command to change the Model's difficulty for all its games.
 */
public class ThemeCommand extends SettingsCommand {

    public static final String COMMAND_WORD = "theme";

    public static final String MESSAGE_USAGE = "Parameters: theme [light/dark]\n"
            + "Example: " + COMMAND_WORD + " light";

    private final ThemeEnum theme;

    public ThemeCommand(ThemeEnum theme) {
        requireNonNull(theme);
        this.theme = theme;
    }

    @Override
    public CommandResult execute(Model model) {
        model.setDefaultTheme(this.theme);
        return new CommandResult("Theme now set to: " + theme);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ThemeCommand // instanceof handles nulls
                && theme.equals(((ThemeCommand) other).theme));
    }

}
