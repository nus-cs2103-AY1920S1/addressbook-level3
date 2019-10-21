package seedu.address.logic.commands.settingcommands;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.SettingsCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appsettings.ThemeEnum;

/**
 * Class that represents a command to change the Model's difficulty for all its games.
 */
public class ThemeCommand extends SettingsCommand {

    public static final String COMMAND_WORD = "theme";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes theme to light or dark."
            + "Parameters: THEME [light/dark]\n"
            + "Example: " + COMMAND_WORD + " light";

    private final ThemeEnum theme;

    public ThemeCommand(ThemeEnum theme) {
        this.theme = theme;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.setDefaultTheme(this.theme);
        return new CommandResult("Theme now set to: " + theme);
    }

}
