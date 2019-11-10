package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Description;
import seedu.address.model.project.Project;
import seedu.address.model.project.Task;
import seedu.address.model.project.Time;
import seedu.address.model.util.SortingOrder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_CHECKED_OUT;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROJECTS;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditTaskCommand extends Command {

    public static final String COMMAND_WORD = "editTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the displayed task list. "
            + "Existing values will be overwritten by the input values.\n"
            + "When editing a task, if d/ is not in the input, task will automatically become undone.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_TIME + "TIME] "
            + "[" + PREFIX_DONE + "] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DESCRIPTION + "Finish GUI "
            + PREFIX_TIME + "09/09/2019 1700";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";

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

        if (!model.isCheckedOut()) {
            throw new CommandException(MESSAGE_NOT_CHECKED_OUT);
        }

        Project projectToEdit = model.getWorkingProject().get();
        List<Task> taskSet = projectToEdit.getTasks();
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.addAll(taskSet);

        if (index.getZeroBased() >= taskSet.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToEdit = taskList.remove(index.getZeroBased());
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);
        model.editTaskInAllPersons(taskToEdit, editedTask, projectToEdit);
        taskList.add(index.getZeroBased(), editedTask);
        Collections.sort(taskList, SortingOrder.getCurrentSortingOrderForTask());


        Project editedProject = new Project(projectToEdit.getTitle(), projectToEdit.getDescription(), projectToEdit.getMemberNames(), taskList, projectToEdit.getFinance(), projectToEdit.getGeneratedTimetable());
        editedProject.setListOfMeeting(projectToEdit.getListOfMeeting());

        model.setProject(projectToEdit, editedProject);
        model.updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask), COMMAND_WORD);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Task createEditedTask(Task taskToEdit, EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Description updatedDescription = editTaskDescriptor.getDescription().orElse(taskToEdit.getDescription());
        Time updatedTime = editTaskDescriptor.getTime().orElse(taskToEdit.getTime());
        boolean updatedIsDone = editTaskDescriptor.getIsDone().orElse(taskToEdit.isDone());

        return new Task(updatedDescription, updatedTime, updatedIsDone);
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
        EditTaskCommand e = (EditTaskCommand) other;
        return index.equals(e.index)
                && editTaskDescriptor.equals(e.editTaskDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditTaskDescriptor {
        private Description description;
        private Time time;
        private boolean isDone;

        public EditTaskDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            setDescription(toCopy.description);
            setTime(toCopy.time);
            setIsDone(toCopy.isDone);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(description, time, isDone);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setTime(Time time) {
            this.time = time;
        }

        public Optional<Time> getTime() {
            return Optional.ofNullable(time);
        }

        public void setIsDone(boolean isDone) {
            this.isDone = isDone;
        }

        public Optional<Boolean> getIsDone() {
            return Optional.ofNullable(isDone);
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

            return getDescription().equals(e.getDescription())
                    && getIsDone() == (e.getIsDone())
                    && getTime().equals(e.getTime());
        }
    }
}
