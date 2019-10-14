package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;

/**
 * Edits the details of an existing task in the address book.
 */
public class DoingTaskCommand extends Command {

    public static final String COMMAND_WORD = "doing-task";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a task as <In Progress> "
            + "by the index number used in the displayed task list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TASK_INDEX + "TASK_INDEX\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_DOING_TASK_SUCCESS = "Updated Task to <In Progress>: %1$s";
    public static final String MESSAGE_TASK_ALREADY_IN_PROGRESS = "This task is already <In Progress>";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";

    private final Index index;

    /**
     * @param index of the task in the filtered task list to edit
     */
    public DoingTaskCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTasksList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToUpdate = lastShownList.get(index.getZeroBased());
        Task updatedTask = createUpdatedTask(taskToUpdate);

        if (!taskToUpdate.isSameTask(updatedTask) && model.hasTask(updatedTask)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.setTask(taskToUpdate, updatedTask);
        model.updateFilteredTasksList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_DOING_TASK_SUCCESS, updatedTask));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToUpdate}
     * where TaskStatus is updated to 'In Progress".
     */
    private static Task createUpdatedTask(Task taskToUpdate) throws CommandException {
        assert taskToUpdate != null;

        Name name = taskToUpdate.getName();
        TaskStatus taskStatus = taskToUpdate.getTaskStatus();
        Set<Tag> tags = taskToUpdate.getTags();
        if (taskStatus == TaskStatus.DOING) {
            throw new CommandException(MESSAGE_TASK_ALREADY_IN_PROGRESS);
        }
        return new Task(name, TaskStatus.DOING, tags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DoingTaskCommand)) {
            return false;
        }

        // state check
        DoingTaskCommand e = (DoingTaskCommand) other;
        return index.equals(e.index);
    }
}
