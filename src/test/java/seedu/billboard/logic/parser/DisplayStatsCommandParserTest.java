package seedu.billboard.logic.parser;

import static seedu.billboard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.billboard.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.billboard.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;
import seedu.billboard.logic.commands.DisplayStatsCommand;
import seedu.billboard.model.statistics.formats.StatisticsFormat;

public class DisplayStatsCommandParserTest {

    private DisplayStatsCommandParser parser = new DisplayStatsCommandParser();

    @Test
    public void parse_validArgs_returnsDisplayStatsCommand() {
        String args = StatisticsFormat.BREAKDOWN.getName();
        DisplayStatsCommand expectedCommand = new DisplayStatsCommand(StatisticsFormat.BREAKDOWN);
        assertParseSuccess(parser, args, expectedCommand);

        String trailingWhitespace = StatisticsFormat.TIMELINE.getName() + "  ";
        DisplayStatsCommand expectedCommand2 = new DisplayStatsCommand(StatisticsFormat.TIMELINE);
        assertParseSuccess(parser, trailingWhitespace, expectedCommand2);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String emptyInput = "";
        assertParseFailure(parser, emptyInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayStatsCommand.MESSAGE_USAGE));

        String whitespaceOnly = "   ";
        assertParseFailure(parser, whitespaceOnly,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayStatsCommand.MESSAGE_USAGE));

        String randomInput = "qjoij qkpo1d";
        assertParseFailure(parser, randomInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayStatsCommand.MESSAGE_USAGE));
    }
}
