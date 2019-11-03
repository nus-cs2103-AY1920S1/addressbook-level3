package seedu.address.model.statistics;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.statistics.TypicalStatistics;

public class StatisticsTest {

    private static final TypicalStatistics statsBuilder = new TypicalStatistics();

    @Test
    public void makeStatistics_checkStats_success() {
        Statistics typicalStats = statsBuilder.getTypicalStatistics();

        assertTrue(typicalStats.getTotalStudents() == 3);
        assertTrue(typicalStats.getMin() == 10);
        assertTrue(typicalStats.getMax() == 100);
        assertTrue(typicalStats.getMedian() == 60);
    }

}
