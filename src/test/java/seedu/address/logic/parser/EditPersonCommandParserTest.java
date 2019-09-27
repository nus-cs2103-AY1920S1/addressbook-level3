package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.EditPersonCommand;
import seedu.address.logic.commands.FindGroupCommand;
import seedu.address.model.person.PersonDescriptor;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPNAME1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPNAME2;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.WHITESPACE;

class EditPersonCommandParserTest {

    private EditPersonCommandParser parser = new EditPersonCommandParser();

    @Test
    void parse_success() {
        /*assertParseSuccess(parser,
                WHITESPACE + PREFIX_EDIT + ALICE.getName().toString(),
                new EditPersonCommand(ALICE.getName(), new PersonDescriptor()));*/
    }

    @Test
    void parse_failure() {
        assertParseFailure(parser,
                WHITESPACE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPersonCommand.MESSAGE_USAGE));

    }
}