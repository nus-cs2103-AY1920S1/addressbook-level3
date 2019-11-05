package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_FILE_NAME_INCLUDES_EXTENSION;
import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.ImportCommand;

public class ImportCommandParserTest {

    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // empty input
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));

        // multiple words
        assertParseFailure(parser, "more than one word",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));

        // file name ends with .json
        assertParseFailure(parser, "myBookmarks.json",
                MESSAGE_FILE_NAME_INCLUDES_EXTENSION);
    }

    @Test
    public void parse_validArgs_returnsImportCommand() {
        // no leading and trailing whitespaces
        ImportCommand expectedImportCommand =
                new ImportCommand(Path.of("data", "bookmarks", "myBookmarks.json"));
        assertParseSuccess(parser, "myBookmarks", expectedImportCommand);

        // leading and trailing whitespaces
        assertParseSuccess(parser, " \n myBookmarks \n \t", expectedImportCommand);
    }

}
