package seedu.jarvis.testutil.finance;

import java.time.LocalDate;

import seedu.jarvis.model.financetracker.purchase.Purchase;
import seedu.jarvis.model.financetracker.purchase.PurchaseDescription;
import seedu.jarvis.model.financetracker.purchase.PurchaseMoneySpent;

/**
 * A utility class to help with building Purchase objects.
 */
public class PurchaseBuilder {

    public static final PurchaseDescription DEFAULT_DESCRIPTION = new PurchaseDescription("Lunch at Reedz");
    public static final PurchaseMoneySpent DEFAULT_MONEY = new PurchaseMoneySpent("5.5");
    public static final LocalDate DEFAULT_DATE = LocalDate.now();

    private PurchaseDescription description;
    private PurchaseMoneySpent moneySpent;
    private LocalDate dateOfPurchase;

    /**
     * Initialises the PurchaseBuilder with the data of {@code purchaseToCopy}.
     */
    public PurchaseBuilder() {
        description = DEFAULT_DESCRIPTION;
        moneySpent = DEFAULT_MONEY;
        dateOfPurchase = DEFAULT_DATE;
    }

    public PurchaseBuilder(Purchase toCopy) {
        description = toCopy.getDescription();
        moneySpent = toCopy.getMoneySpent();
        dateOfPurchase = toCopy.getDateOfPurchase();
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

    /**
     * Sets the {@code dateOfPurchase} of the {@code Purchase} that we are building.
     */
    public PurchaseBuilder withDateOfPurchase(String date) {
        this.dateOfPurchase = LocalDate.parse(date, Purchase.getDateFormat());
        return this;
    }

    public Purchase build() {
        return new Purchase(description, moneySpent, dateOfPurchase);
    }

}
