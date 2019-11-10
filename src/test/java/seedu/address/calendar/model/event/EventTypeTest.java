package seedu.address.calendar.model.event;

import org.junit.jupiter.api.Test;
import seedu.address.commons.exceptions.IllegalValueException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventTypeTest {

    @Test
    public void isBusy() {
        EventType commitment = EventType.COMMITMENT;
        EventType holiday = EventType.HOLIDAY;
        EventType schoolBreak = EventType.SCHOOL_BREAK;
        EventType trip = EventType.TRIP;

        assertTrue(commitment.isBusy());
        assertTrue(trip.isBusy());
        assertFalse(holiday.isBusy());
        assertFalse(schoolBreak.isBusy());
    }

    @Test
    public void getInstanceFromString_invalidEventType_throwsIllegalValueException() {
        String invalid = "Commit";
        String invalid2 = "schoolBreak";
        String invalid3 = "holiday bye";
        String invalid4 = "!!!what's";

        assertThrows(IllegalValueException.class, () -> EventType.getInstanceFromString(invalid));
        assertThrows(IllegalValueException.class, () -> EventType.getInstanceFromString(invalid2));
        assertThrows(IllegalValueException.class, () -> EventType.getInstanceFromString(invalid3));
        assertThrows(IllegalValueException.class, () -> EventType.getInstanceFromString(invalid4));
    }
}
