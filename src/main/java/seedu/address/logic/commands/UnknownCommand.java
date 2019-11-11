package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * To handle when a user supplies a command the application does not understand.
 */
public class UnknownCommand extends Command {

    public static final String CANCEL_USAGE = "\n"
            + "Alternatively, type \"cancel\" if it was a genuine mistake and "
            + "you want to escape this unknown command state.";

    public static final String SHOWING_UNKNOWN_MESSAGE = "%1$s: We don't know what that means. "
            + "\n"
            + "Help us understand so we can add this to our command library for your personal usage."
            + "\n"
            + "Please enter ONLY the command word of what you wanted to do.";

    public static final String UNKNOWN_COMMAND_USAGE = "%1$s: That is not a valid input for mapping a command!"
            + "\n"
            + "Please enter ONLY the command word you wish TutorAid to learn for: ";

    private final String unknown;

    public UnknownCommand(String unknown) {
        this.unknown = unknown;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        String [] splittedCommand = unknown.split(" ");
        if (splittedCommand.length > 1) {
            throw new CommandException(String.format(UNKNOWN_COMMAND_USAGE + model.getSavedCommand() + CANCEL_USAGE,
                    this.unknown));
        }
        model.saveCommand(this.unknown);
        return new CommandResult(String.format(SHOWING_UNKNOWN_MESSAGE + CANCEL_USAGE, this.unknown),
                false, false, false, true, false,
                false, false, false);
    }
}
