package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class EventIdTest {

    @Test
    void isValidId() {
        //null event id
        assertThrows(NullPointerException.class, () -> EventName.isValidName(null));

        // invalid ud
        assertFalse(EventId.isValidId("")); // empty string
        assertFalse(EventId.isValidId("2")); // 1digit
        assertFalse(EventId.isValidId("99")); // 2digit

        // valid id
        assertTrue(EventId.isValidId("000"));
        assertTrue(EventId.isValidId("005"));
        assertTrue(EventId.isValidId("035"));
        assertTrue(EventId.isValidId("999"));
    }

    @Test
    void eventIdEquals() {
        assertNotEquals(new EventId(), new EventId()); //No two events are the same
    }
}
