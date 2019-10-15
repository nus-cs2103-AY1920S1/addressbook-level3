package seedu.address.logic.parser;

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

}
