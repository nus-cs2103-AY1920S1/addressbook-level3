package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AssignmentDeadlineTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignmentDeadline(null));
    }

    @Test
    public void constructor_invalidAssignmentDeadline_throwsIllegalArgumentException() {
        String invalidAssignmentDeadline = "";
        assertThrows(IllegalArgumentException.class, () -> new AssignmentDeadline(invalidAssignmentDeadline));
    }

    @Test
    public void isValidAssignmentDeadline() {
        // null AssignmentDeadline
        assertThrows(NullPointerException.class, () -> AssignmentDeadline.isValidAssignmentDeadline(null));

        // invalid AssignmentDeadline
        assertFalse(AssignmentDeadline.isValidAssignmentDeadline("")); // empty string
        assertFalse(AssignmentDeadline.isValidAssignmentDeadline(" ")); // spaces only
        assertFalse(AssignmentDeadline.isValidAssignmentDeadline("peter*")); // contains non-date formatted
        assertFalse(AssignmentDeadline.isValidAssignmentDeadline("30/30/2010 1800")); // contains non-existent date
        assertFalse(AssignmentDeadline.isValidAssignmentDeadline("01/01/2010 3600")); // contains non-existent time
        assertFalse(AssignmentDeadline.isValidAssignmentDeadline("24-07-2019 2300")); // contains incorrect date format
        assertFalse(AssignmentDeadline.isValidAssignmentDeadline("24/07/2019 23:00")); // contains incorrect time format

        // valid AssignmentDeadline
        assertTrue(AssignmentDeadline.isValidAssignmentDeadline("18/08/2019 1800")); // date and time only
        assertTrue(AssignmentDeadline.isValidAssignmentDeadline("23/04/2300 2000")); // date and time only
    }
}
