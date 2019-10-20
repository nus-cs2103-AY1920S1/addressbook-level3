package seedu.savenus.testutil;

import static seedu.savenus.testutil.TypicalMenu.CARBONARA;
import static seedu.savenus.testutil.TypicalMenu.WAGYU_DONBURI;

import seedu.savenus.model.PurchaseHistory;
import seedu.savenus.model.purchase.Purchase;
import seedu.savenus.model.purchase.TimeOfPurchase;

/**
 * A utility class containing a list of {@code Purchase} objects to be used in tests.
 */
public class TypicalPurchaseHistory {

    private static final TimeOfPurchase VALID_TIMEOFPURCHASE_1 = new TimeOfPurchase("1570680000000");
    private static final TimeOfPurchase VALID_TIMEOFPURCHASE_2 = new TimeOfPurchase("1570976664361");

    private TypicalPurchaseHistory() {} // prevents instantiation

    public static PurchaseHistory getTypicalPurchaseHistory() {
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        purchaseHistory.addPurchase(new Purchase(CARBONARA, VALID_TIMEOFPURCHASE_1));
        purchaseHistory.addPurchase(new Purchase(WAGYU_DONBURI, VALID_TIMEOFPURCHASE_2));
        return purchaseHistory;
    }
}
