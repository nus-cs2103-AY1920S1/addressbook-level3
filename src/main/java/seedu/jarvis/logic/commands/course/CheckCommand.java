package seedu.jarvis.logic.commands.course;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.jarvis.logic.parser.CliSyntax.CourseSyntax.PREFIX_COURSE;

import java.util.List;

import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.commons.util.andor.AndOrTree;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.course.Course;
import seedu.jarvis.model.viewstatus.ViewType;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;

/**
 * Checks if the user can take a certain course in Jarvis.
 */
public class CheckCommand extends Command {
    public static final String COMMAND_WORD = "check";

    public static final String MESSAGE_USAGE = String.format(
        "%s: Checks whether you can take this list. Parameters: %sCOURSE\n"
            + "Example: %s %sCS2103T or %s %sCS3230",
        COMMAND_WORD, PREFIX_COURSE, COMMAND_WORD, PREFIX_COURSE,
        COMMAND_WORD, PREFIX_COURSE, PREFIX_COURSE
    );

    /** Result Strings */
    public static final String MESSAGE_SUCCESS = "Checked %1$s";
    public static final String MESSAGE_NO_INVERSE = "The command " + COMMAND_WORD + " cannot be undone";

    /** To print to user */
    public static final String MESSAGE_NO_PREREQS = "%1$s has no prerequisites!";
    public static final String MESSAGE_CAN_TAKE_COURSE =
        "%s: You are able to take this course!\n\nThese are the prerequisites you have satisfied:\n\n%s";

    public static final String MESSAGE_CANNOT_TAKE_COURSE =
        "%s: You are not able to take this course!\n\nThese are the prerequisites for this course:\n\n%s";

    public static final boolean HAS_INVERSE = false;

    private final Course toCheck;

    public CheckCommand(Course course) {
        requireNonNull(course);
        toCheck = course;
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
     * Checks if the user can take this {@code Course} to the course planner.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that the course was successfully checked
     * @throws CommandException if the course has no prerequisites
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model, toCheck);
        if (isNull(toCheck.getPrereqTree())) {
            handleNoPrereqs(model);
        } else {
            handleWithPrereqs(model);
        }
        model.setViewStatus(ViewType.LIST_COURSE);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toCheck), true);
    }

    /**
     * Handles the case where toCheck has no prerequisites.
     */
    private void handleNoPrereqs(Model model) {
        String messageToUser = String.format(MESSAGE_NO_PREREQS, toCheck);
        model.checkCourse(messageToUser);
    }

    /**
     * Handles the case where toCheck has some prerequisites.
     */
    private void handleWithPrereqs(Model model) {
        AndOrTree<Course> tree = AndOrTree.buildTree(
            toCheck.toString(),
            toCheck.getPrereqTree().toString(), (c) -> CourseUtil.getCourse(c).orElse(null)
        );
        List<Course> userCourses = model.getUnfilteredCourseList();
        String messageToUser = (tree.fulfills(userCourses))
                ? String.format(MESSAGE_CAN_TAKE_COURSE, toCheck, tree.toString())
                : String.format(MESSAGE_CANNOT_TAKE_COURSE, toCheck, tree.toString());
        model.checkCourse(messageToUser);
    }

    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NO_INVERSE);
    }

    /**
     * Gets a {@code JsonAdaptedCommand} from a {@code Command} for local storage purposes.
     *
     * @return {@code JsonAdaptedCommand}.
     * @throws InvalidCommandToJsonException If command should not be adapted to JSON format.
     */
    @Override
    public JsonAdaptedCommand adaptToJsonAdaptedCommand() throws InvalidCommandToJsonException {
        throw new InvalidCommandToJsonException();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof CheckCommand
            && this.toCheck.equals(((CheckCommand) other).toCheck));
    }
}
