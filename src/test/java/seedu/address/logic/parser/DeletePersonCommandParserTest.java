package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.DeletePersonCommand;
import seedu.address.logic.commands.EditGroupCommand;
import seedu.address.logic.commands.FindGroupCommand;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPNAME1;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.BENSON;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.WHITESPACE;

class DeletePersonCommandParserTest {

    private DeletePersonCommandParser parser = new DeletePersonCommandParser();

    @Test
    void parse_success() {
        assertParseSuccess(parser,
                WHITESPACE + PREFIX_NAME + WHITESPACE + ALICE.getName().toString(),
                new DeletePersonCommand(ALICE.getName()));

        assertParseSuccess(parser,
                WHITESPACE + PREFIX_NAME + BENSON.getName().toString(),
                new DeletePersonCommand(BENSON.getName()));
    }

    @Test
    void parse_failure() {
        assertParseFailure(parser,
                WHITESPACE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePersonCommand.MESSAGE_USAGE));
    }
}