package seedu.moneygowhere.logic.parser;

import static seedu.moneygowhere.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_PATH;
import static seedu.moneygowhere.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.moneygowhere.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.logic.commands.ExportCommand;
import seedu.moneygowhere.model.path.FolderPath;

public class ExportCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);
    private static final Path TEST_DATA_FOLDER = Paths.get(".", "src", "test", "data", "SampleSpendings");
    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_validArgs_returnsExportCommand() {
        assertParseSuccess(parser, " " + PREFIX_PATH + TEST_DATA_FOLDER.toString(),
                new ExportCommand(new FolderPath(TEST_DATA_FOLDER.toString())));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_FORMAT));
    }

    @Test
    public void parse_invalidPath_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_PATH, FolderPath.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " " + PREFIX_PATH + "abc", FolderPath.MESSAGE_CONSTRAINTS);
    }
}
