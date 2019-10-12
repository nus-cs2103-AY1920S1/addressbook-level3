package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Title;


/**
 * Command to create a new Activity.
 */

public class ActivityCommand extends Command {
    public static final String COMMAND_WORD = "activity";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new Activity.\n"
            + "Compulsory Parameters: t/\n"
            + "Optional Parameters: p/\n"
            + "Example: Activity ";
    public static final String MESSAGE_SUCCESS = "New activity added: %1$s";

    public static final String MESSAGE_ARGUMENTS = "Title: %s";

    private final Title title;
    private final List<String> participants;

    /**
     * @param title Title of the activity
     * @param participants List of the names of participants
     */
    public ActivityCommand(Title title, List<String> participants) {
        requireAllNonNull(title, participants);
        this.title = title;
        this.participants = participants;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // TODO: Add validation to ensure that all participants are valid Persons
        Activity toAdd = new Activity(title);
        model.addActivity(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
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
