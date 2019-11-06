package seedu.address.logic.commands.gui;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.stream.Collectors;

import seedu.address.commons.core.GuiMode;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Changes the mode, or thematic style, of the GUI.
 */
public class ChangeModeCommand extends Command {
    public static final String COMMAND_WORD = "mode";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Changing the mode or style of the GUI";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the mode or style of the GUI\n"
            + "Parameters: "
            + "MODE\n"
            + "Existing modes are: "
            + Arrays.stream(GuiMode.values()).map(GuiMode::getModeName).collect(Collectors.joining(", "));

    public static final String MESSAGE_SUCCESS = "The mode has been changed to %s.";

    private final GuiMode guiMode;

    public ChangeModeCommand(GuiMode guiMode) {
        requireNonNull(guiMode);
        this.guiMode = guiMode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        GuiSettings oldSettings = model.getGuiSettings();
        GuiSettings newSettings = new GuiSettings(oldSettings.getWindowWidth(), oldSettings.getWindowHeight(),
                (int) oldSettings.getWindowCoordinates().getX(),
                (int) oldSettings.getWindowCoordinates().getY(), guiMode);
        model.setGuiSettings(newSettings);
        return new CommandResult(String.format(MESSAGE_SUCCESS, guiMode.getModeName()), true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeModeCommand // instanceof handles nulls
                && guiMode.equals(((ChangeModeCommand) other).guiMode));
    }
}
