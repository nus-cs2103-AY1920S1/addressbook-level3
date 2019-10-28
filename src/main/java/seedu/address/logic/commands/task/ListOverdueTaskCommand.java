package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class ListOverdueTaskCommand extends Command {
    public static final String COMMAND_WORD = "overdue";

    public static final String MESSAGE_SUCCESS = "Listed all overdue tasks";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_NO_TASKS);
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_OVERDUE_TASKS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
