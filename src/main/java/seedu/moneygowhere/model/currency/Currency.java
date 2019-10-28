package seedu.moneygowhere.model.currency;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Currency for MoneyGoWhere.
 */
public class Currency implements Comparable<Currency> {

    public static final String MESSAGE_CONSTRAINTS = "Currency is limited to 3 letters";
    public static final String VALIDATION_REGEX = "\\p{Upper}{3}+";

    public final String name;
    public final String symbol;
    public final double rate;

    /**
     * Constructs a {@code Currency}.
     *
     * @param name Three letter currency name
     */
    public Currency(String name, String symbol, double rate) {
        requireNonNull(name);
        checkArgument(isValidCurrencyName(name), MESSAGE_CONSTRAINTS);
        this.name = name;
        this.symbol = symbol;
        this.rate = rate;
    }

    /**
     * Returns true if a given string is a valid currency name.
     */
    public static boolean isValidCurrencyName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Currency // instanceof handles nulls
                && name.equals(((Currency) other).name)
                && symbol.equals(((Currency) other).symbol)
                && rate == ((Currency) other).rate); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, symbol, rate);
    }

    @Override
    public int compareTo(Currency o) {
        return name.compareTo(o.name);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return String.format("%s (%s): %.3f", name, symbol, rate);
    }
}
