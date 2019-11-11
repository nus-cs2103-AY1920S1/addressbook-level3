package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME2;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_ROLE1;
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
                        + WHITESPACE + PREFIX_GROUPNAME + GROUP_NAME1.toString()
                        + WHITESPACE + PREFIX_ROLE + GROUP_ROLE1,
                new AddToGroupCommand(ALICE.getName(), GROUP_NAME1, GROUP_ROLE1));

        assertParseSuccess(parser,
                WHITESPACE + PREFIX_NAME + BENSON.getName().toString()
                        + WHITESPACE + PREFIX_GROUPNAME + GROUP_NAME2.toString()
                        + WHITESPACE + PREFIX_ROLE + GROUP_ROLE1,
                new AddToGroupCommand(BENSON.getName(), GROUP_NAME2, GROUP_ROLE1));
    }

    @Test
    void parse_missingNameAndGroupName() {
        assertParseFailure(parser,
                WHITESPACE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToGroupCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_missingName() {
        assertParseFailure(parser,
                WHITESPACE + PREFIX_GROUPNAME + GROUP_NAME1.toString(),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToGroupCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_missingGroupName() {
        assertParseFailure(parser,
                WHITESPACE + PREFIX_NAME + ALICE.getName().toString(),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToGroupCommand.MESSAGE_USAGE));
    }
}
