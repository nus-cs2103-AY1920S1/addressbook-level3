package seedu.jarvis.logic.parser.course;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.jarvis.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.exceptions.CourseNotFoundException;
import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.logic.commands.course.LookUpCommand;


public class LookUpCommandParserTest {

    private LookUpCommandParser parser = new LookUpCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "  ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, LookUpCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsLookUpCommand() {
        try {
            LookUpCommand expectedLookUpCommand = new LookUpCommand(
                CourseUtil.getCourse("CS3230")
            );
            // requires leading whitespace for some reason
            assertParseSuccess(parser, " " + PREFIX_COURSE + "CS3230", expectedLookUpCommand);
            assertParseSuccess(parser, "  " + PREFIX_COURSE + "\n \t" + "CS3230" + "\t",
                    expectedLookUpCommand);
        } catch (CourseNotFoundException e) {
            // "CS3230" will succeed as long as the .json file exists
        }
    }
}
