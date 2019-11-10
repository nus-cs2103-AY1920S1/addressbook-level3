package seedu.billboard.logic.parser;

import static seedu.billboard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_GROUPING;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_INTERVAL;
import static seedu.billboard.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.billboard.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.logic.commands.DisplayStatsCommand;
import seedu.billboard.model.statistics.formats.ExpenseGrouping;
import seedu.billboard.model.statistics.formats.StatisticsFormat;
import seedu.billboard.model.statistics.formats.StatisticsFormatOptions;

public class DisplayStatsCommandParserTest {
    private static final String BREAKDOWN_ARG = StatisticsFormat.BREAKDOWN.getName();
    private static final String TIMELINE_ARG = StatisticsFormat.TIMELINE.getName();
    private static final String WEEK_INTERVAL_OPTION = " " + PREFIX_INTERVAL + DateInterval.WEEK.getName();
    private static final String TAG_GROUPING_OPTION = " " + PREFIX_GROUPING + ExpenseGrouping.TAG.getName();

    private DisplayStatsCommandParser parser = new DisplayStatsCommandParser();

    @Test
    public void parse_validArgs_returnsDisplayStatsCommand() {
        StatisticsFormatOptions emptyOptions = StatisticsFormatOptions.emptyOption();
        DisplayStatsCommand expectedCommand = new DisplayStatsCommand(StatisticsFormat.BREAKDOWN, emptyOptions);
        assertParseSuccess(parser, BREAKDOWN_ARG, expectedCommand);

        String trailingWhitespace = TIMELINE_ARG + "  ";
        DisplayStatsCommand expectedCommand2 = new DisplayStatsCommand(StatisticsFormat.TIMELINE, emptyOptions);
        assertParseSuccess(parser, trailingWhitespace, expectedCommand2);

        String leadingWhitespace = "    " + TIMELINE_ARG;
        assertParseSuccess(parser, leadingWhitespace, expectedCommand2);

        String argsWithTimelineOption = TIMELINE_ARG + WEEK_INTERVAL_OPTION;
        DisplayStatsCommand expectedCommandWithTimelineOption =
                new DisplayStatsCommand(
                        StatisticsFormat.TIMELINE,
                        StatisticsFormatOptions.withOptions(DateInterval.WEEK, null));
        assertParseSuccess(parser, argsWithTimelineOption, expectedCommandWithTimelineOption);

        String argsWithGroupingOption = TIMELINE_ARG + TAG_GROUPING_OPTION;
        DisplayStatsCommand expectedCommandWithGroupingOption =
                new DisplayStatsCommand(
                        StatisticsFormat.TIMELINE,
                        StatisticsFormatOptions.withOptions(null, ExpenseGrouping.TAG));
        assertParseSuccess(parser, argsWithGroupingOption, expectedCommandWithGroupingOption);

        String argsWithAllOptions = TIMELINE_ARG + TAG_GROUPING_OPTION + WEEK_INTERVAL_OPTION;
        DisplayStatsCommand expectedCommandWithAllOptions =
                new DisplayStatsCommand(
                        StatisticsFormat.TIMELINE,
                        StatisticsFormatOptions.withOptions(DateInterval.WEEK, ExpenseGrouping.TAG));
        assertParseSuccess(parser, argsWithAllOptions, expectedCommandWithAllOptions);
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
