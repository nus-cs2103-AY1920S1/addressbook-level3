package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;


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
    private final ArrayList<Person> participants = new ArrayList<>();

    /**
     * @param title Title of the activity
     * @param participants List of the names of participants
     */
    public ActivityCommand(String title, List<String> participants) {
        requireAllNonNull(title, participants);
        for (String personName : participants) {
            // TODO: Find the person from addressbook, and add in if valid
        }
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
