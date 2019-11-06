package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Attendance(null));
    }

    @Test
    public void constructor_invalidAttendance_throwsIllegalArgumentException() {
        String invalidAttendance = "present";
        assertThrows(IllegalArgumentException.class, () -> new Attendance(invalidAttendance));
    }

    @Test
    public void isValidAttendance() {
        // null attendance
        assertThrows(NullPointerException.class, () -> Attendance.isValidAttendance(null));

        // invalid attendances
        assertFalse(Attendance.isValidAttendance(""));
        assertFalse(Attendance.isValidAttendance(" "));
        assertFalse(Attendance.isValidAttendance("^"));
        assertFalse(Attendance.isValidAttendance("here"));

        // valid attendances
        assertTrue(Attendance.isValidAttendance("1"));
        assertTrue(Attendance.isValidAttendance("23"));
        assertTrue(Attendance.isValidAttendance("79"));
        assertTrue(Attendance.isValidAttendance("99"));
    }
}
