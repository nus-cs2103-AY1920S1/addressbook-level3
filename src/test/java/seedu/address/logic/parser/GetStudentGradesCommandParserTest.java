package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OBJECT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.GetStudentGradesCommand;

public class GetStudentGradesCommandParserTest {
    private GetStudentGradesCommandParser parser = new GetStudentGradesCommandParser();

    @Test
    public void parse_validArgs_returnsGetStudentGradesCommand() {
        assertParseSuccess(parser, "1", new GetStudentGradesCommand(INDEX_FIRST_OBJECT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GetStudentGradesCommand.MESSAGE_USAGE));
    }
}
