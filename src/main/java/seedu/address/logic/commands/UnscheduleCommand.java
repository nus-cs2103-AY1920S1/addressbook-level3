package seedu.address.logic.commands;

/**
 * Represents UnscheduleActivityCommand and UnscheduleTimeCommand.
 */
public abstract class UnscheduleCommand extends Command {

    public static final String COMMAND_WORD = "unschedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a contacts to the address book. "
            + ": an activity can be unscheduled from the day by either activity id or by time.";

    public static final String MESSAGE_SUCCESS = "Activity unscheduled!";
}
