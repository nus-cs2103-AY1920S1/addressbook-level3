package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * To handle when a user supplies a command the application does not understand.
 */
public class UnknownCommand extends Command {

    public static final String SHOWING_UNKNOWN_MESSAGE = "%1$s: We don't know what that means. "
            + "\n"
            + "Help us understand so we can add this to our command library for your personal usage."
            + "\n"
            + "Please enter what you really meant or type \"cancel\" if it was a genuine mistake.";

    public static final String UNKNOWN_COMMAND_USAGE = "%1$s: Sorry! We don't support mapping a full input yet "
            + "\n"
            + "Please enter ONLY the command word to continue learning your wrong command for future usage "
            + "\n"
            + "Alternatively, type \"cancel\" if it was a genuine mistake.";

    private final String unknown;

    public UnknownCommand(String unknown) {
        this.unknown = unknown;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        String [] splittedCommand = unknown.split(" ");
        if (splittedCommand.length > 1) {
            throw new CommandException(String.format(UNKNOWN_COMMAND_USAGE, this.unknown));
        }
        model.saveCommand(this.unknown);
        return new CommandResult(String.format(SHOWING_UNKNOWN_MESSAGE, this.unknown),
                false, false, false, true, false);
    }
}
