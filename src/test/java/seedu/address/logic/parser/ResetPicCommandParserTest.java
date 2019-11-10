package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OBJECT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ResetDisplayPictureCommand;

public class ResetPicCommandParserTest {
    private ResetDisplayPictureCommandParser parser = new ResetDisplayPictureCommandParser();

    @Test
    public void parse_validArgs_returnsResetPicCommand() {
        assertParseSuccess(parser, "1", new ResetDisplayPictureCommand(INDEX_FIRST_OBJECTT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ResetDisplayPictureCommand.MESSAGE_USAGE));
    }
}
