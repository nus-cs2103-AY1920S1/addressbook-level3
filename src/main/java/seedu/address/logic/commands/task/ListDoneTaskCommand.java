package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Displays all tasks that have been done.
 */
public class ListDoneTaskCommand extends Command {
    public static final String COMMAND_WORD = "finished";

    public static final String MESSAGE_SUCCESS = "Listed all tasks that have been done";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_DONE_TASKS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
