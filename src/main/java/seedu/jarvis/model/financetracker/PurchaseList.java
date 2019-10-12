package seedu.jarvis.model.financetracker;

import java.util.ArrayList;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.model.financetracker.exceptions.PurchaseNotFoundException;

/**
 * Manages list of monthly expenditures made by the user.
 */
public class PurchaseList {
    private ArrayList<Purchase> allPurchases;

    public PurchaseList(ArrayList<Purchase> allPurchases) {
        this.allPurchases = allPurchases;
    }

    /**
     * Adds a single-use purchases to the list of purchases
     *
     * @param newPurchase object from newly added single-use payment
     */
    public void addSinglePurchase(Purchase newPurchase) {
        allPurchases.add(newPurchase);
    }

    /**
     * Removes a particular purchase from the list and returns purchase.
     *
     * @param purchaseIndex
     * @return Purchase that was just deleted from the user's list of purchases
     */
    public Purchase deletePurchase(int purchaseIndex) {
        if (purchaseIndex < 1) {
            throw new PurchaseNotFoundException();
        } else {
            Index index = Index.fromOneBased(purchaseIndex);
            return allPurchases.remove(index.getZeroBased());
        }
    }

    /**
     * Calculates the total spending based on the list of purchases.
     *
     * @return double value containing total expenditure
     */
    public double totalSpending() {
        double total = 0;
        for (Purchase purchase : allPurchases) {
            total += purchase.getMoneySpent();
        }
        return total;
    }

    public Purchase getPurchase(int purchaseIndex) {
        Index index = Index.fromOneBased(purchaseIndex);
        return allPurchases.get(index.getZeroBased());
    }

    public int getNumPurchases() {
        return allPurchases.size();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PurchaseList // instanceof handles nulls
                && allPurchases.equals(((PurchaseList) other).allPurchases));
    }
}
