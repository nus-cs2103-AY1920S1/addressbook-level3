package seedu.address.model.currency;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.itinerary.Name;

/**
 * Generic abstraction of customised currency.
 */
public class CustomisedCurrency {

    private final Name name;
    private final Symbol symbol;
    private final Rate rate;

    /**
     * Constructs an {@code CustomisedCurrency}.
     */
    public CustomisedCurrency(Name name, Symbol symbol, Rate rate) {
        requireAllNonNull(name, symbol, rate);
        this.name = name;
        this.symbol = symbol;
        this.rate = rate;
    }


    public Name getName() {
        return name;
    }
    public Symbol getSymbol() {
        return symbol;
    }
    public Rate getRate() {
        return rate;
    }

    /**
     * Check if two {@code CustomisedCurrency}s are the same
     *
     * @param otherCurrency The other {@code CustomisedCurrency} to check.
     * @return Boolean of whether the {@code CustomisedCurrency}s are the same.
     */
    public boolean isSameCustomisedCurrency(CustomisedCurrency otherCurrency) {
        if (otherCurrency == this) {
            return true;
        } else {
            return otherCurrency != null
                    && otherCurrency.getName().equals(getName());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CustomisedCurrency)) {
            return false;
        }

        CustomisedCurrency otherCustomisedCurrency = (CustomisedCurrency) other;
        return otherCustomisedCurrency.getName().equals(getName())
                && otherCustomisedCurrency.getSymbol().equals(getSymbol())
                && otherCustomisedCurrency.getRate().equals(getRate());
    }

    @Override
    public String toString() {
        return name.toString() + "(" + symbol.toString() + "),  exchange rate to Singapore Dollar (SGD) is "
                + rate.getValue() + " : 1";
    }
}
