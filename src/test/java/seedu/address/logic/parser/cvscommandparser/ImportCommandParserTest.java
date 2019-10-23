package seedu.address.logic.parser.cvscommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_PATH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.csvcommand.ImportCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.csvcommandparser.ImportCommandParser;
import seedu.address.testutil.TestUtil;

public class ImportCommandParserTest {

    private final Parser parser = new ImportCommandParser();

    @Test
    public void parse_validParameterPassedIn_validCommandReturned() {
        String path = TestUtil.getFilePathInSandboxFolder("Alfred.csv").toString();
        assertParseSuccess(parser, " " + PREFIX_FILE_PATH + path, new ImportCommand(path));
    }

    @Test
    public void parse_invalidParametersPassedIn_exceptionThrown() {
        // Only csv files should be passed in
        String path = " " + PREFIX_FILE_PATH + TestUtil.getFilePathInSandboxFolder("Alfred.txt").toString();
        assertParseFailure(parser, path, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));

        // Preamble must be blank
        path = "mentor" + PREFIX_FILE_PATH + "Alfred.csv";
        assertParseFailure(parser, path, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));

        // PREFIX_FILE_PATH is not present
        path = " " + TestUtil.getFilePathInSandboxFolder("Alfred.csv").toString();
        assertParseFailure(parser, path, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));

        // File name is blank
        path = " " + PREFIX_FILE_PATH.getPrefix();
        assertParseFailure(parser, path, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }

}
