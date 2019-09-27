package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.logic.commands.AddToGroupCommand;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPNAME1;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.WHITESPACE;

class AddPersonCommandParserTest {

    private AddPersonCommandParser parser = new AddPersonCommandParser();

    @Test
    void parse_success() {
        assertParseSuccess(parser,
                WHITESPACE + PREFIX_NAME + ALICE.getName().toString()
                        + WHITESPACE + PREFIX_PHONE + ALICE.getPhone().toString()
                        + WHITESPACE + PREFIX_ADDRESS + ALICE.getAddress().toString()
                        + WHITESPACE + PREFIX_EMAIL + ALICE.getEmail().toString()
                        + WHITESPACE + PREFIX_REMARK + ALICE.getRemark().toString()
                        + WHITESPACE + PREFIX_TAG + "friends",
                new AddPersonCommand(ALICE));
    }

    @Test
    void parse_failure() {
        assertParseFailure(parser,
                WHITESPACE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonCommand.MESSAGE_USAGE));

    }
}