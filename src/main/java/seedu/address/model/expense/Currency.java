package seedu.address.model.expense;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.exchangedata.ExchangeDataSingleton;

/**
 * Represents the currency of an expense in the MyMorise.
 * Guarantees: immutable; is valid as declared in {@link #isValidCurrency(String)}
 */
public class Currency {

    public static final String DEFAULT_BASE_CURRENCY = "SGD";

    public static final String MESSAGE_CONSTRAINTS = "Currency should exactly 3 Letters "
        + "and it should not be blank";
    public static final String VALIDATION_REGEX = "([a-zA-Z]){3}";

    public final String value;
    private Double rate;

    public Currency() {
        value = DEFAULT_BASE_CURRENCY;
        rate = 1.0;
    }

    /**
     * Constructs a {@code Currency}.
     *
     * @param currency A valid currency.
     */
    public Currency(String currency) {
        requireNonNull(currency);
        checkArgument(isValidCurrency(currency), MESSAGE_CONSTRAINTS);
        this.value = currency.toUpperCase();
        this.rate = 1.0;
    }

    /**
     * Constructs a {@code Currency}.
     *
     * @param currency A valid currency.
     * @param rate A valid rate.
     */
    public Currency(String currency, Double rate) {
        requireNonNull(currency);
        checkArgument(isValidCurrency(currency), MESSAGE_CONSTRAINTS);
        this.value = currency.toUpperCase();
        this.rate = rate;
    }

    /**
     * Returns if a given string is a valid currency.
     */
    public static boolean isValidCurrency(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public Double getRate() {
        rate = ExchangeDataSingleton.getInstance().getRates().getRate(value);
        return rate;
    }

    @Override
    public String toString() {
        return value + " @ " + String.format("%.3f",rate);
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
