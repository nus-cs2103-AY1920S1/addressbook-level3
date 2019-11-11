package seedu.address.logic.parser.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.storage.ViewStudyPlanCommand;

/**
 * Test class for {@code ViewStudyPlanCommandParser}.
 */
public class ViewStudyPlanCommandParserTest {

    private ViewStudyPlanCommandParser parser = new ViewStudyPlanCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewStudyPlanCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonIntArg_throwsParseException() {
        assertParseFailure(parser, "hello", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewStudyPlanCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_intOverflow_throwsParseException() {
        assertParseFailure(parser, "9999999999999999999999999", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewStudyPlanCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonEmptyArg_returnsViewStudyPlanCommand() {
        String validStudyPlanIndexString = "1";
        int validStudyPlanIndex = 1;
        ViewStudyPlanCommand expectedViewStudyPlanCommand = new ViewStudyPlanCommand(validStudyPlanIndex);
        assertParseSuccess(parser, validStudyPlanIndexString, expectedViewStudyPlanCommand);
    }

}
