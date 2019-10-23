package seedu.address.logic.calendar.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKDAY;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKDEADLINE;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKDESCRIPTION;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKTAG;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKTIME;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKTITLE;
import static seedu.address.model.calendar.CalendarModel.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.calendar.commands.exceptions.CommandException;
import seedu.address.model.calendar.CalendarModel;
import seedu.address.model.calendar.tag.TaskTag;
import seedu.address.model.calendar.task.Task;
import seedu.address.model.calendar.task.TaskDay;
import seedu.address.model.calendar.task.TaskDeadline;
import seedu.address.model.calendar.task.TaskDescription;
import seedu.address.model.calendar.task.TaskTime;
import seedu.address.model.calendar.task.TaskTitle;

/**
 * Edits the details of an existing task in the taskTime book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the displayed task list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TASKTITLE + "TITLE] "
            + "[" + PREFIX_TASKDAY + "DAY] "
            + "[" + PREFIX_TASKDESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_TASKDEADLINE + "DEADLINE] "
            + "[" + PREFIX_TASKTIME + "TIME] "
            + "[" + PREFIX_TASKTAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TASKDAY + "12/02/2019 "
            + PREFIX_TASKDESCRIPTION + "Submit softcopy only";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This task already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the task in the filtered task list to edit
     * @param editPersonDescriptor details to edit the task with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(CalendarModel calendarModel) throws CommandException {
        requireNonNull(calendarModel);
        List<Task> lastShownList = calendarModel.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
        }

        Task taskToEdit = lastShownList.get(index.getZeroBased());
        Task editedTask = createEditedPerson(taskToEdit, editPersonDescriptor);

        if (!taskToEdit.isSameTask(editedTask) && calendarModel.hasTask(editedTask)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        calendarModel.setTask(taskToEdit, editedTask);
        calendarModel.updateFilteredTaskList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedTask));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Task createEditedPerson(Task taskToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert taskToEdit != null;

        TaskTitle updatedTaskTitle = editPersonDescriptor.getTaskTitle().orElse(taskToEdit.getTaskTitle());
        TaskDay updatedTaskDay = editPersonDescriptor.getTaskDay().orElse(taskToEdit.getTaskDay());
        TaskDescription updatedTaskDescription = editPersonDescriptor.getTaskDescription()
                .orElse(taskToEdit.getTaskDescription());
        TaskDeadline updatedTaskDeadline = editPersonDescriptor.getTaskDeadline()
                .orElse(taskToEdit.getTaskDeadline());
        TaskTime updatedTaskTime = editPersonDescriptor.getTaskTime().orElse(taskToEdit.getTaskTime());
        Set<TaskTag> updatedTaskTags = editPersonDescriptor.getTaskTags().orElse(taskToEdit.getTaskTags());

        return new Task(updatedTaskTitle, updatedTaskDay, updatedTaskDescription, updatedTaskDeadline,
            updatedTaskTime, updatedTaskTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditPersonDescriptor {
        private TaskTitle taskTitle;
        private TaskDay taskDay;
        private TaskDescription taskDescription;
        private TaskDeadline taskDeadline;
        private TaskTime taskTime;
        private Set<TaskTag> taskTags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code taskTags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setTaskTitle(toCopy.taskTitle);
            setTaskDay(toCopy.taskDay);
            setTaskDescription(toCopy.taskDescription);
            setTaskDeadline(toCopy.taskDeadline);
            setTaskTime(toCopy.taskTime);
            setTaskTags(toCopy.taskTags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(taskTitle, taskDay, taskDescription, taskTime, taskTags);
        }

        public void setTaskTitle(TaskTitle taskTitle) {
            this.taskTitle = taskTitle;
        }

        public Optional<TaskTitle> getTaskTitle() {
            return Optional.ofNullable(taskTitle);
        }

        public void setTaskDay(TaskDay taskDay) {
            this.taskDay = taskDay;
        }

        public Optional<TaskDay> getTaskDay() {
            return Optional.ofNullable(taskDay);
        }

        public void setTaskDescription(TaskDescription taskDescription) {
            this.taskDescription = taskDescription;
        }

        public Optional<TaskDescription> getTaskDescription() {
            return Optional.ofNullable(taskDescription);
        }

        public void setTaskDeadline(TaskDeadline taskDeadline) {
            this.taskDeadline = taskDeadline;
        }

        public Optional<TaskDeadline> getTaskDeadline() {
            return Optional.ofNullable(taskDeadline);
        }

        public void setTaskTime(TaskTime taskTime) {
            this.taskTime = taskTime;
        }

        public Optional<TaskTime> getTaskTime() {
            return Optional.ofNullable(taskTime);
        }

        /**
         * Sets {@code taskTags} to this object's {@code taskTags}.
         * A defensive copy of {@code taskTags} is used internally.
         */
        public void setTaskTags(Set<TaskTag> taskTags) {
            this.taskTags = (taskTags != null) ? new HashSet<>(taskTags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code taskTags} is null.
         */
        public Optional<Set<TaskTag>> getTaskTags() {
            return (taskTags != null) ? Optional.of(Collections.unmodifiableSet(taskTags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getTaskTitle().equals(e.getTaskTitle())
                    && getTaskDay().equals(e.getTaskDay())
                    && getTaskDeadline().equals(e.getTaskDeadline())
                    && getTaskDescription().equals(e.getTaskDescription())
                    && getTaskTime().equals(e.getTaskTime())
                    && getTaskTags().equals(e.getTaskTags());
        }
    }
}
