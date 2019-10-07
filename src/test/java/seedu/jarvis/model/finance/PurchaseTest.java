package seedu.jarvis.model.finance;

import static seedu.jarvis.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.financetracker.Purchase;

/**
 * Tests purchase class.
 */
public class PurchaseTest {

    /**
     * Runs all tests in this test class.
     * @param args
     */
    public static void main(String[] args) {
        constructor_nullDescription_throwsNullPointerException();
    }

    @Test
    public static void constructor_nullDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Purchase(null, 0.0));
    }

}
