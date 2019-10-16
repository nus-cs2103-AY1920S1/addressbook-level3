package seedu.address.logic.commands;

/**
 * Edits the details of an existing contacts in the address book.
 */
public abstract class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": activity needs to come after";

    public static final String MESSAGE_SUCCESS = "Added!";
}
