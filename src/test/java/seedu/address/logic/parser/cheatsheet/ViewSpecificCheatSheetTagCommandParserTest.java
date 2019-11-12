package seedu.address.logic.parser.cheatsheet;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CHEATSHEET;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.cheatsheet.ViewSpecificCheatSheetTagCommand;

public class ViewSpecificCheatSheetTagCommandParserTest {
    private ViewSpecificCheatSheetTagCommandParser parser = new ViewSpecificCheatSheetTagCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new ViewSpecificCheatSheetTagCommand(INDEX_FIRST_CHEATSHEET));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewSpecificCheatSheetTagCommand.MESSAGE_USAGE));
    }
}
