package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SEMESTER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.verification.ValidModsCommand;
import seedu.address.logic.parser.verification.ValidModsCommandParser;
import seedu.address.model.semester.SemesterName;

public class ValidModsCommandParserTest {

    private ValidModsCommandParser parser = new ValidModsCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ValidModsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_notSemesterFormat_throwsParseException() {
        assertParseFailure(parser, "Y2S0", MESSAGE_INVALID_SEMESTER);
        assertParseFailure(parser, "y6s2", MESSAGE_INVALID_SEMESTER);
        assertParseFailure(parser, "s1y3", MESSAGE_INVALID_SEMESTER);
    }

    @Test
    public void parse_semesterFormat_returnsValidModsCommand() {
        ValidModsCommand expectedValidModsCommand = new ValidModsCommand(SemesterName.Y1S2);
        assertParseSuccess(parser, "y1s2", expectedValidModsCommand);
        assertParseSuccess(parser, " Y1S2 ", expectedValidModsCommand);
    }
}
