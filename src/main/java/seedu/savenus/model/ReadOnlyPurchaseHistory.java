package seedu.savenus.model;

import javafx.collections.ObservableList;
import seedu.savenus.model.purchase.Purchase;

/**
 * Unmodifiable view of a PurchaseHistory
 */
public interface ReadOnlyPurchaseHistory {
    /**
     * Returns the user's {@code PurchaseHistoryList}.
     */
    ObservableList<Purchase> getPurchaseHistoryList();
}
