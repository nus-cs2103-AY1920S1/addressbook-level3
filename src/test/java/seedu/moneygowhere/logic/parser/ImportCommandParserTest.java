package seedu.moneygowhere.logic.parser;

import static seedu.moneygowhere.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moneygowhere.logic.parser.CliSyntax.PREFIX_PATH;
import static seedu.moneygowhere.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.moneygowhere.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.logic.commands.ImportCommand;
import seedu.moneygowhere.model.path.FilePath;

public class ImportCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE);
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "invalidDateSpending.csv");
    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_validArgs_returnsImportCommand() {
        assertParseSuccess(parser, " " + PREFIX_PATH + TEST_DATA_FOLDER.toString(),
                new ImportCommand(new FilePath(TEST_DATA_FOLDER.toString())));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_FORMAT));
    }

    @Test
    public void parse_invalidPath_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_PATH, FilePath.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " " + PREFIX_PATH + "abc", FilePath.MESSAGE_CONSTRAINTS);
    }
}
