package seedu.address.model.cap.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SemesterPeriodTest {

    @Test
    public void constructor_invalidSemesterPeriod_throwsIllegalArgumentException() {
        int invalidSemesterPeriod = 12431240;
        assertThrows(IllegalArgumentException.class, () -> new SemesterPeriod(invalidSemesterPeriod));
    }

    @Test
    public void isValidSemesterPeriod() {

        // invalid parts
        assertFalse(SemesterPeriod.isValidSemesterPeriod(124124));
        assertFalse(SemesterPeriod.isValidSemesterPeriod(1111));
        assertFalse(SemesterPeriod.isValidSemesterPeriod(10));
        assertFalse(SemesterPeriod.isValidSemesterPeriod(-10));
        assertFalse(SemesterPeriod.isValidSemesterPeriod(-124));
        assertFalse(SemesterPeriod.isValidSemesterPeriod(-0000));
        assertFalse(SemesterPeriod.isValidSemesterPeriod(0));
        assertFalse(SemesterPeriod.isValidSemesterPeriod(86723));

        // valid SemesterPeriod
        assertTrue(SemesterPeriod.isValidSemesterPeriod(4));
        assertTrue(SemesterPeriod.isValidSemesterPeriod(3));
        assertTrue(SemesterPeriod.isValidSemesterPeriod(2));
        assertTrue(SemesterPeriod.isValidSemesterPeriod(1));
    }
}
