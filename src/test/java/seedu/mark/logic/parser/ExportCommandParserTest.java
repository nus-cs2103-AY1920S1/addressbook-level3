package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.mark.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.mark.logic.commands.ExportCommand;

public class ExportCommandParserTest {

    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // empty input
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));

        // multiple words
        assertParseFailure(parser, "more than one word",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsExportCommand() {
        // no leading and trailing whitespaces
        ExportCommand expectedExportCommand =
                new ExportCommand(Paths.get("data", "bookmarks", "myBookmarks"));
        assertParseSuccess(parser, "myBookmarks", expectedExportCommand);

        // leading and trailing whitespaces
        assertParseSuccess(parser, " \n myBookmarks \n \t", expectedExportCommand);
    }

}
