package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.task.TaskPredicate;

/**
 * Finds and lists all tasks with specific details (heading, date, time) as of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindTaskCommand extends Command {
    public static final String COMMAND_WORD = "rfind";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks according to different prefix.\n"
            + "'h/' for finding tasks whose heading contains any one of "
            + "the specified words (case-insensitive) and displays them as a list with index numbers.\n"
            + "'dt/' for finding tasks which has the same date as specified by user input.\n"
            + "'tm/' for finding tasks which has the same time as specified by user input.\n"
            + "You should supply only one of the three prefixes.\n"
            + "Parameters:\n"
            + "h/WORD [WORD]...\n"
            + "dt/DATE\n"
            + "tm/TIME\n"
            + "Example: " + COMMAND_WORD + " h/important fact OR " + COMMAND_WORD + " dt/31/12/2019 OR " + COMMAND_WORD
            + " tm/1300";

    public static final String MESSAGE_EMPTY_STRING = "Please supply non-empty keywords after prefix 'h/'";
    public static final String MESSAGE_EMPTY_DATE = "Please supply a date after prefix 'dt/'";
    public static final String MESSAGE_EMPTY_TIME = "Please supply a time after prefix 'tm/'";

    private final TaskPredicate predicate;

    public FindTaskCommand(TaskPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTaskList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, model.getFilteredTaskList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTaskCommand // instanceof handles nulls
                && predicate.equals(((FindTaskCommand) other).predicate)); // state check
    }
}
