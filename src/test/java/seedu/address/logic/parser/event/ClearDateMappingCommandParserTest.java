package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseIndexSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseInvalidIndexFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseInvalidPreambleArgsFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseNegativeIndexFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseNoIndexAndFieldFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseZeroIndexFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.event.ClearDateMappingCommand;

class ClearDateMappingCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearDateMappingCommand.MESSAGE_USAGE);
    private ClearDateMappingCommandParser parser = new ClearDateMappingCommandParser();

    @Test
    void parse_allFieldsPresent_success() {
        assertParseIndexSuccess(parser, new ClearDateMappingCommand(INDEX_FIRST_EVENT));
    }

    @Test
    void parse_invalidArgs_throwsParseException() {
        assertParseNoIndexAndFieldFailure(parser, MESSAGE_INVALID_FORMAT);
        assertParseNegativeIndexFailure(parser, MESSAGE_INVALID_FORMAT);
        assertParseZeroIndexFailure(parser, MESSAGE_INVALID_FORMAT);
        assertParseInvalidIndexFailure(parser, MESSAGE_INVALID_FORMAT);
        assertParseInvalidPreambleArgsFailure(parser, MESSAGE_INVALID_FORMAT);
    }

}
