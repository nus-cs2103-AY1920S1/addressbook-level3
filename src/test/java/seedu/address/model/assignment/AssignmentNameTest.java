package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AssignmentNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignmentName(null));
    }

    @Test
    public void constructor_invalidAssignmentName_throwsIllegalArgumentException() {
        String invalidAssignmentName = "";
        assertThrows(IllegalArgumentException.class, () -> new AssignmentName(invalidAssignmentName
        ));
    }

    @Test
    public void isValidAssignmentName() {
        // null AssignmentName
        assertThrows(NullPointerException.class, () -> AssignmentName.isValidAssignmentName
                (null));

        // invalid AssignmentName
        assertFalse(AssignmentName.isValidAssignmentName
                ("")); // empty string
        assertFalse(AssignmentName.isValidAssignmentName
                (" ")); // spaces only
        assertFalse(AssignmentName.isValidAssignmentName
                ("^")); // only non-alphanumeric characters
        assertFalse(AssignmentName.isValidAssignmentName
                ("peter*")); // contains non-alphanumeric characters

        // valid AssignmentName
        assertTrue(AssignmentName.isValidAssignmentName
                ("math assignment")); // alphabets only
        assertTrue(AssignmentName.isValidAssignmentName
                ("12345")); // numbers only
        assertTrue(AssignmentName.isValidAssignmentName
                ("math assignment 2")); // alphanumeric characters
        assertTrue(AssignmentName.isValidAssignmentName
                ("English Homework")); // with capital letters
        assertTrue(AssignmentName.isValidAssignmentName
                ("Social Studies Assignment 2")); // long AssignmentName// s
    }
}
