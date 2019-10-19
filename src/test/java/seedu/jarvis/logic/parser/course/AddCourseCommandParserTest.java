package seedu.jarvis.logic.parser.course;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.logic.commands.course.AddCourseCommand;
import seedu.jarvis.model.course.Course;

public class AddCourseCommandParserTest {
    private static final String[] VALID_INPUTS = {
        " c/CS2102 c/CS1010 c/CS1231",
        " c/CS3230",
        " c/GET1028 c/GEH1074",
        PREAMBLE_WHITESPACE + " c/CS1010"
    };

    private static final List<List<Course>> EXPECTED_COURSES = List.of(
        List.of(
            CourseUtil.getCourse("CS2102").get(),
            CourseUtil.getCourse("CS1010").get(),
            CourseUtil.getCourse("CS1231").get()
        ),
        List.of(
            CourseUtil.getCourse("CS3230").get()
        ),
        List.of(
            CourseUtil.getCourse("GET1028").get(),
            CourseUtil.getCourse("GEH1074").get()
        ),
        List.of(
            CourseUtil.getCourse("CS1010").get()
        )
    );

    private static final String[] INVALID_INPUTS = {
        " c/CS101 c/MA15211 c/P1000", // bad courses
        " d/CS1010", // wrong prefix
        " c/CS1010 c/MA1521 c/CS32300", // some bad some good
        "",
    };

    private static final String[] EXPECTED_PARSE_EXCEPTION_MESSAGES = {
        String.format(CourseUtil.MESSAGE_COURSE_NOT_FOUND, List.of("CS101", "MA15211", "P1000")),
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCourseCommand.MESSAGE_USAGE),
        String.format(CourseUtil.MESSAGE_COURSE_NOT_FOUND, List.of("CS32300")),
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCourseCommand.MESSAGE_USAGE),
    };

    private AddCourseCommandParser parser = new AddCourseCommandParser();

    @Test
    public void parse_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        for (int i = 0; i < INVALID_INPUTS.length; i++) {
            assertParseFailure(
                parser,
                INVALID_INPUTS[i],
                EXPECTED_PARSE_EXCEPTION_MESSAGES[i]
            );
        }
    }

    @Test
    public void parse_validArgs_success() {
        for (int i = 0; i < VALID_INPUTS.length; i++) {
            assertParseSuccess(
                parser,
                VALID_INPUTS[i],
                new AddCourseCommand(EXPECTED_COURSES.get(i))
            );
        }
    }
}
