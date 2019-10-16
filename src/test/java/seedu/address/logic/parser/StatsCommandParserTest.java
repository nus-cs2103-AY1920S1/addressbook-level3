package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.logic.commands.StatsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.expense.Timestamp;
import seedu.address.model.statistics.Statistics;

class StatsCommandParserTest {
    private StatsCommandParser parser = new StatsCommandParser();

    @Test
    void parse_optionalFields_success() throws ParseException {
        //correct order
        assertNotNull(parser.parse(String.format(" %s01-10-2019 %s31-10-2019",
                PREFIX_START_DATE,
                PREFIX_END_DATE)));
        //flipped
        assertNotNull(parser.parse(String.format(" %s31-10-2019 %s01-10-2019",
                PREFIX_END_DATE,
                PREFIX_START_DATE)));
        //start date with white space
        assertNotNull(parser.parse(String.format(" %s01-10-2019      ",
                PREFIX_START_DATE)));
        //start date
        assertNotNull(parser.parse(String.format(" %s01-10-2019",
                PREFIX_START_DATE)));
        //end date
        assertNotNull(parser.parse(String.format(" %s31-10-2019 ",
                PREFIX_END_DATE)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE));
        assertParseFailure(parser, CommandTestUtil.STATS_WITHOUT_TAG,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE));
        assertParseFailure(parser, CommandTestUtil.STATS_PREFIX_WITHOUT_INPUT, Timestamp.MESSAGE_CONSTRAINTS_DATE);
        assertParseFailure(parser, CommandTestUtil.STATS_INVALID_PREFIX,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE));
        assertParseFailure(parser, CommandTestUtil.STATS_HIGHER_END_DATE, Statistics.MESSAGE_CONSTRAINTS_END_DATE);
        assertParseFailure(parser, CommandTestUtil.STATS_DUPLICATE_TAG,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE));
    }
}
