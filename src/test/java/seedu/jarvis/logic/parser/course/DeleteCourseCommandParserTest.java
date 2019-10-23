package seedu.jarvis.logic.parser.course;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.CourseSyntax.PREFIX_COURSE;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.jarvis.testutil.TypicalIndexes.INDEX_FIRST_COURSE;

import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.logic.commands.course.DeleteCourseCommand;

class DeleteCourseCommandParserTest {
    private DeleteCourseCommandParser parser = new DeleteCourseCommandParser();

    @Test
    void parse_course_returnsCommand() {
        assertParseSuccess(parser, " c/CS2102", new DeleteCourseCommand(
            CourseUtil.getCourse("CS2102").get()
        ));
    }

    @Test
    void parse_index_returnsCommand() {
        assertParseSuccess(parser, "1", new DeleteCourseCommand(INDEX_FIRST_COURSE));
    }

    @Test
    void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            DeleteCourseCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_COURSE + "CS210",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCourseCommand.MESSAGE_USAGE));
    }
}
