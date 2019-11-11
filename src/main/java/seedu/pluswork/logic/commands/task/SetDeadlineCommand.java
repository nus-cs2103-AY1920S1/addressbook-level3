package seedu.pluswork.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_INDEX;
import static seedu.pluswork.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import seedu.pluswork.commons.core.Messages;
import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.commons.util.DateTimeUtil;
import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.tag.Tag;
import seedu.pluswork.model.task.Name;
import seedu.pluswork.model.task.Task;
import seedu.pluswork.model.task.TaskStatus;

/**
 * Edits the details of an existing task in the address book.
 */
public class SetDeadlineCommand extends Command {

    public static final String COMMAND_WORD = "set-deadline";
    public static final String PREFIX_USAGE = PREFIX_TASK_INDEX + " " + PREFIX_DEADLINE;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets a deadline for a task "
            + "by the index number used in the displayed task list.\n"
            + "Parameters: "
            + PREFIX_TASK_INDEX + "TASK_INDEX "
            + PREFIX_DEADLINE + DateTimeUtil.DEFAULT_INPUT_FORMAT_MESSAGE + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK_INDEX + " 2 "
            + PREFIX_DEADLINE + " 10-10-2019 18:00";

    public static final String MESSAGE_SET_DEADLINE_TASK_SUCCESS = "Set a deadline for the Task: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in +Work.";
    //Create an alert box when task already has a deadline
    public static final String MESSAGE_TASK_ALREADY_HAS_DEADLINE = "This task already has a deadline, please use "
            + "edit-task to edit or remove the deadline";

    private final Index index;
    private final LocalDateTime dateTime;

    /**
     * @param index    of the task in the filtered task list to edit
     * @param dateTime deadline of the task
     */
    public SetDeadlineCommand(Index index, LocalDateTime dateTime) {
        requireNonNull(index);
        requireNonNull(dateTime);

        this.index = index;
        this.dateTime = dateTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTasksList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToUpdate = lastShownList.get(index.getZeroBased());

        // editing of deadlines should be performed by the {@see EditTaskDeadline}
        if (taskToUpdate.hasDeadline()) {
            throw new CommandException(MESSAGE_TASK_ALREADY_HAS_DEADLINE);
        }

        Task updatedTask = createUpdatedTask(taskToUpdate, dateTime);

        if (!taskToUpdate.isSameTask(updatedTask) && model.hasTask(updatedTask)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.setTask(taskToUpdate, updatedTask);
        model.updateFilteredTasksList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_SET_DEADLINE_TASK_SUCCESS, updatedTask));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToUpdate}
     * where TaskStatus is updated to 'In Progress".
     */
    private static Task createUpdatedTask(Task taskToUpdate, LocalDateTime dateTime) throws CommandException {
        assert taskToUpdate != null;
        assert !taskToUpdate.hasDeadline();

        Name name = taskToUpdate.getName();
        TaskStatus taskStatus = taskToUpdate.getTaskStatus();
        Set<Tag> tags = taskToUpdate.getTags();
        Task updatedTask = new Task(name, taskStatus, tags);
        updatedTask.setDeadline(dateTime);
        return updatedTask;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetDeadlineCommand)) {
            return false;
        }

        // state check
        SetDeadlineCommand e = (SetDeadlineCommand) other;
        return index.equals(e.index)
                && dateTime.equals(e.dateTime);
    }
}
