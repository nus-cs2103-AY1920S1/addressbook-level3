package seedu.exercise.logic.parser;

import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.exercise.testutil.CommonTestData.END_DATE_DESC;
import static seedu.exercise.testutil.CommonTestData.INVALID_CHART_DESC;
import static seedu.exercise.testutil.CommonTestData.INVALID_END_DATE_DESC;
import static seedu.exercise.testutil.CommonTestData.INVALID_END_DATE_TOO_FAR_APART_DESC;
import static seedu.exercise.testutil.CommonTestData.INVALID_STATISTIC_CATEGORY_DESC;
import static seedu.exercise.testutil.CommonTestData.LINE_CHART_DESC;
import static seedu.exercise.testutil.CommonTestData.START_DATE_DESC;
import static seedu.exercise.testutil.CommonTestData.STATISTIC_CATEGORY_DESC;
import static seedu.exercise.testutil.CommonTestData.VALID_END_DATE;
import static seedu.exercise.testutil.CommonTestData.VALID_LINE_CHART;
import static seedu.exercise.testutil.CommonTestData.VALID_START_DATE;
import static seedu.exercise.testutil.CommonTestData.VALID_STATISTIC_CATEGORY_CALORIES;

import org.junit.jupiter.api.Test;

import seedu.exercise.logic.commands.statistic.Statistic;
import seedu.exercise.logic.commands.statistic.StatsCommand;
import seedu.exercise.model.property.Date;

public class StatsCommandParserTest {

    private StatsCommandParser parser = new StatsCommandParser();

    @Test
    public void allFieldsPresent_success() {
        StatsCommand expectedStatsCommand = new StatsCommand(VALID_LINE_CHART, VALID_STATISTIC_CATEGORY_CALORIES,
                new Date(VALID_START_DATE), new Date(VALID_END_DATE));

        assertParseSuccess(parser, LINE_CHART_DESC + STATISTIC_CATEGORY_DESC + START_DATE_DESC
            + END_DATE_DESC, expectedStatsCommand);
    }

    @Test
    public void parse_optionalFieldMissing_success() {
        StatsCommand expectedStatsCommand = new StatsCommand(VALID_LINE_CHART, VALID_STATISTIC_CATEGORY_CALORIES,
                null, null);
        assertParseSuccess(parser, LINE_CHART_DESC + STATISTIC_CATEGORY_DESC, expectedStatsCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE);

        //missing chart prefix
        assertParseFailure(parser, VALID_LINE_CHART + STATISTIC_CATEGORY_DESC + START_DATE_DESC
                + END_DATE_DESC, expectedMessage);

        //missing category prefix
        assertParseFailure(parser, LINE_CHART_DESC + VALID_STATISTIC_CATEGORY_CALORIES + START_DATE_DESC
                + END_DATE_DESC, expectedMessage);

        //all prefix missing
        assertParseFailure(parser, VALID_LINE_CHART + VALID_STATISTIC_CATEGORY_CALORIES + VALID_START_DATE
                + VALID_END_DATE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        //invalid chart
        assertParseFailure(parser, INVALID_CHART_DESC + STATISTIC_CATEGORY_DESC
                + START_DATE_DESC + END_DATE_DESC, Statistic.MESSAGE_INVALID_CHART_TYPE);

        //invalid category
        assertParseFailure(parser, LINE_CHART_DESC + INVALID_STATISTIC_CATEGORY_DESC
                + START_DATE_DESC + END_DATE_DESC, Statistic.MESSAGE_INVALID_CATEGORY);

        //start date present, end date not present
        assertParseFailure(parser, LINE_CHART_DESC + STATISTIC_CATEGORY_DESC + START_DATE_DESC,
                StatsCommandParser.MESSAGE_INVALID_COMMAND);

        //end date present, start date not present
        assertParseFailure(parser, LINE_CHART_DESC + STATISTIC_CATEGORY_DESC + END_DATE_DESC,
                StatsCommandParser.MESSAGE_INVALID_COMMAND);

        //end date is before start date
        assertParseFailure(parser, LINE_CHART_DESC + STATISTIC_CATEGORY_DESC + START_DATE_DESC
                + INVALID_END_DATE_DESC, Date.MESSAGE_INVALID_END_DATE);

        //start date and end date too far apart
        assertParseFailure(parser, LINE_CHART_DESC + STATISTIC_CATEGORY_DESC + START_DATE_DESC
                + INVALID_END_DATE_TOO_FAR_APART_DESC, StatsCommandParser.MESSAGE_INVALID_DATE_RANGE);
    }
}
