package seedu.jarvis.logic.parser.course;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.CourseSyntax.PREFIX_COURSE;

import java.util.Optional;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.commons.exceptions.CourseNotFoundException;
import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.logic.commands.course.DeleteCourseCommand;
import seedu.jarvis.logic.parser.ArgumentMultimap;
import seedu.jarvis.logic.parser.ArgumentTokenizer;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.course.Course;

/**
 * Parses input arguments and creates a new AddCourseCommand object.
 */
public class DeleteCourseCommandParser implements Parser<DeleteCourseCommand> {
    @Override
    public DeleteCourseCommand parse(String args) throws ParseException {
        if (isIndex(args)) {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCourseCommand(index);
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_COURSE);
        Optional<String> arg = argMultimap.getValue(PREFIX_COURSE);

        if (arg.isEmpty()) {
            throw new ParseException(String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, DeleteCourseCommand.COMMAND_WORD));
        }

        String courseCode = arg.get();

        try {
            Course course = CourseUtil.getCourse(courseCode);
            return new DeleteCourseCommand(course);
        } catch (CourseNotFoundException e) {
            throw new ParseException(courseCode + " could not be found!");
        }
    }

    /**
     * Checks if the given args is an Index.
     *
     * @param args to be checked
     * @return true if the args is an Index.
     */
    private static boolean isIndex(String args) {
        try {
            Index index = ParserUtil.parseIndex(args);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
