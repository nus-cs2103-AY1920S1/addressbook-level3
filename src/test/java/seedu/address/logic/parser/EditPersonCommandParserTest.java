package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.WHITESPACE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditPersonCommand;

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
