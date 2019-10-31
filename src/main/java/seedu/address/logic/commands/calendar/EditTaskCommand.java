package seedu.address.logic.commands.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MARKING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.classid.ClassId;
import seedu.address.model.task.Marking;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskTime;

/**
 * command to update the existing tasks.
 */
public class EditTaskCommand extends Command {
    public static final String COMMAND_WORD = "edit_task";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the displayed task list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_MARKING + "Y] "
            + "[" + PREFIX_TASK_TIME + "19/10/2019 12:00, 19/10/2019 14:00] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_MARKING + "N";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task:\n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";

    private final Index index;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
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
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToEdit = lastShownList.get(index.getZeroBased());
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

        if (!taskToEdit.isSameTask(editedTask) && model.hasTask(editedTask)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.setTask(taskToEdit, editedTask);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(Task taskToEdit, EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        ClassId updatedClassId = editTaskDescriptor.getClassId().orElse(taskToEdit.getClassId());
        Marking updatedMarking = editTaskDescriptor.getMarking()
                .orElse(taskToEdit.getMarking());
        TreeSet<TaskTime> updatedTimes = editTaskDescriptor.getTaskTimes().orElse(taskToEdit.getTime());

        return new Task(updatedClassId, updatedTimes, updatedMarking);
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
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditTaskDescriptor {
        private ClassId classId;
        private Marking marking;
        private TreeSet<TaskTime> taskTimes;

        public EditTaskDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code taskTimes} is used internally.
         */
        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            setClassId(toCopy.classId);
            setMarking(toCopy.marking);
            setTaskTimes(toCopy.taskTimes);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(classId, marking, taskTimes);
        }

        public void setMarking(Marking marking) {
            this.marking = marking;
        }

        public Optional<Marking> getMarking() {
            return Optional.ofNullable(marking);
        }

        public void setTaskTimes(TreeSet<TaskTime> taskTimes) {
            this.taskTimes = (taskTimes != null) ? new TreeSet<>(taskTimes) : null;
        }

        public Optional<TreeSet<TaskTime>> getTaskTimes() {
            return (taskTimes != null) ? Optional.of(taskTimes) : Optional.empty();
        }

        public void setClassId(ClassId classId) {
            this.classId = classId;
        }

        public Optional<ClassId> getClassId() {
            return Optional.ofNullable(classId);
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

            return getClassId().equals(e.getClassId())
                    && getMarking().equals(e.getMarking())
                    && getTaskTimes().equals(e.getTaskTimes());
        }
    }
}
