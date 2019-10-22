package seedu.savenus.model.purchase;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a PurchaseHistory
 */
public interface ReadOnlyPurchaseHistory {
    /**
     * Returns the user's {@code PurchaseHistoryList}.
     */
    ObservableList<Purchase> getPurchaseHistoryList();
}
