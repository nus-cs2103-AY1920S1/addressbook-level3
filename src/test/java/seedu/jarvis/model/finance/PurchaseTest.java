package seedu.jarvis.model.finance;

import static seedu.jarvis.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.financetracker.purchase.Purchase;
import seedu.jarvis.model.financetracker.purchase.PurchaseDescription;
import seedu.jarvis.model.financetracker.purchase.PurchaseMoneySpent;

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
