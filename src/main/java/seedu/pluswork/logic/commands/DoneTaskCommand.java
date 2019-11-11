package seedu.pluswork.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_INDEX;
import static seedu.pluswork.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import seedu.pluswork.commons.core.Messages;
import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.tag.Tag;
import seedu.pluswork.model.task.Name;
import seedu.pluswork.model.task.Task;
import seedu.pluswork.model.task.TaskStatus;

/**
 * Edits the details of an existing task in the address book.
 */
public class DoneTaskCommand extends Command {

    public static final String COMMAND_WORD = "done-task";
    public static final String PREFIX_USAGE = PREFIX_TASK_INDEX.getPrefix();

    public static final String UPDATED_STATUS = TaskStatus.DONE.getDisplayName();

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks a task as <" + UPDATED_STATUS + "> "
            + "by the index number used in the displayed task list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TASK_INDEX + "TASK_INDEX\n"
            + "Example: " + COMMAND_WORD + " ti/1 ";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Updated Task to <"
            + UPDATED_STATUS + ">: %1$s";
    public static final String MESSAGE_TASK_ALREADY_COMPLETED = "This task is already <"
            + UPDATED_STATUS + ">: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";

    private final Index index;

    /**
     * @param index of the task in the filtered task list to edit
     */
    public DoneTaskCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    public int getIndex() {
        return index.getZeroBased();
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
        if (taskToUpdate.getTags().contains(new Tag("Inventory"))) {
            return new CommandResult("Type-1 /"
                    + taskToUpdate.getName().toString() + "/"
                    + index.getOneBased());
        }
        return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, updatedTask));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToUpdate}
     * where TaskStatus is updated to DONE.
     */
    private static Task createUpdatedTask(Task taskToUpdate) throws CommandException {
        assert taskToUpdate != null;

        Name name = taskToUpdate.getName();
        TaskStatus taskStatus = taskToUpdate.getTaskStatus();
        Set<Tag> tags = taskToUpdate.getTags();
        if (taskStatus == TaskStatus.DONE) {
            throw new CommandException(String.format(MESSAGE_TASK_ALREADY_COMPLETED, taskToUpdate));
        }
        Instant timeStart = taskToUpdate.getTimeStart();

        Task newTask = new Task(name, TaskStatus.DONE, tags);
        newTask.setTimeStart(timeStart);
        newTask.setTimeEnd(Instant.now());

        return newTask;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DoneTaskCommand)) {
            return false;
        }

        // state check
        DoneTaskCommand e = (DoneTaskCommand) other;
        return index.equals(e.index);
    }
}
