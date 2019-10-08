package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Command to create a new Activity.
 */

public class ActivityCommand extends Command {
    public static final String COMMAND_WORD = "activity";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new Activity.\n"
            + "Parameters: \n"
            + "Example: Activity ";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Activity command not implemented yet";

    public static final String MESSAGE_ARGUMENTS = "Title: %s";

    private final String title;

    /**
     * @param title Title of the activity
     */
    public ActivityCommand(String title) {
        requireAllNonNull(title);

        this.title = title;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, title));
    }

    @Override
    public boolean equals(Object other) {
        //short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ActivityCommand)) {
            return false;
        }

        // state check
        ActivityCommand e = (ActivityCommand) other;
        return title.equals(e.title);
    }
}
