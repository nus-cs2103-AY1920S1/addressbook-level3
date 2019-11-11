package seedu.pluswork.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_INDEX;
import static seedu.pluswork.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.List;

import seedu.pluswork.commons.core.Messages;
import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.task.Task;

/**
 * Deletes a task identified using it's displayed index from the address book.
 */
public class DeleteTaskCommand extends Command {

    public static final String COMMAND_WORD = "delete-task";
    public static final String PREFIX_USAGE = PREFIX_TASK_INDEX.getPrefix();

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the displayed task list.\n"
            + "Parameters:" + PREFIX_TASK_INDEX + "TASK INDEX\n"
            + "Example: " + COMMAND_WORD + "ti/1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    private final Index targetIndex;

    public DeleteTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredTasksList(PREDICATE_SHOW_ALL_TASKS);
        List<Task> lastShownList = model.getFilteredTasksList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteTask(taskToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTaskCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteTaskCommand) other).targetIndex)); // state check
    }
}
