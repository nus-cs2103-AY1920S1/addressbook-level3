package seedu.jarvis.model.financetracker;

import static java.util.Objects.requireNonNull;

/**
 * Purchase object stores a single payment including its details such as the description and the money spent.
 */
public class Purchase {
    private String description;
    private double moneySpent;

    public Purchase(String description, double moneySpent) {
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

    public String getDescription() {
        return description;
    }

    public double getMoneySpent() {
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
                || (other instanceof Installment // instanceof handles nulls
                && description.equals(((Purchase) other).description)
                && moneySpent == ((Purchase) other).moneySpent);
    }
}
