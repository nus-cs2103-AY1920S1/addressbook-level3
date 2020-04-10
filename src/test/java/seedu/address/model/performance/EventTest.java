package seedu.address.model.performance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPerformance.EVENT_ONE;
import static seedu.address.testutil.TypicalPerformance.EVENT_TWO;

import org.junit.jupiter.api.Test;

public class EventTest {

    private static final String INVALID_EVENT_NAME = " 50m breaststroke";
    private static final String VALID_EVENT_NAME = "50m breaststroke";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Event(null));
        assertThrows(NullPointerException.class, () -> new Event(null, null));
    }

    @Test
    public void isSameEvent() {
        // same object -> returns true
        assertTrue(EVENT_ONE.isSameEvent(EVENT_ONE));

        // null -> returns false
        assertFalse(EVENT_ONE.isSameEvent(null));

        // different name -> returns false
        assertFalse(EVENT_ONE.isSameEvent(EVENT_TWO));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Event eventCopy = new Event(EVENT_ONE.getName());
        assertTrue(EVENT_ONE.equals(eventCopy));

        // same object -> returns true
        assertTrue(EVENT_ONE.equals(EVENT_ONE));

        // null -> returns false
        assertFalse(EVENT_ONE.equals(null));

        // different type -> returns false
        assertFalse(EVENT_ONE.equals(5));

        // different event -> returns false
        assertFalse(EVENT_ONE.equals(EVENT_TWO));
    }

    @Test
    public void isValidName() {
        // no leading whitespace -> is valid
        assertTrue(Event.isValidName(VALID_EVENT_NAME));

        // leading whitespace -> is not valid
        assertFalse(Event.isValidName(INVALID_EVENT_NAME));
    }
}
