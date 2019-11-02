package seedu.jarvis.model.finance.installment;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.AppUtil.checkArgument;

import java.text.DecimalFormat;

/**
 * Represents the description of an installment in the finance tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(String)}
 */
public class InstallmentMoneyPaid {

    public static final String MESSAGE_CONSTRAINTS =
            "Subscription fee of installments must have a maximum of 2 decimal places, "
                    + "and it should not be blank.";

    public static final String MONEY_CONSTRAINTS =
            "Money spent cannot be equal to or less than 0.";

    public static final String VALIDATION_REGEX = "[0-9]{1,3}[. ]*[0-9]{0,2}";

    private static DecimalFormat df2 = new DecimalFormat("#.00");

    private final double installmentMoneyPaid;

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
        return df2.format(installmentMoneyPaid);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof InstallmentMoneyPaid
                && Double.toString(installmentMoneyPaid)
                .equals(Double.toString(((InstallmentMoneyPaid) other).installmentMoneyPaid)));
    }

}
