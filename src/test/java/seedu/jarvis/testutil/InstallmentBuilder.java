package seedu.jarvis.testutil;

import seedu.jarvis.model.financetracker.Installment;

/**
 * A utility class to help with building Installment objects.
 */
public class InstallmentBuilder {
    public static final String DEFAULT_DESCRIPTION = "Netflix";
    public static final String DEFAULT_MONEY = " 13.50";

    private String description;
    private String subscriptionPrice;

    /**
     * Initialises the PurchaseBuilder with the data of {@code installmentToCopy}.
     */
    public InstallmentBuilder() {
        description = DEFAULT_DESCRIPTION;
        subscriptionPrice = DEFAULT_MONEY;
    }

    /**
     * Sets the {@code Description} of the {@code Purchase} that we are building.
     */
    public InstallmentBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the {@code moneySpent} of the {@code Purchase} that we are building.
     */
    public InstallmentBuilder withMoneySpent(String subscriptionPrice) {
        this.subscriptionPrice = subscriptionPrice;
        return this;
    }

    public Installment build() {
        return new Installment(description, Double.parseDouble(subscriptionPrice));
    }

}
