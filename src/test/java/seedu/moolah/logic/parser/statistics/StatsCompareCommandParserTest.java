package seedu.moolah.logic.parser.statistics;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_FIRST_START_DATE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_PERIOD;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_SECOND_START_DATE;
import static seedu.moolah.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.CommandTestUtil;
import seedu.moolah.logic.commands.statistics.StatsCompareCommand;
import seedu.moolah.logic.parser.exceptions.ParseException;
import seedu.moolah.model.expense.Timestamp;

public class StatsCompareCommandParserTest {
    private StatsCompareCommandParser parser = new StatsCompareCommandParser();

    @Test
    void parse_optionalFields_success() throws ParseException {
        assertNotNull(parser.parse(String.format(" %s01-10-2019 %s31-10-2019 %smonth",
                PREFIX_FIRST_START_DATE,
                PREFIX_SECOND_START_DATE,
                PREFIX_PERIOD)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, CommandTestUtil.STATS_WITHOUT_PREFIX,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCompareCommand.MESSAGE_USAGE));
        assertParseFailure(parser, CommandTestUtil.STATS_FIRST_START_DATE_PREFIX_MISSING_INPUT,
                Timestamp.MESSAGE_CONSTRAINTS_GENERAL);
        assertParseFailure(parser, CommandTestUtil.STATS_SECOND_START_DATE_PREFIX_MISSING_INPUT,
                Timestamp.MESSAGE_CONSTRAINTS_GENERAL);
        assertParseFailure(parser, CommandTestUtil.STATS_INVALID_PREFIX,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCompareCommand.MESSAGE_USAGE));
        /*
        assertParseFailure(parser, CommandTestUtil.STATS_DUPLICATE_DATE_PREFIX,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE));
        assertParseFailure(parser, CommandTestUtil.STATS_DUPLICATE_DATE_PREFIX_WITH_COMMAND,
                MESSAGE_REPEATED_PREFIX_COMMAND);
         */
    }
}

