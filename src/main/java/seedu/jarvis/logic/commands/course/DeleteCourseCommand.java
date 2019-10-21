package seedu.jarvis.logic.commands.course;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.jarvis.logic.parser.CliSyntax.CourseSyntax.PREFIX_COURSE;

import java.util.List;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.course.Course;

public class DeleteCourseCommand extends Command {
    public static final String COMMAND_WORD = "delete-course";

    public static final String MESSAGE_USAGE = String.format(
        "%s: Deletes a course identified by either the course-code or index number used in the"
            + "displayed course list.\n"
            + "Parameters: INDEX (must be a positive integer) | %sCOURSE\n"
            + "Example: %s 1, %s 2, %s %sCS2102",
        COMMAND_WORD, PREFIX_COURSE, COMMAND_WORD, COMMAND_WORD, COMMAND_WORD, PREFIX_COURSE
    );

    public static final String MESSAGE_SUCCESS =
        "Course deleted: %1$s";
    public static final String MESSAGE_COURSE_NOT_IN_LIST =
        "The given course is not inside your list!";
    public static final String MESSAGE_INVERSE_SUCCESS_ADD =
        "Re-added course: %1$s";
    public static final String MESSAGE_INVERSE_COURSE_ALREADY_EXISTS =
        "The course given is already in your list!";
    public static final String REPRESENTATION = "%s: %s";

    public static final boolean HAS_INVERSE = true;

    private Course toDelete;
    private Index targetIndex;

    private DeleteCourseCommand(Course toDelete, Index targetIndex) {
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

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public boolean hasInverseExecution() {
        return HAS_INVERSE;
    }

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
        return new CommandResult(String.format(MESSAGE_SUCCESS));
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

    @Override
    public String toString() {
        if (isNull(targetIndex)) {
            return String.format(REPRESENTATION, COMMAND_WORD, toDelete);
        }
        return String.format(REPRESENTATION, COMMAND_WORD, targetIndex.getOneBased());
    }
}
