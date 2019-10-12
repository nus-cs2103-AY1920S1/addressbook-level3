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

    //=========== Getter Methods ==================================================================================

    public String getDescription() {
        return description;
    }

    public double getMoneySpent() {
        return moneySpent;
    }

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
