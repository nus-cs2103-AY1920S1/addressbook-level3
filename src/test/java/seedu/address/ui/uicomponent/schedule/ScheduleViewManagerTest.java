package seedu.address.ui.uicomponent.schedule;

import org.junit.jupiter.api.Test;
import seedu.address.model.display.schedulewindow.PersonSchedule;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplay;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;
import seedu.address.ui.schedule.ScheduleViewManager;
import seedu.address.ui.schedule.exceptions.InvalidScheduleViewException;

import java.util.ArrayList;
import java.util.List;

import static seedu.address.testutil.Assert.assertThrows;

public class ScheduleViewManagerTest {
    private static final PersonSchedule TEST_EMPTY_SCHEDULE = PersonScheduleBuilder.buildEmptySchedule();
    private static final PersonSchedule TEST_VALID_SCHEDULE = PersonScheduleBuilder.buildValidSchedule();
    private static final PersonSchedule TEST_INVALID_SCHEDULE = PersonScheduleBuilder.buildInvalidSchedule();
    private static final ScheduleWindowDisplay EMPTY_DISPLAY = new ScheduleWindowDisplay(
            new ArrayList<>(List.of(TEST_EMPTY_SCHEDULE)), null, null,
            ScheduleWindowDisplayType.PERSON);
    private static final ScheduleWindowDisplay VALID_DISPLAY = new ScheduleWindowDisplay(
            new ArrayList<>(List.of(TEST_VALID_SCHEDULE)), null, null,
            ScheduleWindowDisplayType.PERSON);
    private static final ScheduleWindowDisplay INVALID_DISPLAY = new ScheduleWindowDisplay(
            new ArrayList<>(List.of(TEST_INVALID_SCHEDULE)), null, null,
            ScheduleWindowDisplayType.PERSON);

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