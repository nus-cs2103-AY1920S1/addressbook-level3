package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CoverageTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Coverage(null));
    }

    @Test
    public void constructor_invalidCoverage_throwsIllegalArgumentException() {
        String invalidCoverage = "";
        assertThrows(IllegalArgumentException.class, () -> new Coverage(invalidCoverage));
    }

    @Test
    public void isValidCoverage() {
        // null coverage
        assertThrows(NullPointerException.class, () -> Coverage.isValidCoverage(null));

        // invalid coverage
        assertFalse(Coverage.isValidCoverage("")); // empty string
        assertFalse(Coverage.isValidCoverage(" ")); // spaces only
        assertFalse(Coverage.isValidCoverage("^")); // only non-alphanumeric characters
        assertFalse(Coverage.isValidCoverage("days/*")); // contains non-alphanumeric characters
        assertFalse(Coverage.isValidCoverage("10 2 3")); // missing prefixes
        assertFalse(Coverage.isValidCoverage("months/12 2")); // missing prefixes
        assertFalse(Coverage.isValidCoverage("days/32")); // contains days more than 31
        assertFalse(Coverage.isValidCoverage("months/13")); // contains months more than 12
        assertFalse(Coverage.isValidCoverage("years/-1")); // contains negative year

        // valid coverage
        assertTrue(Coverage.isValidCoverage("days/1 months/1 years/1")); // valid prefixes and numbers
        assertTrue(Coverage.isValidCoverage("days/31 months/12 years/1")); // valid prefixes and numbers
        assertTrue(Coverage.isValidCoverage("days/31")); // only has days
        assertTrue(Coverage.isValidCoverage("months/12")); // only has months
        assertTrue(Coverage.isValidCoverage("years/1")); // only has years
        assertTrue(Coverage.isValidCoverage("months/1 years/1")); // only has years and months
        assertTrue(Coverage.isValidCoverage("days/1 years/1")); // only has years and days
        assertTrue(Coverage.isValidCoverage("days/1 months/1")); // only has months and days
    }
}
