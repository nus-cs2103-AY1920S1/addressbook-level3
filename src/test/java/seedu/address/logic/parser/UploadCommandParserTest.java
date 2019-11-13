package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalFiles.FILE_EXAMPLE_1;
import static seedu.address.testutil.TypicalFiles.FILE_EXAMPLE_2;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OBJECT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UploadPictureCommand;

public class UploadCommandParserTest {

    private UploadPictureCommandParser parser = new UploadPictureCommandParser();

    @Test
    public void parse_validArgs_returnsUploadCommand() {
        // Test both jpg and png files
        assertParseSuccess(parser, "1 f/path/file.png",
                new UploadPictureCommand(INDEX_FIRST_OBJECT, FILE_EXAMPLE_1));
        assertParseSuccess(parser, "1 f/path/file.jpg",
                new UploadPictureCommand(INDEX_FIRST_OBJECT, FILE_EXAMPLE_2));
        // Test with whitespaces
        assertParseSuccess(parser, "\n 1 \n f/ \n path/file.png",
                new UploadPictureCommand(INDEX_FIRST_OBJECT, FILE_EXAMPLE_1));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // empty args
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UploadPictureCommand.MESSAGE_USAGE));
        // valid index, no prefix
        assertParseFailure(parser, "1 a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UploadPictureCommand.MESSAGE_USAGE));
        // valid index, have prefix but filepath empty
        assertParseFailure(parser, "1 a f/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UploadPictureCommand.MESSAGE_USAGE));
        // valid index, invalid file type
        assertParseFailure(parser, "1 a f/test.pdf", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UploadPictureCommand.MESSAGE_USAGE));
    }
}
