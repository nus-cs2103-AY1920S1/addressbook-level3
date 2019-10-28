package seedu.address.model.question;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class SubjectTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Subject(null));
    }

    @Test
    public void constructor_invalidSubject_throwsIllegalArgumentException() {
        String invalidSubject = "";
        assertThrows(IllegalArgumentException.class, () -> new Subject(invalidSubject));
    }

    @Test
    void isValidSubject() {
        // null difficulty
        assertThrows(NullPointerException.class, () -> Subject.isValidSubject(null));

        // invalid difficulty
        assertFalse(Subject.isValidSubject("")); // empty string
        assertFalse(Subject.isValidSubject(" ")); // spaces only

        // valid phone numbers
        assertTrue(Subject.isValidSubject("CS2103T"));
        assertTrue(Subject.isValidSubject("Math"));
        assertTrue(Subject.isValidSubject("Software Engineering")); // long difficulty
    }
}
