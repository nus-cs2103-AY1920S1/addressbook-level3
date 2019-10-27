package seedu.address.model.performance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPerformance.EVENT_ONE;
import static seedu.address.testutil.TypicalPerformance.EVENT_TWO;
import org.junit.jupiter.api.Test;

public class EventTest {

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
}
