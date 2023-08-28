package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SIGNATURE_FORMAT;
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
        assertParseSuccess(parser, "1 Manager A",
                new GenReportCommand(Index.fromZeroBased(FIRST_BODY_ID_NUM.getIdNum()), "Manager A"));
        assertParseSuccess(parser, "1 abcdefghijklmnopqrstuvwxyzabcdabcdefghi",
                new GenReportCommand(Index.fromZeroBased(FIRST_BODY_ID_NUM.getIdNum()),
                        "abcdefghijklmnopqrstuvwxyzabcdabcdefghi"));
        assertParseSuccess(parser, "1",
                new GenReportCommand(Index.fromZeroBased(FIRST_BODY_ID_NUM.getIdNum()), ""));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        //Signature contains numbers
        assertParseFailure(parser, "1 1", MESSAGE_INVALID_SIGNATURE_FORMAT);
        assertParseFailure(parser, "1 1abc", MESSAGE_INVALID_SIGNATURE_FORMAT);
        //Signature contains punctuation
        assertParseFailure(parser, "1 %", MESSAGE_INVALID_SIGNATURE_FORMAT);
        assertParseFailure(parser, "1 %abc", MESSAGE_INVALID_SIGNATURE_FORMAT);
        //Signature too long
        assertParseFailure(parser, "1 abcdefghijklmnopqrstuvwxyzabcdabcdefghij",
                MESSAGE_INVALID_SIGNATURE_FORMAT);
        //Invalid string
        assertParseFailure(parser, "B Manager A", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GenReportCommand.MESSAGE_USAGE));

        //No input given
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GenReportCommand.MESSAGE_USAGE));
    }
}
