package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.commands.DeletePersonCommand;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPNAME1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPNAME2;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.WHITESPACE;

class DeleteGroupCommandParserTest {

    private DeleteGroupCommandParser parser = new DeleteGroupCommandParser();

    @Test
    void parse_success() {
        assertParseSuccess(parser,
                WHITESPACE + PREFIX_GROUPNAME + GROUPNAME1.toString(),
                new DeleteGroupCommand(GROUPNAME1));

        assertParseSuccess(parser,
                WHITESPACE + PREFIX_GROUPNAME + GROUPNAME2.toString(),
                new DeleteGroupCommand(GROUPNAME2));
    }

    @Test
    void parse_failure() {
        assertParseFailure(parser,
                WHITESPACE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE));
    }
}