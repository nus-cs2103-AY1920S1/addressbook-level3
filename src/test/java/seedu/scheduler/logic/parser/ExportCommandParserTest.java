package seedu.scheduler.logic.parser;

import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.logic.commands.CommandTestUtil.FILE_PATH_DESC;
import static seedu.scheduler.logic.commands.CommandTestUtil.INVALID_FILE_PATH_DESC;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_FILE_PATH;
import static seedu.scheduler.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.scheduler.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.scheduler.logic.commands.ExportCommand;
import seedu.scheduler.model.FilePath;

public class ExportCommandParserTest {
    public static final String INVALID_FILE_PATH = "src/test/data/ImportsTest/InterviewerInvalidTestData.csv";
    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ExportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_exportCommand_success() {
        FilePath validFilePath = new FilePath(VALID_FILE_PATH);
        assertParseSuccess(parser, FILE_PATH_DESC, new ExportCommand(validFilePath));

        FilePath invalidFilePath = new FilePath(INVALID_FILE_PATH);
        assertParseSuccess(parser, INVALID_FILE_PATH_DESC, new ExportCommand(invalidFilePath));
    }

}
