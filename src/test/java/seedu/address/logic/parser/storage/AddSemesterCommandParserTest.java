package seedu.address.logic.parser.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.storage.AddSemesterCommand;
import seedu.address.model.semester.SemesterName;

/**
 * Test class for {@code AddSemesterCommandParser}.
 */
public class AddSemesterCommandParserTest {

    private AddSemesterCommandParser parser = new AddSemesterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddSemesterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidSemesterName_throwsParseException() {
        assertParseFailure(parser, "y1s3", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddSemesterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_mainstreamSemesterName_throwsParseException() {
        assertParseFailure(parser, "y1s1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddSemesterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validSemester_returnsAddSemesterCommand() {
        AddSemesterCommand expectedAddSemesterCommand = new AddSemesterCommand(SemesterName.Y1ST1);
        assertParseSuccess(parser, "y1sT1", expectedAddSemesterCommand);
    }

}
