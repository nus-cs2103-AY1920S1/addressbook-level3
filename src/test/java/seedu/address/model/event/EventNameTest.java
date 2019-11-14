package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class EventNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventName(null));
    }

    @Test
    public void constructor_invalidEventName_throwsIllegalArgumentException() {
        String invalidEventName = "";
        assertThrows(IllegalArgumentException.class, () -> new EventName(invalidEventName));
    }

    @Test
    void isValidName() {
        //null event name
        assertThrows(NullPointerException.class, () -> EventName.isValidName(null));

        // invalid name
        assertFalse(EventName.isValidName("")); // empty string
        assertFalse(EventName.isValidName(" ")); // spaces only
        assertFalse(EventName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(EventName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(EventName.isValidName("music concert")); // alphabets only
        assertTrue(EventName.isValidName("12345")); // numbers only
        assertTrue(EventName.isValidName("2nd coming")); // alphanumeric characters
        assertTrue(EventName.isValidName("Graduation Ceremony")); // with capital letters
        assertTrue(EventName.isValidName("CS2103T Practical Exam for AY1920 Semester 1")); // long names
    }


    @Test
    void eventNameEquals() {
        assertEquals(new EventName("niceevent"), new EventName("niceevent"));
        assertNotEquals(new EventName("niceevent"), new EventName("niceEvent")); //Different casing
        assertNotEquals(new EventName("Nice Event   "), new EventName("Nice Event "));
    }
}
