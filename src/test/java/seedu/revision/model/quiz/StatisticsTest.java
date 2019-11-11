package seedu.revision.model.quiz;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class StatisticsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Statistics(null));
    }

    @Test
    public void constructor_invalidStatistics_throwsIllegalArgumentException() {
        String invalidStatistics = "";
        assertThrows(IllegalArgumentException.class, () -> new Statistics(invalidStatistics));
    }

    @Test
    public void isValidStatistics() {
        // null statistics
        assertThrows(NullPointerException.class, () -> Statistics.isValidStatistics(null));

        // invalid statistics
        assertFalse(Statistics.isValidStatistics("")); // empty string
        assertFalse(Statistics.isValidStatistics(" ")); // spaces only
        assertFalse(Statistics.isValidStatistics("91")); // one number
        assertFalse(Statistics.isValidStatistics("Statistics")); // non-numeric
        assertFalse(Statistics.isValidStatistics("9011p041")); // alphabets within digits
        assertFalse(Statistics.isValidStatistics("9312 1534")); // spaces within digits
        assertFalse(Statistics.isValidStatistics("25/25,a/a,b/b,c/c")); // alphabets in scores

        // valid statistics
        assertTrue(Statistics.isValidStatistics("25/25,10/10,8/8,7/7"));
    }
}

