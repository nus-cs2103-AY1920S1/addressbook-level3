package seedu.pluswork.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_INDEX;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_STATUS;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_TAG;
import static seedu.pluswork.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.pluswork.commons.core.Messages;
import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.commons.util.CollectionUtil;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.tag.Tag;
import seedu.pluswork.model.task.Name;
import seedu.pluswork.model.task.Task;
import seedu.pluswork.model.task.TaskStatus;

/**
 * Edits the details of an existing task in the address book.
 */
public class EditTaskCommand extends Command {

    public static final String COMMAND_WORD = "edit-task";
    public static final String PREFIX_USAGE = PREFIX_TASK_INDEX + " " + PREFIX_TASK_NAME + " " + PREFIX_TASK_TAG + " "
            + PREFIX_TASK_STATUS;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the displayed task list. "
            + "Existing values will be overwritten by the input values. "
            + "However, a deadline can only be removed or edited using this command. "
            + "To set one, please use set-deadline!\n"
            + "Parameters:" + PREFIX_TASK_INDEX + "TASK INDEX"
            + PREFIX_TASK_NAME + "NAME] "
            + PREFIX_TASK_STATUS + "STATUS "
            + PREFIX_DEADLINE + "DEADLINE "
            + PREFIX_TASK_TAG + "TAG...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TASK_INDEX + "1 " + PREFIX_TASK_NAME + " new name";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_NOT_EDITED_DEADLINE = "Please do not use this command to set a deadline!\n"
            + "Use set-deadline instead";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";

    private final Index index;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param index              of the task in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public EditTaskCommand(Index index, EditTaskDescriptor editTaskDescriptor) {
        requireNonNull(index);
        requireNonNull(editTaskDescriptor);

        this.index = index;
        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTasksList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToEdit = lastShownList.get(index.getZeroBased());

        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

        if (!taskToEdit.isSameTask(editedTask) && model.hasTask(editedTask)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.setTask(taskToEdit, editedTask);
        model.updateFilteredTasksList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(Task taskToEdit, EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;
        assert taskToEdit.hasDeadline();

        Name updatedName = editTaskDescriptor.getName().orElse(taskToEdit.getName());
        TaskStatus updatedTaskStatus = editTaskDescriptor.getTaskStatus().orElse(taskToEdit.getTaskStatus());
        Optional<LocalDateTime> deadlineToUpdate = editTaskDescriptor.getDeadline();
        LocalDateTime updatedDeadline = deadlineToUpdate.orElse(null);
        Instant timeStart;

        if (taskToEdit.getTaskStatus().equals(TaskStatus.UNBEGUN) && updatedTaskStatus.equals(TaskStatus.DOING)) {
            timeStart = Instant.now();
        } else {
            timeStart = editTaskDescriptor.getTimeStart().orElse(taskToEdit.getTimeStart());
        }

        Instant timeEnd;
        if (taskToEdit.getTaskStatus().equals(TaskStatus.DOING) && updatedTaskStatus.equals(TaskStatus.DONE)) {
            timeEnd = Instant.now();
        } else {
            timeEnd = editTaskDescriptor.getTimeEnd().orElse(taskToEdit.getTimeEnd());
        }


        Set<Tag> updatedTags = editTaskDescriptor.getTags().orElse(taskToEdit.getTags());

        Task newTask = new Task(updatedName, updatedTaskStatus, updatedTags, updatedDeadline);
        newTask.setTimeStart(timeStart);
        newTask.setTimeEnd(timeEnd);

        return newTask;

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTaskCommand)) {
            return false;
        }

        // state check
        EditTaskCommand e = (EditTaskCommand) other;
        return index.equals(e.index)
                && editTaskDescriptor.equals(e.editTaskDescriptor);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Name name;
        private TaskStatus taskStatus;
        private Set<Tag> tags;
        private Instant timeStart;
        private Instant timeEnd;
        private LocalDateTime deadline;

        public EditTaskDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            setName(toCopy.name);
            setTaskStatus(toCopy.taskStatus);
            setTags(toCopy.tags);
            setDeadline(toCopy.deadline);
            setTimeStart(toCopy.timeStart);
            setTimeEnd(toCopy.timeEnd);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, taskStatus, tags, deadline);
        }

        public void setTaskStatus(TaskStatus taskStatus) {
            this.taskStatus = taskStatus;
        }

        public Optional<TaskStatus> getTaskStatus() {
            return Optional.ofNullable(taskStatus);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setDeadline(LocalDateTime dateTime) {
            this.deadline = dateTime;
        }

        public Optional<LocalDateTime> getDeadline() {
            return Optional.ofNullable(deadline);
        }


        public void setTimeStart(Instant start) {
            this.timeStart = start;
        }

        public Optional<Instant> getTimeStart() {
            return Optional.ofNullable(timeStart);
        }

        public void setTimeEnd(Instant end) {
            this.timeEnd = end;
        }

        public Optional<Instant> getTimeEnd() {
            return Optional.ofNullable(timeEnd);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTaskDescriptor)) {
                return false;
            }

            // state check
            EditTaskDescriptor e = (EditTaskDescriptor) other;

            return getName().equals(e.getName())
                    && (getTaskStatus().equals(e.getTaskStatus()))
                    && getTags().equals(e.getTags())
                    && getDeadline().equals((e.getDeadline()));
        }
    }
}
