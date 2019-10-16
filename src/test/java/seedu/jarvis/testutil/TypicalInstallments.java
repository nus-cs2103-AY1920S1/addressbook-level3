package seedu.jarvis.testutil;

import seedu.jarvis.model.financetracker.installment.Installment;
import seedu.jarvis.model.financetracker.installment.InstallmentDescription;
import seedu.jarvis.model.financetracker.installment.InstallmentMoneyPaid;

/**
 * A utility class containing a list of {@code Installment} objects to be used in tests.
 */
public class TypicalInstallments {

    public static final Installment PHONE_BILL = new InstallmentBuilder()
            .withDescription(new InstallmentDescription("Phone bill"))
            .withMoneySpent(new InstallmentMoneyPaid("40.0")).build();

    public static final Installment TRANSPORT_CONCESSION = new InstallmentBuilder()
            .withDescription(new InstallmentDescription("Transport concession"))
            .withMoneySpent(new InstallmentMoneyPaid("25.0")).build();
}
