package seedu.jarvis.model.financetracker;

import java.util.ArrayList;
/**
 * Manages list of monthly expenditures made by the user.
 */
public class PurchaseList {
    private ArrayList<Purchase> allPurchases;

    public PurchaseList(ArrayList<Purchase> allPurchases) {
            this.allPurchases = allPurchases;
//        }
    }

    /**
     * Adds a single-use purchases to the list of purchases
     * @param newPurchase object from newly added single-use payment
     */
    public void addSinglePurchase(Purchase newPurchase) {
        allPurchases.add(newPurchase);
    }

    /**
     * Adds a single-time payment to a person to the list of purchases
     * @param newPurchase of payment to a person
     */
    public void addSinglePayment(Purchase newPurchase) {
        allPurchases.add(newPurchase);
    }

    /**
     * Removes a particular purchase from the list and returns purchase. todo return object to show user
     * @param purchaseIndex
     * @return
     */
    public Purchase deletePurchase(int purchaseIndex) {
        //todo check if the number is within the size of the list and edit tests accordingly
        return allPurchases.remove(purchaseIndex - 1);
    }

    public double totalSpending() {
        double total = 0;
        for (Purchase purchase : allPurchases) {
            total += purchase.getMoneySpent();
        }
        return total;
    }

    public Purchase getPurchase(int purchaseIndex) {
        return this.allPurchases.get(purchaseIndex - 1);
    }

    public int getNumPurchases() {
        return this.allPurchases.size();
    }
}
