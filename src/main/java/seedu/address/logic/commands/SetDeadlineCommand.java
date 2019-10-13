package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.time.LocalDateTime;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;

/**
 * Edits the details of an existing task in the address book.
 */
public class SetDeadlineCommand extends Command {

    public static final String COMMAND_WORD = "set-deadline";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets a deadline for a task "
            + "by the index number used in the displayed task list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TASK_INDEX + "TASK_INDEX"
            + PREFIX_DEADLINE + "YYYY-MM-DDTHH:MM:SS\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK_INDEX + " 2 "
            + PREFIX_DEADLINE + " 2015-02-20T06:30:00";

    public static final String MESSAGE_SET_DEADLINE_TASK_SUCCESS = "Set a deadline for the Task: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in +Work.";
    //Create an alert box when task already has a deadline
    public static final String MESSAGE_TASK_ALREADY_HAS_DEADLINE = "This task already has a deadline";

    private final Index index;
    private final LocalDateTime dateTime;

    /**
     * @param index of the task in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
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

        Name name = taskToUpdate.getName();
        TaskStatus taskStatus = taskToUpdate.getTaskStatus();
        Set<Tag> tags = taskToUpdate.getTags();
        Task updatedTask = new Task(name, TaskStatus.DOING, tags);
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
