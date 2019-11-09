package seedu.savenus.model.purchase;

import javafx.collections.ObservableList;

//@@author raikonen
/**
 * Unmodifiable view of a PurchaseHistory
 */
public interface ReadOnlyPurchaseHistory {
    /**
     * Returns the user's {@code PurchaseHistoryList}.
     */
    ObservableList<Purchase> getPurchaseHistoryList();
}
