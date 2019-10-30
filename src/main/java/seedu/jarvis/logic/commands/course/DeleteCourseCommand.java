package seedu.jarvis.logic.commands.course;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.jarvis.logic.parser.CliSyntax.CourseSyntax.PREFIX_COURSE;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.course.Course;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.course.JsonAdaptedDeleteCourseCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;

/**
 * Delete a course into Jarvis.
 */
public class DeleteCourseCommand extends Command {
    public static final String COMMAND_WORD = "delete-course";

    public static final String MESSAGE_USAGE = String.format(
        "%s: Deletes a course identified by either the course-code or index number used in the"
            + "displayed course list.\n"
            + "Parameters: INDEX (must be a positive integer) | %sCOURSE\n"
            + "Example: %s 1, %s 2, %s %sCS2102",
        COMMAND_WORD, PREFIX_COURSE, COMMAND_WORD, COMMAND_WORD, COMMAND_WORD, PREFIX_COURSE
    );

    public static final String MESSAGE_SUCCESS = "Course deleted: %1$s";
    public static final String MESSAGE_COURSE_NOT_IN_LIST = "The given course is not inside your list!";
    public static final String MESSAGE_INVERSE_SUCCESS_ADD = "Re-added course: %1$s";
    public static final String MESSAGE_INVERSE_COURSE_ALREADY_EXISTS = "The course given is already in your list!";
    public static final String REPRESENTATION = "%s: %s";

    public static final boolean HAS_INVERSE = true;

    private Course toDelete;
    private Index targetIndex;

    public DeleteCourseCommand(Course toDelete, Index targetIndex) {
        this.toDelete = toDelete;
        this.targetIndex = targetIndex;
    }

    public DeleteCourseCommand(Index targetIndex) {
        this(null, targetIndex);
        requireNonNull(targetIndex);
    }

    public DeleteCourseCommand(Course toDelete) {
        this(toDelete, null);
        requireNonNull(toDelete);
    }

    /**
     * Gets the command word of the command.
     *
     * @return {@code String} representation of the command word.
     */
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    /**
     * Returns whether the command has an inverse execution.
     * If the command has no inverse execution, then calling {@code executeInverse}
     * will be guaranteed to always throw a {@code CommandException}.
     *
     * @return Whether the command has an inverse execution.
     */
    @Override
    public boolean hasInverseExecution() {
        return HAS_INVERSE;
    }

    /**
     * Gets the {@code Index} of the {@code Course} to be deleted.
     *
     * @return {@code Index} of the {@code Course} to be deleted.
     */
    public Optional<Index> getTargetIndex() {
        return Optional.ofNullable(targetIndex);
    }

    /**
     * Gets the {@code Course} that was deleted, which is null if the person has not been deleted yet.
     *
     * @return {@code Optional} of {@code Course} that was deleted, empty if person has not been deleted.
     */
    public Optional<Course> getDeletedCourse() {
        return Optional.ofNullable(toDelete);
    }

    /**
     * Deletes a {@code Course} to the course planner, if the course is already
     * inside course planner.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that course was deleted successfully
     * @throws CommandException if there is no matching course or index is out of bounds
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Course> lastShownList = model.getUnfilteredCourseList();
        if (isNull(targetIndex)) {
            targetIndex = getIndexToDeleteByCourse(lastShownList);
        } else {
            toDelete = getCourseToDeleteByIndex(lastShownList);
        }

        assert !isNull(targetIndex) && !isNull(toDelete);

        model.deleteCourse(toDelete);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete));
    }

    private Course getCourseToDeleteByIndex(List<Course> lastShownList) throws CommandException {
        assert targetIndex != null;
        int zeroBasedIndex = targetIndex.getZeroBased();
        if (zeroBasedIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COURSE_DISPLAYED_INDEX);
        }
        return lastShownList.get(zeroBasedIndex);
    }

    private Index getIndexToDeleteByCourse(List<Course> lastShownList) throws CommandException {
        assert toDelete != null;
        int index = lastShownList.indexOf(toDelete);
        if (index < 0) {
            throw new CommandException(MESSAGE_COURSE_NOT_IN_LIST);
        }
        return Index.fromZeroBased(index);
    }

    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        requireAllNonNull(model, toDelete);
        if (model.hasCourse(toDelete)) {
            throw new CommandException(String.format(MESSAGE_INVERSE_COURSE_ALREADY_EXISTS, toDelete));
        }
        model.addCourse(targetIndex.getZeroBased(), toDelete);
        return new CommandResult(String.format(MESSAGE_INVERSE_SUCCESS_ADD, toDelete));
    }

    /**
     * Gets a {@code JsonAdaptedCommand} from a {@code Command} for local storage purposes.
     *
     * @return {@code JsonAdaptedCommand}.
     * @throws InvalidCommandToJsonException If command should not be adapted to JSON format.
     */
    @Override
    public JsonAdaptedCommand adaptToJsonAdaptedCommand() throws InvalidCommandToJsonException {
        return new JsonAdaptedDeleteCourseCommand(this);
    }

    @Override
    public String toString() {
        if (isNull(targetIndex)) {
            return String.format(REPRESENTATION, COMMAND_WORD, toDelete);
        }
        return String.format(REPRESENTATION, COMMAND_WORD, targetIndex.getOneBased());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof DeleteCourseCommand)) {
            return false;
        }
        DeleteCourseCommand other = (DeleteCourseCommand) o;
        return Objects.equals(targetIndex, other.targetIndex) && Objects.equals(toDelete, other.toDelete);
    }
}
