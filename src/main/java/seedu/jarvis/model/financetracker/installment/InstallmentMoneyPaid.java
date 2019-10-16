package seedu.jarvis.model.financetracker.installment;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.AppUtil.checkArgument;

/**
 * Represents the description of an installment in the finance tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(String)}
 */
public class InstallmentMoneyPaid {

    public static final String MESSAGE_CONSTRAINTS =
            "Subscription fee of installments should Names should only contain alphanumeric characters and spaces, "
                    + "and it should not be blank";

    public static final String VALIDATION_REGEX = "[0-9]{1,13}(\\.[0-9]*)?";

    public final double installmentMoneyPaid;

    /**
     * Constructs a {@code InstallmentMoneyPaid}.
     *
     * @param value A valid installment money paid.
     */
    public InstallmentMoneyPaid(String value) {
        requireNonNull(value);
        checkArgument(isValidAmount(value), MESSAGE_CONSTRAINTS);
        installmentMoneyPaid = Double.parseDouble(value);
    }

    /**
     * Returns true if the given double is a valid amount.
     */
    public static boolean isValidAmount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public double getInstallmentMoneyPaid() {
        return installmentMoneyPaid;
    }

    @Override
    public String toString() {
        return Double.toString(installmentMoneyPaid);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof InstallmentMoneyPaid
                && Double.toString(installmentMoneyPaid)
                .equals(Double.toString(((InstallmentMoneyPaid) other).installmentMoneyPaid)));
    }

}
