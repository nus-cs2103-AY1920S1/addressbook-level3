package seedu.jarvis.testutil;

import seedu.jarvis.model.financetracker.Purchase;

/**
 * A utility class to help with building Purchase objects.
 */
public class PurchaseBuilder {

    public static final String DEFAULT_DESCRIPTION = "Lunch at Reedz";
    public static final String DEFAULT_MONEY = " 5.50";

    private String description;
    private String moneySpent;

    /**
     * Initialises the PurchaseBuilder with the data of {@code purchaseToCopy}.
     */
    public PurchaseBuilder() {
        description = DEFAULT_DESCRIPTION;
        moneySpent = DEFAULT_MONEY;
    }

    /**
     * Sets the {@code Description} of the {@code Purchase} that we are building.
     */
    public PurchaseBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the {@code moneySpent} of the {@code Purchase} that we are building.
     */
    public PurchaseBuilder withMoneySpent(String moneySpent) {
        this.moneySpent = moneySpent;
        return this;
    }

    public Purchase build() {
        return new Purchase(description, Double.parseDouble(moneySpent));
    }

}
