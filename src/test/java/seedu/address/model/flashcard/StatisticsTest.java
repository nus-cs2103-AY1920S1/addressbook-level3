package seedu.address.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class StatisticsTest {

    private final Statistics nonStandardStatistics = new Statistics(LocalDate.now().minusDays(10), LocalDate.now()
            .minusDays(9), ScheduleIncrement.SECOND);

    @Test
    public void constructor_noArguments_defaultConstructor() {
        Statistics testStatistics = new Statistics();
        assertEquals(testStatistics.getLastViewed(), LocalDate.now());
        assertEquals(testStatistics.getToViewNext(), LocalDate.now().plusDays(1));
        assertEquals(testStatistics.getCurrentIncrement(), ScheduleIncrement.FIRST);
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Statistics(LocalDate.now(), LocalDate.now().plusDays(1),
                null));
        assertThrows(NullPointerException.class, () -> new Statistics(LocalDate.now(), null,
                ScheduleIncrement.FIRST));
        assertThrows(NullPointerException.class, () -> new Statistics(null, LocalDate.now().plusDays(1),
                ScheduleIncrement.FIRST));
        assertThrows(NullPointerException.class, () -> new Statistics(null, null,
                null));
    }

    @Test
    public void onView_viewedBeforeToViewNextIsDue_onlyLastViewedUpdated() {
        //Example statistics of flashcard with final increment (7 days) last viewed 3 days before current date
        //and to be viewed next 4 days from current date which matches the total of 7 days as per the increment.
        Statistics testStatistics = new Statistics(LocalDate.now().minusDays(3), LocalDate.now().plusDays(4),
                ScheduleIncrement.FINAL);
        testStatistics.onView();
        assertEquals(testStatistics.getLastViewed(), LocalDate.now());
        assertEquals(testStatistics.getCurrentIncrement(), ScheduleIncrement.FINAL); //Should not be updated
        assertEquals(testStatistics.getToViewNext(), LocalDate.now().plusDays(4)); //Should not be updated
    }

    @Test
    public void onView_viewedOnDayToViewNextIsDue_allFieldsUpdated() {
        //Example statistics of flashcard with fourth increment (3 days) last viewed 3 days before current date
        //and to be viewed next on current date which matches the total of 3 days as per the increment.
        Statistics testStatistics = new Statistics(LocalDate.now().minusDays(3), LocalDate.now(),
                ScheduleIncrement.FOURTH);
        testStatistics.onView();
        assertEquals(testStatistics.getLastViewed(), LocalDate.now());
        assertEquals(testStatistics.getCurrentIncrement(), ScheduleIncrement.FIFTH);
        assertEquals(testStatistics.getToViewNext(), LocalDate.now().plusDays(5)); //As per the fifth ScheduleIncrement
    }

    @Test
    public void onView_viewedAfterToViewNextIsDue_allFieldsUpdated() {
        //Example statistics of flashcard with third increment (2 days) last viewed 3 days before current date
        //and to be viewed next on 1 day before current date which matches the total of 2 days as per the increment.
        Statistics testStatistics = new Statistics(LocalDate.now().minusDays(3), LocalDate.now().minusDays(1),
                ScheduleIncrement.THIRD);
        testStatistics.onView();
        assertEquals(testStatistics.getLastViewed(), LocalDate.now());
        assertEquals(testStatistics.getCurrentIncrement(), ScheduleIncrement.FOURTH);
        assertEquals(testStatistics.getToViewNext(), LocalDate.now().plusDays(3)); //As per the fifth ScheduleIncrement
    }

    @Test
    public void method_toString() {
        StringBuilder expected = new StringBuilder();
        expected.append("This flashcard was last viewed on: ");
        expected.append(nonStandardStatistics.getLastViewed().toString());
        expected.append(". This flashcard should next be viewed on ");
        expected.append(nonStandardStatistics.getToViewNext().toString());
        expected.append(" for optimum revision!");
        assertEquals(expected.toString(), nonStandardStatistics.toString());
        assertNotEquals(LocalDate.now(), nonStandardStatistics.toString());
    }

    @Test
    public void equals() {
        Statistics statistics = new Statistics();
        Statistics statisticsLastViewedModified = new Statistics(LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(1), ScheduleIncrement.FIRST);
        Statistics statisticsToViewNextModified = new Statistics(LocalDate.now(), LocalDate.now().plusDays(3),
                ScheduleIncrement.FIRST);
        Statistics statisticsCurrentIncrementModified = new Statistics(LocalDate.now(), LocalDate.now().plusDays(1),
                ScheduleIncrement.SECOND);
        Statistics anotherStatistics = nonStandardStatistics;

        assertTrue(statistics.equals(statistics));
        assertTrue(statistics.equals(new Statistics()));

        assertFalse(statistics.equals(anotherStatistics));
        assertFalse(statistics.equals(statisticsLastViewedModified));
        assertFalse(statistics.equals(statisticsToViewNextModified));
        assertFalse(statistics.equals(statisticsCurrentIncrementModified));
        assertFalse(statistics.equals(null));
    }

}
