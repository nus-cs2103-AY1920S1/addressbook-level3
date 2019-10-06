package seedu.jarvis.model.financetracker;

/**
 * Object stores a single payment including its details such as the description and the money spent.
 */
public class Purchase {
    private String description;
    private double moneySpent;

    public Purchase(String description, double moneySpent) {
        this.description = description;
        this.moneySpent = moneySpent;
    }

    /**
     * GETTER METHODS
     */
    public String getDescription() {
        return this.description;
    }

    public double getMoneySpent() {
        return this.moneySpent;
    }

    @Override
    public String toString() {
        return this.description + " (" + this.moneySpent + ")";
    }
}
