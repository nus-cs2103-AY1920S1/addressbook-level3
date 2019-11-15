package seedu.pluswork.logic.commands.settings;

import static java.util.Objects.requireNonNull;

import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.settings.ClockFormat;

/**
 * Allows user to set the clock format of +Work.
 */
public class ClockCommand extends Command {
    public static final String COMMAND_WORD = "clock";
    public static final String PREFIX_USAGE = "";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Toggles clock format between 24hr and 12hr."
            + "Parameters: [twenty_four/twelve]\n"
            + "Example: " + COMMAND_WORD + " twenty_four";
    public static final String SHOWING_CLOCK_MESSAGE = "Clock format now set to: ";

    private final ClockFormat clockFormat;

    public ClockCommand(ClockFormat clockFormat) {
        requireNonNull(clockFormat);
        this.clockFormat = clockFormat;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.setClockFormat(this.clockFormat);
        return new CommandResult(SHOWING_CLOCK_MESSAGE + clockFormat.getDisplayName());
    }

}
