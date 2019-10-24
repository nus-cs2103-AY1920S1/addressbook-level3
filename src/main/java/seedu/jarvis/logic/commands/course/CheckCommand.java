package seedu.jarvis.logic.commands.course;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.jarvis.logic.parser.CliSyntax.CourseSyntax.PREFIX_COURSE;

import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.commons.util.andor.AndOrTree;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.course.Course;

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

    public static final String MESSAGE_SUCCESS = "Checked %1$s";
    public static final String MESSAGE_FAILURE = "%1$s has no prerequisites!";
    public static final String MESSAGE_NO_INVERSE =
            "The command " + COMMAND_WORD + " cannot be undone";
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
        requireNonNull(model);

        if (isNull(toCheck.getPrereqTree())) {
            throw new CommandException(String.format(MESSAGE_FAILURE, toCheck));
        }

        AndOrTree tree = AndOrTree.buildTree(
            toCheck.toString(),
            toCheck.getPrereqTree().toString(), (c) -> CourseUtil.getCourse(c).orElse(null)
        );

        // TODO act on model
        // return new CommandResult(String.format(MESSAGE_SUCCESS, toCheck));

        return new CommandResult(tree.toString());
    }

    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NO_INVERSE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof CheckCommand
            && this.toCheck.equals(((CheckCommand) other).toCheck));
    }
}
