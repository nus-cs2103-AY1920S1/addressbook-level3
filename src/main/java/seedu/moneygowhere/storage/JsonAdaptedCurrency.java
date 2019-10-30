package seedu.moneygowhere.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.moneygowhere.commons.exceptions.IllegalValueException;
import seedu.moneygowhere.model.currency.Currency;

/**
 * Jackson-friendly version of {@link Currency}.
 */
class JsonAdaptedCurrency implements Comparable<JsonAdaptedCurrency> {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Currency's %s field is missing!";

    private final String name;
    private final String symbol;
    private final double rate;

    /**
     * Constructs a {@code JsonAdaptedSpending} with the given Spending details.
     */
    @JsonCreator
    public JsonAdaptedCurrency(@JsonProperty("name") String name, @JsonProperty("symbol") String symbol,
            @JsonProperty("rate") double rate) {
        this.name = name;
        this.symbol = symbol;
        this.rate = rate;
    }

    /**
     * Converts a given {@code Currency} into this class for Jackson use.
     */
    public JsonAdaptedCurrency(Currency source) {
        name = source.name;
        symbol = source.symbol;
        rate = source.rate;
    }

    /**
     * Converts this Jackson-friendly adapted Spending object into the model's {@code Currency} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Spending.
     */
    public Currency toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Name"));
        }
        if (!Currency.isValidCurrencyName(name)) {
            throw new IllegalValueException(Currency.MESSAGE_CONSTRAINTS);
        }

        if (symbol == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Symbol"));
        }

        if (!Currency.isValidCurrencySymbol(symbol)) {
            throw new IllegalValueException(Currency.MESSAGE_CONSTRAINT_SYMBOL);
        }

        if (rate <= 0 || rate >= Double.MAX_VALUE) {
            throw new IllegalValueException(Currency.MESSAGE_CONSTRAINT_RATE);
        }

        return new Currency(name, symbol, rate);
    }

    @Override
    public int compareTo(JsonAdaptedCurrency o) {
        return name.compareTo(o.name);
    }
}
