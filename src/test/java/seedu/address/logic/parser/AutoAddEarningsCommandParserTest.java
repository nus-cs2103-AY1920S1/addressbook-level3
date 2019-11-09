package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AutoAddEarningsCommand;

public class AutoAddEarningsCommandParserTest {

    private AutoAddEarningsCommandParser parser = new AutoAddEarningsCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AutoAddEarningsCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "-4",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AutoAddEarningsCommand.MESSAGE_USAGE));
    }

}
