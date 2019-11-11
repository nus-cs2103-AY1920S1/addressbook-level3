package seedu.weme.logic.parser.commandparser.exportcommandparser;

import static seedu.weme.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_FILEPATH;

import org.junit.jupiter.api.Test;

import seedu.weme.logic.commands.exportcommand.ExportCommand;
import seedu.weme.model.path.DirectoryPath;
import seedu.weme.testutil.TestUtil;

class ExportCommandParserTest {

    private static final String VALID_SANDBOX_DIRECTORY = TestUtil.getSandboxFolder().toString();
    private static final String SANDBOX_DIRECTORY_DESC = " " + PREFIX_FILEPATH + VALID_SANDBOX_DIRECTORY;

    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_pathSpecified_success() {
        // sandbox path specified
        DirectoryPath directoryPath = new DirectoryPath(VALID_SANDBOX_DIRECTORY);

        ExportCommand expectedCommand = new ExportCommand(directoryPath);
        assertParseSuccess(parser, SANDBOX_DIRECTORY_DESC, expectedCommand);
    }

    @Test
    public void parse_noPathSpecified_success() {
        ExportCommand expectedCommand = new ExportCommand(true);

        assertParseSuccess(parser, " ", expectedCommand);
    }

}
