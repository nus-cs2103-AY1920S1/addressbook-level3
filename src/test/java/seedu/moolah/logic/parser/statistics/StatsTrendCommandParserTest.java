package seedu.moolah.logic.parser.statistics;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.moolah.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.CommandTestUtil;
import seedu.moolah.logic.commands.statistics.StatsTrendCommand;
import seedu.moolah.logic.parser.exceptions.ParseException;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.model.statistics.Statistics;

class StatsTrendCommandParserTest {

    private StatsTrendCommandParser parser = new StatsTrendCommandParser();

    @Test
    void parse_optionalFields_success() throws ParseException {
        //start date and end date in the usual order
        assertNotNull(parser.parse(String.format(" %s01-10-2019 %s31-10-2019 %sCaTeGoRy",
                PREFIX_START_DATE,
                PREFIX_END_DATE,
                PREFIX_MODE)));
        //start date and end date in the usual order, with mode prefix
        assertNotNull(parser.parse(String.format(" %s01-10-2019 %s31-10-2019 %sBuDgeT",
                PREFIX_START_DATE,
                PREFIX_END_DATE,
                PREFIX_MODE)));
        //with start date and end date but flipped, with mode prefix
        assertNotNull(parser.parse(String.format(" %s31-10-2019 %s01-10-2019 %sBuDgeT",
                PREFIX_END_DATE,
                PREFIX_START_DATE,
                PREFIX_MODE)));
        //start date and mode prefix
        assertNotNull(parser.parse(String.format(" %s01-10-2019 %sCATEGORY",
                PREFIX_START_DATE, PREFIX_MODE)));
        //end date and mode prefix
        assertNotNull(parser.parse(String.format(" %s31-10-2019 %sBudGET",
                PREFIX_END_DATE, PREFIX_MODE)));
        //just mode prefix
        assertNotNull(parser.parse(String.format(" %sBudGet", PREFIX_MODE)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, CommandTestUtil.STATS_WITHOUT_PREFIX,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsTrendCommand.MESSAGE_USAGE));
        assertParseFailure(parser, CommandTestUtil.STATS_TREND_START_DATE_PREFIX_MISSING_INPUT,
                Timestamp.MESSAGE_CONSTRAINTS_GENERAL);
        assertParseFailure(parser, CommandTestUtil.STATS_TREND_END_DATE_PREFIX_MISSING_INPUT,
                Timestamp.MESSAGE_CONSTRAINTS_GENERAL);
        assertParseFailure(parser, CommandTestUtil.STATS_INVALID_PREFIX,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsTrendCommand.MESSAGE_USAGE));
        assertParseFailure(parser, CommandTestUtil.STATS_TREND_HIGHER_END_DATE,
                Statistics.MESSAGE_CONSTRAINTS_END_DATE);
        /*
        assertParseFailure(parser, CommandTestUtil.STATS_DUPLICATE_DATE_PREFIX,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE));
        assertParseFailure(parser, CommandTestUtil.STATS_DUPLICATE_DATE_PREFIX_WITH_COMMAND,
                MESSAGE_REPEATED_PREFIX_COMMAND);
         */
    }

    //consider checking for natural language commands



}




