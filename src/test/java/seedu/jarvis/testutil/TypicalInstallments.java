package seedu.jarvis.testutil;

import seedu.jarvis.model.financetracker.installment.Installment;
import seedu.jarvis.testutil.finance.InstallmentBuilder;

/**
 * A utility class containing a list of {@code Installment} objects to be used in tests.
 */
public class TypicalInstallments {

    public static final Installment PHONE_BILL = new InstallmentBuilder()
            .withDescription("Phone bill")
            .withMoneySpent("40.0").build();

    public static final Installment TRANSPORT_CONCESSION = new InstallmentBuilder()
            .withDescription("Transport concession")
            .withMoneySpent("25.0").build();
}
