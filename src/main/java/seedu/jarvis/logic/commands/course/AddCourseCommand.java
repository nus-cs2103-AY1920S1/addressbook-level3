package seedu.jarvis.logic.commands.course;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.jarvis.logic.parser.CliSyntax.CourseSyntax.PREFIX_COURSE;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        "%s: Adds a number of courses to the user's list. Parameters: [%sCOURSE]...\n"
            + "Example: %s %sCS2103T or %s %sCS3230 %sCS2103T",
        COMMAND_WORD, PREFIX_COURSE, COMMAND_WORD, PREFIX_COURSE,
        COMMAND_WORD, PREFIX_COURSE, PREFIX_COURSE
    );

    public static final String MESSAGE_SUCCESS =
        "New Course(s) added: %1$s";
    public static final String MESSAGE_DUPLICATE_COURSES =
        "All the courses given are already in your list!";
    public static final String MESSAGE_INVERSE_SUCCESS_DELETE =
        "Deleted Course(s): %1$s";
    public static final String MESSAGE_INVERSE_COURSE_NOT_FOUND =
        "Course(s) already deleted: %1$s";
    public static final String REPRESENTATION = "%s: %s";

    public static final boolean HAS_INVERSE = true;

    private final List<Course> toAdd;

    /** List of courses that are actually added, for use by undo/redo */
    private List<Course> hasAdded;

    public AddCourseCommand(List<Course> courses) {
        requireNonNull(courses);
        toAdd = courses;
        hasAdded = null;
    }

    /**
     * Gets the {@code Course} to be added in this {@code AddCourseCommand}.
     *
     * @return {@code Course} to be added
     */
    public List<Course> getCoursesToAdd() {
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

        assert toAdd.stream()
            .allMatch(course -> CourseUtil.courseExists(course.getCourseCode().toString()))
            : "toAdd should not have non-existent courses.";

        assert !toAdd.isEmpty() : "toAdd should not be empty.";

        hasAdded = toAdd.stream()
            .distinct()
            .filter(c -> !model.hasCourse(c)) // remove courses already inside course planner
            .collect(Collectors.toList());

        if (hasAdded.isEmpty()) {
            throw new CommandException(MESSAGE_DUPLICATE_COURSES);
        }

        hasAdded.forEach(model::addCourse);
        return new CommandResult(String.format(MESSAGE_SUCCESS, hasAdded.toString()));
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

        assert !isNull(hasAdded) : "hasAdded should not be null upon undo.";
        assert !hasAdded.isEmpty() : "hasAdded should not be empty upon undo.";

        if (hasAdded.stream().noneMatch(model::hasCourse)) {
            throw new CommandException(String.format(MESSAGE_INVERSE_COURSE_NOT_FOUND, hasAdded));
        }
        hasAdded.forEach(model::deleteCourse);
        return new CommandResult(String.format(MESSAGE_INVERSE_SUCCESS_DELETE, hasAdded));
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
        if (toAdd.isEmpty()) {
            return String.format(REPRESENTATION, COMMAND_WORD, "Nothing to add!");
        }
        return String.format(REPRESENTATION, COMMAND_WORD, toAdd);
    }
}
