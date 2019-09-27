package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.EditGroupCommand;
import seedu.address.logic.commands.EditPersonCommand;
import seedu.address.model.group.GroupDescriptor;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDIT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPNAME1;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.WHITESPACE;

class EditGroupCommandParserTest {

    private EditGroupCommandParser parser = new EditGroupCommandParser();

    @Test
    void parse_success() {
        /*assertParseSuccess(parser,
                WHITESPACE + PREFIX_EDIT + GROUPNAME1.toString(),
                new EditGroupCommand(GROUPNAME1, new GroupDescriptor()));*/
    }

    @Test
    void parse_failure() {
        assertParseFailure(parser,
                WHITESPACE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditGroupCommand.MESSAGE_USAGE));
    }
}