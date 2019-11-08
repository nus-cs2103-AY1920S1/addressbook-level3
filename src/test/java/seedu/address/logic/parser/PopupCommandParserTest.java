package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.WHITESPACE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.PopupCommand;

class PopupCommandParserTest {

    private PopupCommandParser parser = new PopupCommandParser();

    @Test
    void parse_success() {
        assertParseSuccess(parser,
                WHITESPACE + PREFIX_ID + 1,
                new PopupCommand(0, 1));
    }

    @Test
    void parse_allNull() {
        assertParseFailure(parser,
                WHITESPACE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PopupCommand.MESSAGE_USAGE));
    }

}
