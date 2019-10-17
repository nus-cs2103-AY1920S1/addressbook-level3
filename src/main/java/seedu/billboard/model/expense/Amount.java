package seedu.billboard.model.expense;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Represents an Expense's amount in Billboard.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(String)}}
 */
public class Amount {
    public static final String MESSAGE_CONSTRAINTS =
            "Amount should only contain a float number and it should not be blank";

    //TODO: Add parsing money logic
    public final BigDecimal amount;

    public Amount(String amount) {
        this.amount = new BigDecimal(amount);
    }

    /**
     * Returns true if a given string is a valid amount.
     */
    public static boolean isValidAmount(String test) {
        try {
            new BigDecimal(test);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Adds this amount to another amount.
     * @param other Amount to be added.
     * @return new Amount representing the combine total of the two amounts.
     */
    public Amount add(Amount other) {
        BigDecimal result = amount.add(other.amount);
        return new Amount(result.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Amount // instanceof handles nulls
                && amount.compareTo(((Amount) other).amount) == 0); // state check
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return decimalFormat.format(amount);
    }
}
