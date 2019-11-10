package seedu.jarvis.logic.commands.course;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.jarvis.logic.parser.CliSyntax.CourseSyntax.PREFIX_COURSE;

import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.course.Course;
import seedu.jarvis.model.viewstatus.ViewType;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;

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

    public static final String MESSAGE_SUCCESS = "Looked up %1$s";
    public static final String MESSAGE_NO_INVERSE = "The command " + COMMAND_WORD + " cannot be undone";
    public static final boolean HAS_INVERSE = false;

    private final Course toShow;

    public LookUpCommand(Course course) {
        requireNonNull(course);
        toShow = course;
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

    @Override
    public boolean hasInverseExecution() {
        return HAS_INVERSE;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        assert CourseUtil.courseExists(toShow.toString())
            : String.format("%s should exist", toShow);

        requireAllNonNull(model, toShow);
        model.lookUpCourse(toShow);
        model.setViewStatus(ViewType.LIST_COURSE);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toShow), true);
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
                || (other instanceof LookUpCommand
                && this.toShow.equals(((LookUpCommand) other).toShow));
    }
}
