package seedu.jarvis.model.financetracker;

import java.util.ArrayList;
/**
 * Manages list of monthly expenditures made by the user.
 */
public class PurchaseList {
    private ArrayList<Purchase> allPurchases;

    public PurchaseList(ArrayList<Purchase> allPurchases) {
        if (allPurchases.size() == 0) {
            //todo throw new error for no existing purchase list
        } else {
            this.allPurchases = allPurchases;
        }
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
        /*Purchase newPurchase = new Purchase(description, value);
        newPurchase.setPersonPaid(personPaid);*/
        allPurchases.add(newPurchase);
    }

    /**
     * Removes a particular purchase from the list and returns purchase. todo return object to show user
     * @param purchaseIndex
     * @return
     */
    public Purchase deletePurchase(int purchaseIndex) {
        if (purchaseIndex > allPurchases.size()) {
            //todo throw error
        }
        return allPurchases.remove(purchaseIndex - 1);
    }
}
