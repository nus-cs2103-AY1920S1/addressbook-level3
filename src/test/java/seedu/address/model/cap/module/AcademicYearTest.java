package seedu.address.model.cap.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AcademicYearTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AcademicYear(null));
    }

    @Test
    public void constructor_invalidAcademicYear_throwsIllegalArgumentException() {
        String invalidAcademicYear = "";
        assertThrows(IllegalArgumentException.class, () -> new AcademicYear(invalidAcademicYear));
    }


    @Test
    public void isValidAcademicYear() {
        // null AcademicYear
        assertThrows(NullPointerException.class, () -> AcademicYear.isValidAcademicYear(null));

        // blank AcademicYear
        assertFalse(AcademicYear.isValidAcademicYear("")); // empty string
        assertFalse(AcademicYear.isValidAcademicYear(" ")); // spaces only

        // invalid parts
        assertFalse(AcademicYear.isValidAcademicYear("asfafef")); // letters instead
        assertFalse(AcademicYear.isValidAcademicYear("fass")); // letters instead
        assertFalse(AcademicYear.isValidAcademicYear(" law")); // letters instead, with a space
        assertFalse(AcademicYear.isValidAcademicYear("1200")); // time
        assertFalse(AcademicYear.isValidAcademicYear("1111")); // random academic year
        assertFalse(AcademicYear.isValidAcademicYear("2131")); // second year is too far away from first year
        assertFalse(AcademicYear.isValidAcademicYear("1921")); // second year is 2 years away
        assertFalse(AcademicYear.isValidAcademicYear("1314")); // out of range
        assertFalse(AcademicYear.isValidAcademicYear("1112")); // out of range
        assertFalse(AcademicYear.isValidAcademicYear("1922")); // second year is 3 years away
        assertFalse(AcademicYear.isValidAcademicYear("1918")); // first year more than second year

        // missing parts
        assertFalse(AcademicYear.isValidAcademicYear("192")); // missing last digit
        assertFalse(AcademicYear.isValidAcademicYear("1")); // missing numbers
        assertFalse(AcademicYear.isValidAcademicYear("201")); // missing numbers

        // valid AcademicYear
        assertTrue(AcademicYear.isValidAcademicYear("1920"));
        assertTrue(AcademicYear.isValidAcademicYear("2122"));
        assertTrue(AcademicYear.isValidAcademicYear("2223"));
        assertTrue(AcademicYear.isValidAcademicYear("1819"));
        assertTrue(AcademicYear.isValidAcademicYear("1718"));
    }
}
