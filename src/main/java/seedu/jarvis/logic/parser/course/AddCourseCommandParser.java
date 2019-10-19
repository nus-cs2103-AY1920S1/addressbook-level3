package seedu.jarvis.logic.parser.course;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.CourseSyntax.PREFIX_COURSE;

import java.util.List;
import java.util.stream.Collectors;

import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.logic.commands.course.AddCourseCommand;
import seedu.jarvis.logic.parser.ArgumentMultimap;
import seedu.jarvis.logic.parser.ArgumentTokenizer;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.course.Course;

/**
 * Parsers input arguments and creates a new AddCourseCommand object.
 */
public class AddCourseCommandParser implements Parser<AddCourseCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCourseCommand
     * and returns an AddCourseCommand object for execution.
     *
     * @param args to consider
     * @return a new {@code AddCourseCommand} object
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddCourseCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_COURSE);
        List<String> userInputCourses = argMultimap.getAllValues(PREFIX_COURSE);

        if (userInputCourses.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCourseCommand.MESSAGE_USAGE));
        }

        List<String> nonExistentCourses = getNonExistentCourses(userInputCourses);

        if (!nonExistentCourses.isEmpty()) {
            throw new ParseException(String.format(CourseUtil.MESSAGE_COURSE_NOT_FOUND, nonExistentCourses));
        }

        List<Course> coursesThatExist = getCoursesToAdd(nonExistentCourses, userInputCourses);
        return new AddCourseCommand(coursesThatExist);
    }

    /**
     * Returns a {@code List} of courses that do not exist in {@code toAdd}. Returns an empty
     * {@code List} if all courses do not exist.
     *
     * This is a helper method.
     *
     * @return
     */
    private List<String> getNonExistentCourses(List<String> toAdd) {
        return toAdd.stream()
            .filter(code -> CourseUtil.getCourse(code).isEmpty())
            .collect(Collectors.toList());
    }

    /**
     * Returns a {@code List<Course>} containing all proper elements to add to the
     * Course Planner.
     *
     * @param nonExistentCourses to filter out
     * @param toAdd user input courses
     * @return {@code List<Course>} containing appropriate courses to add
     */
    private List<Course> getCoursesToAdd(List<String> nonExistentCourses, List<String> toAdd) {
        return toAdd.stream()
            .filter(code -> !nonExistentCourses.contains(code)) // remove faulty courses
            .map(code -> CourseUtil.getCourse(code).get())
            .distinct() // remove duplicates
            .collect(Collectors.toList());
    }
}
