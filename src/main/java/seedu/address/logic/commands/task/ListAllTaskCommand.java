package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Displays all tasks in the list.
 */
public class ListAllTaskCommand extends Command {
    public static final String COMMAND_WORD = "rlist";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
