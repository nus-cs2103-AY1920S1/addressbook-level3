package seedu.address.model.currency;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.itinerary.Name;

/**
 * Generic abstraction of customisedCurrency.
 */
public class CustomisedCurrency {

    // Compulsory fields
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
                    && otherCurrency.getRate().equals(getRate());
        }
    }

    @Override
    public String toString() {
        return name.toString() + "(" + symbol.toString() + "),  exchange rate -- 1 : " + rate.getValue();
    }
}
