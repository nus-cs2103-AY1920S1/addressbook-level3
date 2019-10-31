package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.settings.ClockFormat;

/**
 * Allows user to set the clock format of +Work.
 */
public class ClockCommand extends Command {
    public static final String COMMAND_WORD = "clock";
    public static final String PREFIX_USAGE = "";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Toggles clock format between 24hr and 12hr."
            + "Parameters: [twenty_four/twelve]\n"
            + "Example: " + COMMAND_WORD + " twenty_four";

    private final ClockFormat clockFormat;

    public ClockCommand(ClockFormat clockFormat) {
        this.clockFormat = clockFormat;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.setClockFormat(this.clockFormat);
        return new CommandResult("Clock format now set to: " + clockFormat.getDisplayName());
    }

}
