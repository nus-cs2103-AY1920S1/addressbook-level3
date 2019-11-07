package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class EventSchedulePrefsTest {

    private static final EventScheduleViewMode VALID_VIEW_MODE = EventScheduleViewMode.DAILY;
    private static final EventScheduleViewMode VALID_OTHER_VIEW_MODE = EventScheduleViewMode.WEEKLY;
    private static final LocalDateTime VALID_TARGET_VIEW_DATE_TIME = LocalDateTime.parse("2019-01-01T03:00");
    private static final LocalDateTime VALID_OTHER_TARGET_VIEW_DATE_TIME = LocalDateTime.parse("2019-01-02T04:00");

    @Test
    public void constructor_nullViewMode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new EventSchedulePrefs(null, VALID_TARGET_VIEW_DATE_TIME));
    }

    @Test
    public void constructor_nullTargetViewDateTime_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new EventSchedulePrefs(VALID_VIEW_MODE, null));
    }

    @Test
    public void setViewMode_validViewMode_returnsTrue() {
        EventSchedulePrefs eventSchedulePrefs = new EventSchedulePrefs(VALID_VIEW_MODE, VALID_TARGET_VIEW_DATE_TIME);
        eventSchedulePrefs.setViewMode(VALID_OTHER_VIEW_MODE);
        EventSchedulePrefs expectedEventSchedulePrefs =
                new EventSchedulePrefs(VALID_OTHER_VIEW_MODE, VALID_TARGET_VIEW_DATE_TIME);
        assertEquals(expectedEventSchedulePrefs, eventSchedulePrefs);
    }

    @Test
    public void setViewMode_nullViewMode_throwsNullPointerException() {
        EventSchedulePrefs eventSchedulePrefs = new EventSchedulePrefs(VALID_VIEW_MODE, VALID_TARGET_VIEW_DATE_TIME);
        assertThrows(NullPointerException.class, () -> eventSchedulePrefs.setViewMode(null));
    }

    @Test
    public void setTargetViewDateTime_nullTargetViewDate_throwsNullPointerException() {
        EventSchedulePrefs eventSchedulePrefs = new EventSchedulePrefs(VALID_VIEW_MODE, VALID_TARGET_VIEW_DATE_TIME);
        assertThrows(NullPointerException.class, () -> eventSchedulePrefs.setTargetViewDateTime(null));
    }

    @Test
    public void setTargetViewDateTime_validTargetDateTime_returnsTrue() {
        EventSchedulePrefs eventSchedulePrefs = new EventSchedulePrefs(VALID_VIEW_MODE, VALID_TARGET_VIEW_DATE_TIME);
        eventSchedulePrefs.setTargetViewDateTime(VALID_OTHER_TARGET_VIEW_DATE_TIME);
        EventSchedulePrefs expectedEventSchedulePrefs =
                new EventSchedulePrefs(VALID_VIEW_MODE, VALID_OTHER_TARGET_VIEW_DATE_TIME);
        assertEquals(expectedEventSchedulePrefs, eventSchedulePrefs);
    }
}
