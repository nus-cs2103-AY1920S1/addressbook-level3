package seedu.jarvis.logic.commands.course;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_COURSE;

import seedu.jarvis.commons.exceptions.CourseNotFoundException;
import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.course.Course;

/**
 * Looks up a course in Jarvis.
 */
public class LookUpCommand extends Command {
    public static final String COMMAND_WORD = "lookup";

    public static final String MESSAGE_USAGE = String.format(
        "%s: Shows information about a specific course. Parameters: %sCOURSE\n"
        + "Example: %s %sCS2103T or %s %sCS3230",
        COMMAND_WORD,
        PREFIX_COURSE,
        COMMAND_WORD,
        PREFIX_COURSE,
        COMMAND_WORD,
        PREFIX_COURSE
    );

    public static final String MESSAGE_NO_INVERSE = "The command " + COMMAND_WORD + " cannot be undone";

    public static final String MESSAGE_COURSE_NOT_FOUND =
        "%s: The given course could not be found!";

    public static final boolean HAS_INVERSE = false;

    private final Course toShow;

    public LookUpCommand(Course course) {
        requireNonNull(course);
        toShow = course;
    }

    @Override
    public boolean hasInverseExecution() {
        return false;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            CourseUtil.getCourse(toShow.getCourseCode().toString());
        } catch (CourseNotFoundException e) {
            throw new CommandException(MESSAGE_COURSE_NOT_FOUND);
        }

        // TODO execute on model i.e model.lookUpCourse(toShow)
        //      instead of returning it with command result
        String toDisplay = toShow.toDisplayableString();
        return new CommandResult(toDisplay);
    }

    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NO_INVERSE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof LookUpCommand
                && this.toShow.equals(((LookUpCommand) other).toShow));
    }
}
