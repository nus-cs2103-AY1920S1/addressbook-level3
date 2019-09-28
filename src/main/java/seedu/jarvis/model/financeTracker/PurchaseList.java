package seedu.jarvis.model.financeTracker;

import java.util.ArrayList;

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
     * @param description of the purchase
     * @param value spent on the purchase
     */
    public void addSinglePurchase(String description, double value) {
        Purchase newPurchase = new Purchase(description, value);
        allPurchases.add(newPurchase);
    }

    /**
     * Adds a single-time payment to a person to the list of purchases
     * @param description of the purchase
     * @param value spent on the purchase
     * @param personPaid person paid to
     */
    public void addSinglePayment(String description, double value, PersonPaid personPaid) {
        Purchase newPurchase = new Purchase(description, value);
        newPurchase.setPersonPaid(personPaid);
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
