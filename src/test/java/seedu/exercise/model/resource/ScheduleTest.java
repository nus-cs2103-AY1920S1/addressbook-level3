package seedu.exercise.model.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.exercise.testutil.TestUtil.assertCommonEqualsTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.exercise.testutil.typicalutil.TypicalSchedule;

public class ScheduleTest {

    private Schedule legSchedule = TypicalSchedule.VALID_SCHEDULE_LEG_DATE;
    private Schedule legScheduleOnAnotherDate = TypicalSchedule.VALID_SCHEDULE_LEG_DATE_2;

    @Test
    public void equals_variousScenarios_success() {
        assertCommonEqualsTest(legSchedule);

        //diff date -> false
        Assertions.assertFalse(legSchedule.equals(legScheduleOnAnotherDate));
    }

    @Test
    public void toString_returnsCorrectlyFormattedString() {
        //Replaces the first date 13/12/2019 to 12/12/2019
        assertEquals(legScheduleOnAnotherDate.toString().replaceFirst("3", "2"),
                legSchedule.toString());
    }
}
