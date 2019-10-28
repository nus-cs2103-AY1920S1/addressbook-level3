package seedu.jarvis.model.finance;

import static seedu.jarvis.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.finance.purchase.Purchase;
import seedu.jarvis.model.finance.purchase.PurchaseDescription;
import seedu.jarvis.model.finance.purchase.PurchaseMoneySpent;

/**
 * Tests purchase class.
 */
public class PurchaseTest {

    @Test
    public void constructor_nullDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Purchase(new PurchaseDescription(null),
                new PurchaseMoneySpent("0.0"), LocalDate.parse("", Purchase.getDateFormat())));
    }
}
