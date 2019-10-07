package seedu.jarvis.model.finance;

import org.junit.jupiter.api.Test;
import seedu.jarvis.model.financetracker.Purchase;

import static seedu.jarvis.testutil.Assert.assertThrows;

public class PurchaseTest {
    @Test
    public void constructor_nullDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Purchase(null, 0.0));
    }

}
