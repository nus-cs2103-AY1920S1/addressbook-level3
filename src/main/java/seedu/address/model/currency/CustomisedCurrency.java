package seedu.address.model.currency;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Generic abstraction of customisedCurrency.
 */
public class CustomisedCurrency {

    // Compulsory fields
    private final String name;
    private final String symbol;
    private final double rate;

    /**
     * Constructs an {@code CustomisedCurrency}.
     */
    public CustomisedCurrency(String name, String symbol, double rate) {
        requireAllNonNull(name, symbol, rate);
        this.name = name;
        this.symbol = symbol;
        this.rate = rate;
    }


    public String getName() {
        return name;
    }
    public String getSymbol() {
        return symbol;
    }
    public double getRate() {
        return rate;
    }

    /**
     * Check if two currencies are the same
     *
     * @param otherCurrency The other currency to check.
     * @return Boolean of whether the currencies are the same.
     */
    public boolean isSameCustomisedCurrency(CustomisedCurrency otherCurrency) {
        if (otherCurrency == this) {
            return true;
        } else {
            return otherCurrency != null
                    && otherCurrency.getName().equals(getName())
                    && otherCurrency.getSymbol().equals(getSymbol())
                    && otherCurrency.getRate() == (getRate());
        }
    }
}
