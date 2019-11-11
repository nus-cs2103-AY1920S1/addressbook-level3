package seedu.address.logic.commands.gui;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.stream.Collectors;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.GuiTheme;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Changes the thematic style of the GUI.
 */
public class ChangeThemeCommand extends Command {
    public static final String COMMAND_WORD = "theme";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Changing the theme of the GUI";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the theme of the GUI\n"
            + "Parameters: "
            + "THEME\n"
            + "Existing themes are: "
            + Arrays.stream(GuiTheme.values()).map(GuiTheme::getModeName).collect(Collectors.joining(", "));

    public static final String MESSAGE_SUCCESS = "The theme has been changed to %s.";

    private final GuiTheme guiTheme;

    public ChangeThemeCommand(GuiTheme guiTheme) {
        requireNonNull(guiTheme);
        this.guiTheme = guiTheme;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        GuiSettings oldSettings = model.getGuiSettings();
        if (oldSettings == null) {
            GuiSettings newSettings = new GuiSettings(600, 900,
                0, 0, guiTheme);
            model.setGuiSettings(newSettings);
        } else {
            GuiSettings newSettings = new GuiSettings(oldSettings.getWindowWidth(), oldSettings.getWindowHeight(),
                    (int) oldSettings.getWindowCoordinates().getX(),
                    (int) oldSettings.getWindowCoordinates().getY(), guiTheme);
            model.setGuiSettings(newSettings);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, guiTheme.getModeName()), true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeThemeCommand // instanceof handles nulls
                && guiTheme.equals(((ChangeThemeCommand) other).guiTheme));
    }
}
