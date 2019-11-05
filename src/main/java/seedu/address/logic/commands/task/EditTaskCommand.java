package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEADING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.Content;
import seedu.address.model.note.Note;
import seedu.address.model.note.Title;
import seedu.address.model.question.Answer;
import seedu.address.model.question.Difficulty;
import seedu.address.model.question.Question;
import seedu.address.model.question.QuestionBody;
import seedu.address.model.question.Subject;
import seedu.address.model.task.Heading;
import seedu.address.model.task.Task;

/**
 * Edits the details of an existing revision task.
 */
public class EditTaskCommand extends Command {
    public static final String COMMAND_WORD = "redit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the revision identified "
            + "by the index number used in the displayed task list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_HEADING + "HEADING] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_TIME + "TIME]\n"
            + "Example: " + COMMAND_WORD + " 2 "
            + PREFIX_HEADING + "CS2103T UML diagram "
            + PREFIX_DATE + "01/05/2019 "
            + PREFIX_TIME + "0900";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists";
    public static final String MESSAGE_TARGET_NOT_FOUND = "The new heading must be an existing note title or question "
            + "body";

    private final Index index;
    private final EditTaskDescriptor editTaskDescriptor;

    public EditTaskCommand(Index index, EditTaskDescriptor editTaskDescriptor) {
        requireNonNull(index);
        requireNonNull(editTaskDescriptor);

        this.index = index;
        this.editTaskDescriptor = editTaskDescriptor;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToEdit = lastShownList.get(index.getZeroBased());
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor, model);

        if (!taskToEdit.equals(editedTask) && model.hasTask(editedTask)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.setTask(taskToEdit, editedTask);
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(Task taskToEdit, EditTaskDescriptor editTaskDescriptor, Model model)
            throws CommandException {
        assert taskToEdit != null;

        Heading updatedHeading = editTaskDescriptor.getHeading().orElse(taskToEdit.getHeading());
        Note note = new Note(new Title(updatedHeading.toString()), new Content("dummy content"));
        Question question = new Question(new QuestionBody(updatedHeading.toString()), new Answer("dummy answer"),
                new Subject("dummy subject"), new Difficulty("dummy difficulty"));

        if (!(model.hasNote(note) || model.hasQuestion(question))) {
            throw new CommandException(MESSAGE_TARGET_NOT_FOUND);
        }

        LocalDate updatedDate = editTaskDescriptor.getDate().orElse(taskToEdit.getDate());
        LocalTime updatedTime = editTaskDescriptor.getTime().orElse(taskToEdit.getTime());
        boolean isDone = taskToEdit.getStatus();
        return new Task(updatedHeading, updatedDate, updatedTime, isDone);
    }

    /**
     * Stores the details to edit the revision task with.
     * A non-empty heading or date or time will replace the corresponding field value of the task;
     */
    public static class EditTaskDescriptor {
        private Heading heading;
        private LocalDate date;
        private LocalTime time;
        private boolean isDone;

        public EditTaskDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            setHeading(toCopy.heading);
            setDate(toCopy.date);
            setTime(toCopy.time);
            setStatus(toCopy.isDone);


        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(heading, date, time);
        }


        public void setHeading(Heading heading) {
            this.heading = heading;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public void setTime(LocalTime time) {
            this.time = time;
        }

        public void setStatus(boolean isDone) {
            this.isDone = isDone;
        }

        public Optional<Heading> getHeading() {
            return Optional.ofNullable(heading);
        }

        public Optional<LocalDate> getDate() {
            return Optional.ofNullable(date);
        }

        public Optional<LocalTime> getTime() {
            return Optional.ofNullable(time);
        }

        public Optional<Boolean> getStatus() {
            return Optional.of(isDone);
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

            return getHeading().equals(e.getHeading())
                    && getDate().equals(e.getDate())
                    && getTime().equals(e.getTime())
                    && getStatus() == e.getStatus();
        }
    }
}
