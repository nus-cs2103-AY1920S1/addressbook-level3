package seedu.jarvis.testutil.finance;

import seedu.jarvis.model.finance.installment.Installment;
import seedu.jarvis.model.finance.installment.InstallmentDescription;
import seedu.jarvis.model.finance.installment.InstallmentMoneyPaid;

/**
 * A utility class to help with building Installment objects.
 */
public class InstallmentBuilder {

    public static final InstallmentDescription DEFAULT_DESCRIPTION = new InstallmentDescription("Netflix");
    public static final InstallmentMoneyPaid DEFAULT_MONEY = new InstallmentMoneyPaid("13.50");

    private InstallmentDescription description;
    private InstallmentMoneyPaid subscriptionPrice;

    public InstallmentBuilder() {
        description = DEFAULT_DESCRIPTION;
        subscriptionPrice = DEFAULT_MONEY;
    }

    /**
     * Initialises the PurchaseBuilder with the data of {@code installmentToCopy}.
     */
    public InstallmentBuilder(Installment toCopy) {
        description = toCopy.getDescription();
        subscriptionPrice = toCopy.getMoneySpentOnInstallment();
    }

    /**
     * Sets the {@code Description} of the {@code Purchase} that we are building.
     */
    public InstallmentBuilder withDescription(String description) {
        this.description = new InstallmentDescription(description);
        return this;
    }

    /**
     * Sets the {@code moneySpent} of the {@code Purchase} that we are building.
     */
    public InstallmentBuilder withMoneySpent(String subscriptionPrice) {
        this.subscriptionPrice = new InstallmentMoneyPaid(subscriptionPrice);
        return this;
    }

    public Installment build() {
        return new Installment(description, subscriptionPrice);
    }

}
