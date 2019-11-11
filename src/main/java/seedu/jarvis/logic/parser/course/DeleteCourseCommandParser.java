package seedu.jarvis.logic.parser.course;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.CourseSyntax.PREFIX_COURSE;

import java.util.Optional;

import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.logic.commands.course.DeleteCourseCommand;
import seedu.jarvis.logic.parser.ArgumentTokenizer;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCourseCommand object
 */
public class DeleteCourseCommandParser implements Parser<DeleteCourseCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCourseCommand
     * and returns a DeleteCourseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCourseCommand parse(String args) throws ParseException {
        if (argIsACourse(args)) {
            return getFromCourse(args);
        }
        if (argIsAnIndex(args)) {
            return getFromIndex(args);
        }
        throw new ParseException(String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, DeleteCourseCommand.MESSAGE_USAGE));
    }

    /**
     * Returns true if the given arg is a Course.
     */
    private boolean argIsACourse(String args) {
        Optional<String> courseCode =
                ArgumentTokenizer.tokenize(args, PREFIX_COURSE).getValue(PREFIX_COURSE);
        if (courseCode.isEmpty()) {
            return false;
        } else {
            return CourseUtil.getCourse(courseCode.get()).isPresent();
        }
    }

    /**
     * Returns a new {@code DeleteCourseCommand} from the given arg. This method should only be
     * called if the given arg is definitely an Index.
     */
    private DeleteCourseCommand getFromCourse(String args) {
        return ArgumentTokenizer.tokenize(args, PREFIX_COURSE).getValue(PREFIX_COURSE).map(course ->
            new DeleteCourseCommand(CourseUtil.getCourse(course).get())
        ).get();
    }

    /**
     * Returns true if the given arg is an Index.
     */
    private boolean argIsAnIndex(String args) {
        try {
            ParserUtil.parseIndex(args);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    /**
     * Returns a new {@code DeleteCourseCommand} from the given arg. This method should only be
     * called if the given arg is definitely an Index.
     */
    private DeleteCourseCommand getFromIndex(String args) throws ParseException {
        try {
            return new DeleteCourseCommand(ParserUtil.parseIndex(args));
        } catch (ParseException e) {
            throw new ParseException(String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, DeleteCourseCommand.MESSAGE_USAGE));
        }
    }
}
