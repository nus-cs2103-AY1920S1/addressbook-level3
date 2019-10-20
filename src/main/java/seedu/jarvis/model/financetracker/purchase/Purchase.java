package seedu.jarvis.model.financetracker.purchase;

import static java.util.Objects.requireNonNull;

/**
 * Purchase object stores a single payment including its details such as the description and the money spent.
 */
public class Purchase {
    private PurchaseDescription description;
    private PurchaseMoneySpent moneySpent;

    public Purchase(PurchaseDescription description, PurchaseMoneySpent moneySpent) {
        requireNonNull(description);
        this.description = description;
        this.moneySpent = moneySpent;
    }

    //=========== Reset Methods ==================================================================================

    public Purchase(Purchase purchase) {
        resetData(purchase);
    }

    /**
     * Resets all data from {@code description} and {@code moneySpent} from the given {@code purchase}.
     *
     * @param purchase
     */
    public void resetData(Purchase purchase) {
        requireNonNull(purchase);
        this.description = purchase.getDescription();
        this.moneySpent = purchase.getMoneySpent();
    }

    //=========== Getter Methods ==================================================================================

    public PurchaseDescription getDescription() {
        return description;
    }

    public PurchaseMoneySpent getMoneySpent() {
        return moneySpent;
    }

    //=========== Common Methods ==================================================================================

    @Override
    public String toString() {
        return description + " (" + moneySpent + ")";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Purchase // instanceof handles nulls
                && description.equals(((Purchase) other).description)
                && moneySpent.equals(((Purchase) other).moneySpent));
    }

    public boolean isSamePurchase(Purchase purchase) {
        return this.equals(purchase);
    }
}
