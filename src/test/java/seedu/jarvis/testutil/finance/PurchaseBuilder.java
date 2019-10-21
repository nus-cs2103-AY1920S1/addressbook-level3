package seedu.jarvis.testutil.finance;

import seedu.jarvis.model.financetracker.purchase.Purchase;
import seedu.jarvis.model.financetracker.purchase.PurchaseDescription;
import seedu.jarvis.model.financetracker.purchase.PurchaseMoneySpent;

/**
 * A utility class to help with building Purchase objects.
 */
public class PurchaseBuilder {

    public static final PurchaseDescription DEFAULT_DESCRIPTION = new PurchaseDescription("Lunch at Reedz");
    public static final PurchaseMoneySpent DEFAULT_MONEY = new PurchaseMoneySpent("5.5");

    private PurchaseDescription description;
    private PurchaseMoneySpent moneySpent;

    /**
     * Initialises the PurchaseBuilder with the data of {@code purchaseToCopy}.
     */
    public PurchaseBuilder() {
        description = DEFAULT_DESCRIPTION;
        moneySpent = DEFAULT_MONEY;
    }

    public PurchaseBuilder(Purchase toCopy) {
        description = toCopy.getDescription();
        moneySpent = toCopy.getMoneySpent();
    }

    /**
     * Sets the {@code Description} of the {@code Purchase} that we are building.
     */
    public PurchaseBuilder withDescription(String description) {
        this.description = new PurchaseDescription(description);
        return this;
    }

    /**
     * Sets the {@code moneySpent} of the {@code Purchase} that we are building.
     */
    public PurchaseBuilder withMoneySpent(String moneySpent) {
        this.moneySpent = new PurchaseMoneySpent(moneySpent);
        return this;
    }

    public Purchase build() {
        return new Purchase(description, moneySpent);
    }

}
