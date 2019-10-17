package seedu.jarvis.logic.commands.course;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.logic.parser.CliSyntax.CourseSyntax.PREFIX_COURSE;

import java.util.Objects;

import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.course.Course;

/**
 * Adds a course into Jarvis.
 */
public class AddCourseCommand extends Command {
    public static final String COMMAND_WORD = "add-course";

    public static final String MESSAGE_USAGE = String.format(
        "%s: lets the user take this course. Parameters: %sCOURSE\n"
        + "Example: %s %sCS2103T or %s %s CS3230",
        COMMAND_WORD,
        PREFIX_COURSE,
        COMMAND_WORD,
        PREFIX_COURSE,
        COMMAND_WORD,
        PREFIX_COURSE
    );

    public static final String MESSAGE_SUCCESS = "New Course added: %1$s";
    public static final String MESSAGE_DUPLICATE_COURSE = "%s is already in your list!";
    public static final String MESSAGE_COURSE_NOT_FOUND = "%s: The given course could not be found!";
    public static final String MESSAGE_INVERSE_SUCCESS_DELETE = "Deleted course: %1$s";
    public static final String MESSAGE_INVERSE_COURSE_NOT_FOUND = "Course already deleted: %1$s";
    public static final String REPRESENTATION = "%s: %s";

    public static final boolean HAS_INVERSE = true;

    private final Course toAdd;

    public AddCourseCommand(Course course) {
        requireNonNull(course);
        toAdd = course;
    }

    /**
     * Gets the {@code Course} to be added in this {@code AddCourseCommand}.
     *
     * @return {@code Course} to be added
     */
    public Course getCourseToAdd() {
        return toAdd;
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
        return true;
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

        if (model.hasCourse(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_COURSE);
        }

        if (!CourseUtil.courseExists(toAdd.getCourseCode().toString())) {
            throw new CommandException(MESSAGE_COURSE_NOT_FOUND);
        }

        model.addCourse(toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
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

        if (!model.hasCourse(toAdd)) {
            throw new CommandException(String.format(MESSAGE_INVERSE_COURSE_NOT_FOUND, toAdd));
        }

        model.deleteCourse(toAdd);

        return new CommandResult(String.format(MESSAGE_INVERSE_SUCCESS_DELETE, toAdd));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        } else {
            AddCourseCommand that = (AddCourseCommand) o;
            return Objects.equals(toAdd, that.toAdd);
        }
    }

    @Override
    public String toString() {
        return String.format(REPRESENTATION, COMMAND_WORD, toAdd.getCourseCode().toString());
    }
}
