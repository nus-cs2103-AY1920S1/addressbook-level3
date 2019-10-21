package seedu.savenus.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.savenus.model.purchase.Purchase;
import seedu.savenus.model.purchase.PurchaseHistoryList;

/**
 * Wraps all purchase history data
 */
public class PurchaseHistory implements ReadOnlyPurchaseHistory {

    private final PurchaseHistoryList purchaseHistoryList;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        purchaseHistoryList = new PurchaseHistoryList();
    }

    public PurchaseHistory() {}

    /**
     * Creates an Menu using the foods in the {@code toBeCopied}
     */
    public PurchaseHistory(ReadOnlyPurchaseHistory toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Set user's {@code PurchaseHistory}.
     */
    public void setPurchaseHistoryList(List<Purchase> purchaseHistoryList) {
        this.purchaseHistoryList.setPurchases(purchaseHistoryList);
    }

    /**
     * Resets the existing data of this {@code Menu} with {@code newData}.
     */
    public void resetData(ReadOnlyPurchaseHistory newData) {
        requireNonNull(newData);

        setPurchaseHistoryList(newData.getPurchaseHistoryList());
    }

    //// purchases operations

    /**
     * Add food to user's {@code PurchaseHistory}.
     */
    public void addPurchase(Purchase purchase) {
        purchaseHistoryList.add(purchase);
    }

    /**
     * Remove food in user's {@code PurchaseHistory}.
     */
    public void removePurchase(Purchase purchase) {
        List<Purchase> currentPurchaseHistory = getPurchaseHistoryList().stream().collect(Collectors.toList());
        currentPurchaseHistory.remove(purchase);
        setPurchaseHistoryList(currentPurchaseHistory);
    }


    //// util methods
    @Override
    public String toString() {
        return purchaseHistoryList.asUnmodifiableObservableList().size()
                + " Purchases made";
    }

    /**
     * Get user's {@code PurchaseHistoryList}.
     */
    public ObservableList<Purchase> getPurchaseHistoryList() {
        return purchaseHistoryList.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PurchaseHistory // instanceof handles nulls
                && purchaseHistoryList.equals(((PurchaseHistory) other).purchaseHistoryList));
    }

    @Override
    public int hashCode() {
        return purchaseHistoryList.hashCode();
    }
}
