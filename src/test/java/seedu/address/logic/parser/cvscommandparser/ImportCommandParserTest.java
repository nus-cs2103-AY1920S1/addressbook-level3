package seedu.address.logic.parser.cvscommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_PATH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.csvcommand.ImportCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.csvcommandparser.ImportCommandParser;
import seedu.address.testutil.TestUtil;

public class ImportCommandParserTest {

    private final Parser parser = new ImportCommandParser();

    @Test
    public void parse_validParameterPassedIn_validCommandReturned() throws CommandException {
        String path = TestUtil.getFilePathInSandboxFolder("Alfred.csv").toString();
        assertParseSuccess(parser, " " + PREFIX_FILE_PATH + path, new ImportCommand(path));

        String errorFilePath = TestUtil.getFilePathInSandboxFolder("Alfred_Error.csv").toString();
        assertParseSuccess(
                parser,
                " " + PREFIX_FILE_PATH + path + " " + PREFIX_FILE_PATH + errorFilePath,
                new ImportCommand(path, errorFilePath));
    }

    @Test
    public void parse_invalidParametersPassedIn_exceptionThrown() {
        // Only csv files should be passed in
        String path = " " + PREFIX_FILE_PATH + TestUtil.getFilePathInSandboxFolder("Alfred.txt").toString();
        assertParseFailure(parser, path, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));

        // Preamble must be blank
        path = "mentor " + PREFIX_FILE_PATH + "Alfred.csv";
        assertParseFailure(parser, path, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));

        // PREFIX_FILE_PATH is not present
        path = " " + TestUtil.getFilePathInSandboxFolder("Alfred.csv").toString();
        assertParseFailure(parser, path, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));

        // File name is blank
        path = " " + PREFIX_FILE_PATH;
        assertParseFailure(parser, path, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));

        // no files are present
        path = "";
        assertParseFailure(parser, path, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));

        // errorFile is not a csv file
        path = " " + PREFIX_FILE_PATH + "Alfred.csv " + PREFIX_FILE_PATH + "AlfredError.txt";
        assertParseFailure(parser, path, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));

        // More than two files are present
        path = " " + PREFIX_FILE_PATH + "Alfred.csv " + PREFIX_FILE_PATH + "AlfredError.csv "
                + PREFIX_FILE_PATH + "RandomFile.csv";
        assertParseFailure(parser, path, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }

}
