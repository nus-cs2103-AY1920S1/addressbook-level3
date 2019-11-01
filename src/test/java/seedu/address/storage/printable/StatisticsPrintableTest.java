package seedu.address.storage.printable;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StatisticsPrintableTest {

    private static final String VALID_FILENAME = "SampleStats.png";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StatisticsPrintable(null, VALID_FILENAME));
    }

}
