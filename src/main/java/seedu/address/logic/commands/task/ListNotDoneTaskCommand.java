package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

public class ListNotDoneTaskCommand extends Command {
    public static final String COMMAND_WORD = "unfinished";

    public static final String MESSAGE_SUCCESS = "Listed all tasks that are yet to be done";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTaskList(Model.PREDICATE_SHOW_NOT_DONE_TASKS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
