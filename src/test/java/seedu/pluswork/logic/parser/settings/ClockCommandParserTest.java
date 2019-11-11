package seedu.pluswork.logic.parser.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.pluswork.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pluswork.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.pluswork.logic.commands.settings.ClockCommand;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.logic.parser.ParserUtilTest;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.logic.parser.settings.ClockCommandParser;

/**
 * White-box testing for parsing done here, refer to {@link ParserUtilTest} for varied user
 * input tests.
 */
class ClockCommandParserTest {
    private ClockCommandParser clockCommandParser = new ClockCommandParser();

    // enumerations cannot be equal, hence test for equality of instance of theme command created
    @Test
    void parse_validArgs_returnsClockCommand() throws ParseException {
        assertEquals(clockCommandParser.parse("twelve").getClass(), ClockCommand.class);
    }

    @Test
    void parse_invalidArgs_throwsCommandException() throws CommandException {
        assertParseFailure(clockCommandParser, "theme lig",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClockCommand.MESSAGE_USAGE));
    }
}
