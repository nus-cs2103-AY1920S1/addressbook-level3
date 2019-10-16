package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIdentificationNumbers.FIRST_BODY_ID_NUM;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GenReportCommand;

public class GenReportCommandParserTest {

    private GenReportCommandParser parser = new GenReportCommandParser();

    @Test
    public void parse_validArgs_returnsGenReportCommand() {
        assertParseSuccess(parser, "B0",
                new GenReportCommand(Index.fromZeroBased(FIRST_BODY_ID_NUM.getIdNum())));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        //Invalid string
        assertParseFailure(parser, "B", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GenReportCommand.MESSAGE_USAGE));

        //Invalid first char
        assertParseFailure(parser, "A1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GenReportCommand.MESSAGE_USAGE));

        //No input given
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GenReportCommand.MESSAGE_USAGE));
    }
}
