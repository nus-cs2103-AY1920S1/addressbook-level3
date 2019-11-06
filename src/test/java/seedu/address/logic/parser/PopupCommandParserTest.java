package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPNAME1;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.WHITESPACE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.PopupCommand;

class PopupCommandParserTest {

    private PopupCommandParser parser = new PopupCommandParser();

    @Test
    void parse_success() {
        assertParseSuccess(parser,
                WHITESPACE + PREFIX_GROUPNAME + GROUPNAME1.toString()
                        + WHITESPACE + PREFIX_ID + 1,
                new PopupCommand(GROUPNAME1, 0, 1));
    }

    @Test
    void parse_allNull() {
        assertParseFailure(parser,
                WHITESPACE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PopupCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_nullId() {
        assertParseFailure(parser,
                WHITESPACE + PREFIX_GROUPNAME + GROUPNAME1.toString(),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PopupCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_nullGroupName() {
        assertParseFailure(parser,
                WHITESPACE + PREFIX_ID + 1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PopupCommand.MESSAGE_USAGE));
    }
}
