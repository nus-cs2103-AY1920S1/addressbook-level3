package seedu.jarvis.logic.parser.course;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.CourseSyntax.PREFIX_COURSE;

import java.util.Optional;

import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.logic.commands.course.CheckCommand;
import seedu.jarvis.logic.parser.ArgumentMultimap;
import seedu.jarvis.logic.parser.ArgumentTokenizer;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.course.Course;

/**
 * Parsers input arguments and creates a new CheckCommand object.
 */
public class CheckCommandParser implements Parser<CheckCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CheckCommand
     * and returns a CheckCommand object for execution
     *
     * @param args to consider
     * @return a new {@code CheckCommand} object
     * @throws ParseException if the user input does not confirm to the expected format
     */
    @Override
    public CheckCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_COURSE);
        Optional<String> userInputCourse = argMultimap.getValue(PREFIX_COURSE);

        if (userInputCourse.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE));
        }

        String courseCode = userInputCourse.get();

        Course course = CourseUtil.getCourse(courseCode).orElseThrow(() ->
            new ParseException(String.format(CourseUtil.MESSAGE_COURSE_NOT_FOUND, courseCode))
        );

        return new CheckCommand(course);
    }
}
