package seedu.address.ui.uicomponent.schedule;

import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.display.scheduledisplay.PersonScheduleDisplay;
import seedu.address.model.display.scheduledisplay.ScheduleDisplay;
import seedu.address.model.display.timeslots.PersonSchedule;
import seedu.address.ui.schedule.ScheduleViewManager;
import seedu.address.ui.schedule.exceptions.InvalidScheduleViewException;

public class ScheduleViewManagerTest {
    private static final PersonSchedule TEST_EMPTY_SCHEDULE = PersonScheduleBuilder.buildEmptySchedule();
    private static final PersonSchedule TEST_VALID_SCHEDULE = PersonScheduleBuilder.buildValidSchedule();
    private static final PersonSchedule TEST_INVALID_SCHEDULE = PersonScheduleBuilder.buildInvalidSchedule();
    private static final ScheduleDisplay EMPTY_DISPLAY = new PersonScheduleDisplay(
            new ArrayList<>(List.of(TEST_EMPTY_SCHEDULE)));
    private static final ScheduleDisplay VALID_DISPLAY = new PersonScheduleDisplay(
            new ArrayList<>(List.of(TEST_VALID_SCHEDULE)));
    private static final ScheduleDisplay INVALID_DISPLAY = new PersonScheduleDisplay(
            new ArrayList<>(List.of(TEST_INVALID_SCHEDULE)));

    @Test
    public void validScheduleTest() throws InvalidScheduleViewException {
        //Empty.
        ScheduleViewManager.getInstanceOf(EMPTY_DISPLAY);
        //Valid.
        ScheduleViewManager.getInstanceOf(VALID_DISPLAY);
    }

    @Test
    public void invalidScheduleTest() {
        assertThrows(InvalidScheduleViewException.class, () -> ScheduleViewManager.getInstanceOf(INVALID_DISPLAY));
    }
}
