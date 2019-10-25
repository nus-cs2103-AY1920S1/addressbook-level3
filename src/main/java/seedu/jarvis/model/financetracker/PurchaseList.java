package seedu.jarvis.model.financetracker;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.model.financetracker.exceptions.PurchaseNotFoundException;
import seedu.jarvis.model.financetracker.purchase.Purchase;

/**
 * Manages list of monthly expenditures made by the user.
 */
public class PurchaseList {
    private ObservableList<Purchase> internalPurchaseList = FXCollections.observableArrayList();
    private final ObservableList<Purchase> internalUnmodifiablePurchaseList =
            FXCollections.unmodifiableObservableList(internalPurchaseList);

    //=========== Reset Methods ==================================================================================

    /**
     * Default constructor to be used when JARVIS starts up.
     */
    public PurchaseList() {

    }

    /**
     * Constructs an PurchaseList with reference from another PurchaseList,
     * updating all existing fields from another PurchaseList.
     */
    public PurchaseList(ObservableList<Purchase> internalPurchaseList) {
        requireNonNull(internalPurchaseList);
        this.internalPurchaseList = internalPurchaseList;
    }

    public void setPurchases(List<Purchase> listPurchases) {
        requireAllNonNull(listPurchases);

        internalPurchaseList.setAll(listPurchases);
    }

    //=========== Getter Methods ==================================================================================

    public ObservableList<Purchase> getInternalPurchaseList() {
        return internalPurchaseList;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Purchase> asUnmodifiableObservableList() {
        return internalUnmodifiablePurchaseList;
    }

    /**
     * Returns the {@code Purchase} based on its {@code Index}.
     *
     * @param purchaseIndex of the purchase to be retrieved as seen on the list of purchases
     * @return Purchase
     * @throws PurchaseNotFoundException if the index is greater than the number of purchases
     */
    public Purchase getPurchase(int purchaseIndex) {
        Index index = Index.fromOneBased(purchaseIndex);
        if (index.getZeroBased() < 0 || index.getZeroBased() >= getNumPurchases()) {
            throw new PurchaseNotFoundException();
        }
        return internalPurchaseList.get(index.getZeroBased());
    }

    /**
     * Checks whether the purchase matches any purchase that already exists in the list.
     */
    public boolean contains(Purchase toCheck) {
        requireNonNull(toCheck);
        return internalPurchaseList.stream().anyMatch(toCheck::isSamePurchase);
    }

    /**
     * Checks for the existence of the purchase that has already been added to avoid duplicates in the list.
     *
     * @param purchase that is to be newly added
     * @return boolean checking the existence of the same purchase
     */
    public boolean hasPurchase(Purchase purchase) {
        return this.contains(purchase);
    }

    public int getNumPurchases() {
        return internalPurchaseList.size();
    }

    //=========== Command Methods ==================================================================================

    /**
     * Adds a single-use purchases to the list of purchases.
     *
     * @param newPurchase object from newly added single-use payment
     */
    public void addSinglePurchase(Purchase newPurchase) {
        requireNonNull(newPurchase);
        internalPurchaseList.add(newPurchase);
    }

    /**
     * Adds a purchase to the list at the given index.
     *
     * @param newPurchase object from newly added single-use payment
     * @param zeroBasedIndex index where the purchase should be added
     */
    public void addSinglePurchase(int zeroBasedIndex, Purchase newPurchase) {
        requireNonNull(newPurchase);
        internalPurchaseList.add(zeroBasedIndex, newPurchase);
    }

    /**
     * Removes purchase from the list of purchases based on the purchase number.
     *
     * @param purchaseIndex
     * @return Purchase that was just deleted from the user's list of purchases
     */
    public Purchase deletePurchase(int purchaseIndex) {
        try {
            Index index = Index.fromOneBased(purchaseIndex);
            return internalPurchaseList.remove(index.getZeroBased());
        } catch (IndexOutOfBoundsException e) {
            throw new PurchaseNotFoundException();
        }
    }

    /**
     * Removes purchase from the list of purchases.
     */
    public Purchase deletePurchase(Purchase purchase) {
        internalPurchaseList.remove(purchase);
        return purchase;
    }

    /**
     * Replaces the purchase {@code target} in the list with {@code editedPurchase}.
     *
     * {@code target} must exist in the list.
     * The identity of {@code editedPurchase} must not be the same as another existing purchase in the
     * list.
     */
    public void setPurchase(Purchase target, Purchase editedPurchase) {
        requireAllNonNull(target, editedPurchase);

        int index = internalPurchaseList.indexOf(target);
        if (index == -1) {
            throw new PurchaseNotFoundException();
        }

        internalPurchaseList.set(index, editedPurchase);
    }

    /**
     * Calculates the total spending based on the list of purchases.
     *
     * @return double value containing total expenditure
     */
    public double totalSpending() {
        double total = 0;
        for (Purchase purchase : internalPurchaseList) {
            total += purchase.getMoneySpent().getPurchaseAmount();
        }
        return total;
    }

    //=========== Common Methods ==================================================================================

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PurchaseList // instanceof handles nulls
                && internalPurchaseList.equals(((PurchaseList) other).internalPurchaseList));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are your purchases for this month: ");
        for (Purchase purchase : internalPurchaseList) {
            sb.append(purchase);
            sb.append("\n");
        }
        return sb.toString();
    }
}
