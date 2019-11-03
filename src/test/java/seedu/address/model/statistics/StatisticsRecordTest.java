package seedu.address.model.statistics;

import static seedu.address.testutil.Assert.assertThrows;

import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.testutil.statistics.TypicalStatistics;

public class StatisticsRecordTest {

    private final StatisticsRecord statisticsRecord = new StatisticsRecord();
    private final Statistics stats = new TypicalStatistics().getTypicalStatistics();

    @Test
    public void constructor() {
        Assertions.assertEquals(Collections.emptyList(), statisticsRecord.getProcessedStatistics());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> statisticsRecord.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyNotesRecord_replacesData() {
        StatisticsRecord newData = new TypicalStatistics().getTypicalStatisticsRecord();
        statisticsRecord.resetData(newData);
        Assertions.assertEquals(newData, statisticsRecord);
    }

    @Test
    public void setData_withValidStatistics_replacesData() {
        statisticsRecord.setStatistics(stats);
        StatisticsRecord newData = new TypicalStatistics().getTypicalStatisticsRecord();
        newData.setStatistics(stats);
        Assertions.assertEquals(newData, statisticsRecord);
    }

    @Test
    public void getStatisticsList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
            statisticsRecord.getProcessedStatistics().remove(0));
    }
}
