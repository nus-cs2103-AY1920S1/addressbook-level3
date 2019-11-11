package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_DESCRIPTION1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_ROLE1;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.WHITESPACE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddGroupCommand;

class AddGroupCommandParserTest {

    private AddGroupCommandParser parser = new AddGroupCommandParser();

    @Test
    void parse_success() {
        assertParseSuccess(parser,
                WHITESPACE + PREFIX_GROUPNAME + GROUP_NAME1.toString()
                + WHITESPACE + PREFIX_DESCRIPTION + GROUP_DESCRIPTION1.toString()
                + WHITESPACE + PREFIX_ROLE + GROUP_ROLE1,
                new AddGroupCommand(GROUP1));
    }

    @Test
    void parse_failure() {
        assertParseFailure(parser,
                WHITESPACE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGroupCommand.MESSAGE_USAGE));

    }
}
