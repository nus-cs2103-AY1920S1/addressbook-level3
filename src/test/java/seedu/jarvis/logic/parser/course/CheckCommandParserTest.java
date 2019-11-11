package seedu.jarvis.logic.parser.course;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.CourseSyntax.PREFIX_COURSE;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.logic.commands.course.CheckCommand;


public class CheckCommandParserTest {

    private CheckCommandParser parser = new CheckCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "  ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsCheckCommand() {
        CheckCommand expectedCheckCommand = new CheckCommand(
            CourseUtil.getCourse("CS3230").get()
        );
        // requires leading whitespace for some reason
        assertParseSuccess(parser, " " + PREFIX_COURSE + "CS3230", expectedCheckCommand);
        assertParseSuccess(parser, "  " + PREFIX_COURSE + "\n \t" + "CS3230" + "\t",
                expectedCheckCommand);
    }
}
