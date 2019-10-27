package seedu.jarvis.testutil.finance;

import java.util.Arrays;
import java.util.List;

import seedu.jarvis.model.finance.installment.Installment;

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

    public static List<Installment> getTypicalInstallments() {
        return Arrays.asList(PHONE_BILL, TRANSPORT_CONCESSION);
    }
}
