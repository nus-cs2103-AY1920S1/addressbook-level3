package seedu.scheduler.logic.parser;

import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.scheduler.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.scheduler.logic.commands.ScheduleCommand;

public class ScheduleCommandParserTest {
    private ScheduleCommandParser parser = new ScheduleCommandParser();

    @Test
    public void parse_nonEmptyArg_throwsParseException() {
        assertParseFailure(parser, "hello", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_returnsScheduleCommand() {
        // no trailing characters
        ScheduleCommand expectedScheduleCommand = new ScheduleCommand();
        assertParseSuccess(parser, "  ", expectedScheduleCommand);
    }
}
