package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalFiles.FILE_EXAMPLE_1;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OBJECT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UploadPictureCommand;

public class UploadCommandParserTest {

    private UploadPictureCommandParser parser = new UploadPictureCommandParser();

    @Test
    public void parse_validArgs_returnsUploadCommand() {
        assertParseSuccess(parser, "1 f/path/file.png", new UploadPictureCommand(INDEX_FIRST_OBJECT, FILE_EXAMPLE_1));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UploadPictureCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UploadPictureCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 a f/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UploadPictureCommand.MESSAGE_USAGE));
    }
}
