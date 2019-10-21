package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Clears all tasks in the revision task list.
 */
public class ClearTaskCommand extends Command {
    public static final String COMMAND_WORD = "rclear";
    public static final String MESSAGE_SUCCESS = "All tasks have been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.clearTaskList();
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
