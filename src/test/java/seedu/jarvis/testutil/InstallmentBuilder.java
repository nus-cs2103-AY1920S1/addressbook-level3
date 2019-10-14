package seedu.jarvis.testutil;

import seedu.jarvis.model.financetracker.installment.Installment;
import seedu.jarvis.model.financetracker.installment.InstallmentDescription;
import seedu.jarvis.model.financetracker.installment.InstallmentMoneyPaid;

/**
 * A utility class to help with building Installment objects.
 */
public class InstallmentBuilder {
    public static final InstallmentDescription DEFAULT_DESCRIPTION = new InstallmentDescription("Netflix");
    public static final InstallmentMoneyPaid DEFAULT_MONEY = new InstallmentMoneyPaid("13.50");

    private InstallmentDescription description;
    private InstallmentMoneyPaid subscriptionPrice;

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
    public InstallmentBuilder withDescription(InstallmentDescription description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the {@code moneySpent} of the {@code Purchase} that we are building.
     */
    public InstallmentBuilder withMoneySpent(InstallmentMoneyPaid subscriptionPrice) {
        this.subscriptionPrice = subscriptionPrice;
        return this;
    }

    public Installment build() {
        return new Installment(description, subscriptionPrice);
    }

}
