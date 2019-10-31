package seedu.scheduler.logic.parser;

import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.scheduler.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.scheduler.logic.commands.EmailCommand;
import seedu.scheduler.model.person.Name;

public class EmailCommandParserTest {

    private EmailCommandParser parser = new EmailCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EmailCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArg_returnsEmailCommand() {
        assertParseSuccess(parser, "timeslot Alice", new EmailCommand(new Name("Alice")));
    }
}
