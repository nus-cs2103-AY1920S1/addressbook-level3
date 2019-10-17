package seedu.jarvis.logic.commands.course;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.address.AddAddressCommand;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.course.Course;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.jarvis.logic.parser.CliSyntax.CourseSyntax.PREFIX_COURSE;

public class DeleteCourseCommand extends Command {
    public static final String COMMAND_WORD = "delete-course";

    public static final String MESSAGE_USAGE = String.format(
        "%s: deletes the course from the user's course planner. Parameters: %sCOURSE\n"
        + "Example: %s %sCS2103T or %s %s CS3230",
        COMMAND_WORD,
        PREFIX_COURSE,
        COMMAND_WORD,
        PREFIX_COURSE,
        COMMAND_WORD,
        PREFIX_COURSE
    );

    public static final String MESSAGE_INVALID_INDEX = "The course index given is invalid.";
    public static final String MESSAGE_SUCCESS = "Deleted course: %1$s";
    public static final String MESSAGE_INVERSE_SUCCESS_ADD = AddCourseCommand.MESSAGE_SUCCESS;
    public static final String MESSAGE_INVERSE_COURSE_TO_ADD_ALREADY_EXIST = AddCourseCommand.MESSAGE_DUPLICATE_COURSE;
    public static final String MESSAGE_COURSE_NOT_FOUND = AddCourseCommand.MESSAGE_COURSE_NOT_FOUND;
    public static final String MESSAGE_DUPLICATE_COURSE = ""; // TODO
    public static final String REPRESENTATION = "%s: %s";

    public static final boolean HAS_INVERSE = true;

    private Index targetIndex;
    private Course deletedCourse;

    /**
     * Gets the {@code Course} to be added in this {@code AddCourseCommand}.
     *
     * @return {@code Course} to be added
     */
    public Optional<Course> getDeletedCourse() {
        return Optional.ofNullable(deletedCourse);
    }

    public DeleteCourseCommand(Index targetIndex, Course deletedCourse) {
        this.targetIndex = targetIndex;
        this.deletedCourse = deletedCourse;
    }

    public DeleteCourseCommand(Index targetIndex) {
        this(targetIndex, null);
    }

    public DeleteCourseCommand(Course deletedCourse) {
        this(null, deletedCourse);
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
     * Adds {@code Course} to the course planner, if the course is not already
     * inside course planner.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that course was added successfully.
     * @throws CommandException If there already is a {@code Course} matching
     * the course to be added in the course planner
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Course> lastShownList = model.getUnfilteredCourseList();

        if (!isNull(targetIndex) && targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        if (!isNull(deletedCourse)) {
            model.deleteCourse(deletedCourse);
        }

        deletedCourse = lastShownList.get(targetIndex.getZeroBased());
        model.deleteCourse(deletedCourse);

        return new CommandResult(String.format(MESSAGE_SUCCESS, deletedCourse));
    }

    /**
     * Deletes the {@code Course} from the course planner that was added by this command's
     * execution if course is still in the course planner.
     *
     * @param model {@code Model} which the command should inversely operate on.
     * @return {@code CommandResult} that course was removed if course was in course planner, else
     * {@code CommandResult} that course was already not in course planner
     * @throws CommandException if the course to be removed is not found in the course planner
     */
    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasCourse(deletedCourse)) {
            throw new CommandException(String.format(MESSAGE_INVERSE_COURSE_TO_ADD_ALREADY_EXIST, deletedCourse));
        }

        if (!CourseUtil.courseExists(deletedCourse.getCourseCode().toString())) {
            throw new CommandException(String.format(MESSAGE_COURSE_NOT_FOUND, deletedCourse.getCourseCode().toString()));
        }

        model.addCourse(targetIndex.getZeroBased(), deletedCourse);

        return new CommandResult(String.format(MESSAGE_INVERSE_SUCCESS_ADD, deletedCourse));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        } else {
            DeleteCourseCommand that = (DeleteCourseCommand) o;
            return Objects.equals(deletedCourse, that.deletedCourse);
        }
    }

    @Override
    public String toString() {
        return String.format(REPRESENTATION, COMMAND_WORD, deletedCourse.getCourseCode().toString());
    }
}
