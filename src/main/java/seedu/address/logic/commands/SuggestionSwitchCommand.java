package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OFF;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ON;

import seedu.address.logic.commands.exceptions.CommandWordException;
import seedu.address.model.Model;

/**
 * Turns suggestions on and off in the addressbook.
 */
public class SuggestionSwitchCommand extends Command {
    public static final String COMMAND_WORD = "suggestion";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switches command suggestions on and off. "
            + "Parameters: "
            + "[" + PREFIX_ON + "] "
            + "[" + PREFIX_OFF + "]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ON;

    public static final String MESSAGE_SUCCESS = "Suggestions have been switched %1$s";

    private static final String SWITCH_ON = "on";
    private static final String SWITCH_OFF = "off";

    private boolean isOn;

    public SuggestionSwitchCommand(boolean isOn) {
        this.isOn = isOn;
    }

    @Override
    public CommandResult execute(Model model) throws CommandWordException {
        requireNonNull(model);
        if (isOn) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, SWITCH_ON));
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS, SWITCH_OFF));
        }
    }

    public boolean isOn() {
        return isOn;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SuggestionSwitchCommand // instanceof handles nulls
                && isOn == ((SuggestionSwitchCommand) other).isOn); // state check
    }
}
