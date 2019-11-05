package seedu.address.statistic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.statistic.TypicalStatsPayload.DEFAULT_STATS_PAYLOAD_REVENUE_1;
import static seedu.address.statistic.TypicalStatsPayload.DEFAULT_STATS_PAYLOAD_STATS_PAYLOAD_TEST;
import static seedu.address.statistic.TypicalStatsPayload.DEFAULT_STATS_PAYLOAD_STATS_PAYLOAD_TEST_IS_DEFAULT;
import static seedu.address.statistic.TypicalStatsPayload.ENDING_DATE_4;
import static seedu.address.statistic.TypicalStatsPayload.STARTING_DATE_4;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.statisticcommand.StatisticType;

public class StatsPayloadTest {
    @Test
    void isSameStatsPayload() {
        // same object -> returns true
        assertTrue(DEFAULT_STATS_PAYLOAD_REVENUE_1.equals(DEFAULT_STATS_PAYLOAD_REVENUE_1));
    }

    @Test
    void isCorrectQueryType() {
        assertFalse(DEFAULT_STATS_PAYLOAD_STATS_PAYLOAD_TEST.isDefaultQuery());
        assertTrue(DEFAULT_STATS_PAYLOAD_STATS_PAYLOAD_TEST_IS_DEFAULT.isDefaultQuery());

    }

    @Test
    void areDatesCorrect() {
        assertTrue(STARTING_DATE_4.equals(DEFAULT_STATS_PAYLOAD_STATS_PAYLOAD_TEST.getStartingDate()));
        assertTrue(ENDING_DATE_4.equals(DEFAULT_STATS_PAYLOAD_STATS_PAYLOAD_TEST.getEndingDate()));
    }


    @Test
    void areTypesCorrect() {
        assertTrue(StatisticType.COST.equals(DEFAULT_STATS_PAYLOAD_STATS_PAYLOAD_TEST.getStatisticType()));
        //assertTrue(ENDING_DATE_4.equals(DEFAULT_STATS_PAYLOAD_STATS_PAYLOAD_TEST.getEndingDate()));
    }

    @Test
    void isDisplayCorrect() {
        //System.out.println(DEFAULT_STATS_PAYLOAD_STATS_PAYLOAD_TEST.displayStartingDate());
        assertTrue("2018.6.10".equals(DEFAULT_STATS_PAYLOAD_STATS_PAYLOAD_TEST.displayStartingDate()));
        assertTrue("2020.5.17".equals(DEFAULT_STATS_PAYLOAD_STATS_PAYLOAD_TEST.displayEndingDate()));
    }

}
