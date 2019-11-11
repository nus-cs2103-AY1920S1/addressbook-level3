package seedu.address.logic.commands.calendar;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.task.TaskContainsDatePredicate;

/**
 * Lists all tasks in the address book on a certain date to the user.
 */
public class ListTasksBasedOnDateCommand extends Command {
    public static final String COMMAND_WORD = "find_task_by_date";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks on the specific date.\n "
            + "Parameters: DATE\n"
            + "Example: " + COMMAND_WORD + " 20/10/2019";

    private final TaskContainsDatePredicate predicate;

    public ListTasksBasedOnDateCommand(TaskContainsDatePredicate predicate) {
        this.predicate = predicate;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTaskList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, model.getFilteredTaskList().size()),
                false, false, false, false, true,
                false, false, false);
    }
}
