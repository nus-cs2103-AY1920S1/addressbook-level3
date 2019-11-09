package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RegisterAccountCommand;

public class RegisterAccountCommandParserTest {

    private RegisterAccountCommandParser parser = new RegisterAccountCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RegisterAccountCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "%@$#",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RegisterAccountCommand.MESSAGE_USAGE));
    }
}
