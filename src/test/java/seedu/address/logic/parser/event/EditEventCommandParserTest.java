package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_MUSICAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_PARTY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseInvalidPreambleArgsFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseInvalidPrefixFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseNegativeIndexFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseNoFieldFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseNoIndexAndFieldFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseNoIndexFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseZeroIndexFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.event.EditEventCommand;

class EditEventCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE);

    private EditEventCommandParser parser = new EditEventCommandParser();

    @Test
    public void parse_missingParts_failure() {
        assertParseNoIndexFailure(parser, VALID_EVENT_NAME_PARTY, MESSAGE_INVALID_FORMAT);
        assertParseNoFieldFailure(parser, EditEventCommandParser.MESSAGE_NOT_EDITED);
        assertParseNoIndexAndFieldFailure(parser, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        assertParseNegativeIndexFailure(parser, EVENT_NAME_DESC_MUSICAL, MESSAGE_INVALID_FORMAT);
        assertParseZeroIndexFailure(parser, EVENT_NAME_DESC_MUSICAL, MESSAGE_INVALID_FORMAT);
        assertParseInvalidPreambleArgsFailure(parser, EVENT_NAME_DESC_MUSICAL, MESSAGE_INVALID_FORMAT);
        assertParseInvalidPrefixFailure(parser, EVENT_NAME_DESC_MUSICAL, MESSAGE_INVALID_FORMAT);
    }

}
