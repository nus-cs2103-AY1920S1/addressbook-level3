package seedu.scheduler.logic.parser;
import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.logic.commands.CommandTestUtil.FILE_PATH_DESC;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_FILE_PATH;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_ROLE_AMY_INTVR;
import static seedu.scheduler.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.scheduler.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.scheduler.logic.commands.ImportCommand;
import seedu.scheduler.model.FilePath;
import seedu.scheduler.model.person.Role;


public class ImportCommandParserTest {
    private ImportCommandParser parser = new ImportCommandParser();
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ImportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_importCommand_success() {
        Role expectedRole = new Role(VALID_ROLE_AMY_INTVR);
        FilePath expectedFilePath = new FilePath(VALID_FILE_PATH);
        assertParseSuccess(parser, VALID_ROLE_AMY_INTVR + FILE_PATH_DESC,
                new ImportCommand(expectedRole, expectedFilePath));
    }
}
