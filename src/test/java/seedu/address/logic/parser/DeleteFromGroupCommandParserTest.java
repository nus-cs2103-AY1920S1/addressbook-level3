package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME2;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.BENSON;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.WHITESPACE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteFromGroupCommand;

class DeleteFromGroupCommandParserTest {

    private DeleteFromGroupCommandParser parser = new DeleteFromGroupCommandParser();

    @Test
    void parse_success() {
        assertParseSuccess(parser,
                WHITESPACE + PREFIX_NAME + ALICE.getName().toString()
                        + WHITESPACE + PREFIX_GROUPNAME + GROUP_NAME1,
                new DeleteFromGroupCommand(ALICE.getName(), GROUP_NAME1));

        assertParseSuccess(parser,
                WHITESPACE + PREFIX_NAME + BENSON.getName().toString()
                        + WHITESPACE + PREFIX_GROUPNAME + GROUP_NAME2,
                new DeleteFromGroupCommand(BENSON.getName(), GROUP_NAME2));
    }

    @Test
    void parse_missingName() {
        assertParseFailure(parser,
                WHITESPACE + PREFIX_GROUPNAME + GROUP_NAME2,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteFromGroupCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_missingGroupName() {
        assertParseFailure(parser,
                WHITESPACE + PREFIX_NAME + BENSON.getName().toString(),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteFromGroupCommand.MESSAGE_USAGE));
    }
}
