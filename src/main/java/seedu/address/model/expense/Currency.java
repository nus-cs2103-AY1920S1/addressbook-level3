package seedu.address.model.expense;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the currency of an expense in the MYMorise.
 * Guarantees: immutable; is valid as declared in {@link #isValidCurrency(String)}
 */
public class Currency {

    public static final String MESSAGE_CONSTRAINTS = "Currency should exactly 3 Letters "
        + "and it should not be blank";
    public static final String VALIDATION_REGEX = "([a-zA-Z]){3}";

    public final String value;

    /**
     * Constructs an {@code Amount}.
     *
     * @param currency A valid amount.
     */
    public Currency(String currency) {
        requireNonNull(currency);
        checkArgument(isValidCurrency(currency), MESSAGE_CONSTRAINTS);
        value = currency.toUpperCase();
    }

    /**
     * Returns if a given string is a valid currency.
     */
    public static boolean isValidCurrency(String test) {
        //TODO: Extend and verify against ISO-4217 Currency Representation List
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Currency // instanceof handles nulls
            && value.equals(((Currency) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
