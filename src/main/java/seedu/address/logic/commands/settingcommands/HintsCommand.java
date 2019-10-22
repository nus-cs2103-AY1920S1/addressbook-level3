package seedu.address.logic.commands.settingcommands;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.SettingsCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Class that represents a command to change the Model's difficulty for all its games.
 */
public class HintsCommand extends SettingsCommand {

    public static final String COMMAND_WORD = "hints";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enables or disables hints."
            + "Parameters: HINTS [on/off]\n"
            + "Example: " + COMMAND_WORD + " on";

    private final boolean hintsEnabled;

    public HintsCommand(boolean hintsEnabled) {
        this.hintsEnabled = hintsEnabled;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.setHintsEnabled(this.hintsEnabled);
        return new CommandResult("Hints now set to: " + (this.hintsEnabled ? "ON" : "OFF"));
    }

}
