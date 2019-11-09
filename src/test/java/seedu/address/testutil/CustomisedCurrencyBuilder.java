package seedu.address.testutil;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.currency.CustomisedCurrency;
import seedu.address.model.currency.Rate;
import seedu.address.model.currency.Symbol;
import seedu.address.model.itinerary.Name;

/**
 * Builder class to accommodate optional properties using builder pattern.
 * Can be used to construct {@link CustomisedCurrency} without optional fields.
 */
public class CustomisedCurrencyBuilder {
    private Name name;
    private Symbol symbol;
    private Rate rate;

    private CustomisedCurrencyBuilder() {}

    public static CustomisedCurrencyBuilder newInstance() {
        return new CustomisedCurrencyBuilder();
    }

    /**
     * Constructs a CustomisedCurrencyBuilder instance from the specified currency.
     *
     * @param currency CustomisedCurrency to use.
     * @return new CustomisedCurrencyBuilder instance.
     */
    public static CustomisedCurrencyBuilder of(CustomisedCurrency currency) {
        requireAllNonNull(currency.getName(), currency.getSymbol());
        return CustomisedCurrencyBuilder.newInstance()
                .setName(currency.getName())
                .setSymbol(currency.getSymbol())
                .setRate(currency.getRate());
    }

    public CustomisedCurrencyBuilder setName(Name name) {
        this.name = name;
        return this;
    }

    public CustomisedCurrencyBuilder setSymbol(Symbol symbol) {
        this.symbol = symbol;
        return this;
    }

    public CustomisedCurrencyBuilder setRate(Rate rate) {
        this.rate = rate;
        return this;
    }

    /**
     * Terminal method to construct new {@link CustomisedCurrency}.
     */
    public CustomisedCurrency build() {
        return new CustomisedCurrency(name, symbol, rate);
    }

}
