package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPNAME1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPNAME2;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.BENSON;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.WHITESPACE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddToGroupCommand;

class AddToGroupCommandParserTest {

    private AddToGroupCommandParser parser = new AddToGroupCommandParser();


    @Test
    void parse_success() {
        assertParseSuccess(parser,
                WHITESPACE + PREFIX_NAME + ALICE.getName().toString()
                        + WHITESPACE + PREFIX_GROUPNAME + GROUPNAME1.toString(),
                new AddToGroupCommand(ALICE.getName(), GROUPNAME1));

        assertParseSuccess(parser,
                WHITESPACE + PREFIX_NAME + BENSON.getName().toString()
                        + WHITESPACE + PREFIX_GROUPNAME + GROUPNAME2.toString(),
                new AddToGroupCommand(BENSON.getName(), GROUPNAME2));
    }

    @Test
    void parse_allNull() {
        assertParseFailure(parser,
                WHITESPACE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToGroupCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_nullName() {
        assertParseFailure(parser,
                WHITESPACE + PREFIX_GROUPNAME + GROUPNAME1.toString(),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToGroupCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_nullGroupName() {
        assertParseFailure(parser,
                WHITESPACE + PREFIX_NAME + ALICE.getName().toString(),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToGroupCommand.MESSAGE_USAGE));
    }
}
