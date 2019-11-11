package seedu.ifridge.logic.parser.shoppinglist;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ifridge.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.ifridge.logic.commands.shoppinglist.UrgentShoppingCommand;

class UrgentShoppingCommandParserTest {
    private UrgentShoppingCommandParser parser = new UrgentShoppingCommandParser();

    @Test
    public void parse_validArgs_returnsUrgentShoppingCommand() {
        assertParseSuccess(parser, "1", new UrgentShoppingCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UrgentShoppingCommand.MESSAGE_USAGE));
    }
}
