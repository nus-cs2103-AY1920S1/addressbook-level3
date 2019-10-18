package seedu.jarvis.logic.parser.course;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.CourseSyntax.PREFIX_COURSE;

import java.util.Optional;

import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.logic.commands.course.LookUpCommand;
import seedu.jarvis.logic.parser.ArgumentMultimap;
import seedu.jarvis.logic.parser.ArgumentTokenizer;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.course.Course;

/**
 * Parses input arguments and creates a new LookUpCommand object.
 */
public class LookUpCommandParser implements Parser<LookUpCommand> {
    @Override
    public LookUpCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_COURSE);
        Optional<String> userInputCourseCode = argumentMultimap.getValue(PREFIX_COURSE);

        if (userInputCourseCode.isEmpty()) {
            throw new ParseException(String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, LookUpCommand.MESSAGE_USAGE));
        }

        String courseCode = userInputCourseCode.get();
        Course course = CourseUtil.getCourse(courseCode).orElseThrow(() ->
            new ParseException(String.format(CourseUtil.MESSAGE_COURSE_NOT_FOUND, courseCode))
        );
        return new LookUpCommand(course);
    }
}
