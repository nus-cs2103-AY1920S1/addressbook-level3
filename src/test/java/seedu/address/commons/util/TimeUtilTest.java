package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class TimeUtilTest {

    @Test
    public void parseAgeGroup_validYearOfBirth_returnTrue() {
        int currentYear = TimeUtil.getCurrentYear();
        assertEquals(TimeUtil.BELOW_TWENTY, TimeUtil.parseAgeGroup(currentYear));
        assertEquals(TimeUtil.TWENTY_TO_SIXTYFOUR, TimeUtil.parseAgeGroup(currentYear - 20));
        assertEquals(TimeUtil.TWENTY_TO_SIXTYFOUR, TimeUtil.parseAgeGroup(currentYear - 64));
        assertEquals(TimeUtil.ABOVE_SIXTYFIVE, TimeUtil.parseAgeGroup(currentYear - 100));
    }

    @Test
    public void parseAgeGroup_invalidYearOfBirth_throwsIllegalArgumentException() {
        int currentYear = TimeUtil.getCurrentYear();
        int invalidYear = currentYear + 1;
        assertThrows(IllegalArgumentException.class,
            () -> TimeUtil.parseAgeGroup(invalidYear));
    }
}
