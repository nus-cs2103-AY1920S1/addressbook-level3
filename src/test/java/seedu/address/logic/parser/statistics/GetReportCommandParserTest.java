package seedu.address.logic.parser.statistics;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.statistics.GetReportCommand;

public class GetReportCommandParserTest {

    private GetReportCommandParser parser = new GetReportCommandParser();

    @Test
    public void parse_invalidCommandFormat_failure() {
        assertParseFailure(parser, " ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                GetReportCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " a", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                GetReportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validIndex_success() {
        GetReportCommand expectedCommand = new GetReportCommand(Index.fromOneBased(1));
        assertParseSuccess(parser, " 1", expectedCommand);
    }

    /* Will edit later
    @Test
    public void parse_invalidIndex_failure() {
        assertThrows(IndexOutOfBoundsException.class, () -> new GetReportCommandParser().parse("-2"));
    }*/

}
