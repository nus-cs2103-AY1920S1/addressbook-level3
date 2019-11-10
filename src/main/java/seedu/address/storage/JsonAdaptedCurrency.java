package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.expense.Amount;
import seedu.address.model.expense.Currency;

/**
 * Jackson-friendly version of {@link Currency}.
 */
public class JsonAdaptedCurrency {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "%s field of an expense is missing!";

    private final String name;
    private final double rate;

    /**
     * Constructs a {@code JsonAdaptedCurrency} with the given expense details.
     */
    @JsonCreator
    public JsonAdaptedCurrency(@JsonProperty("name") String name, @JsonProperty("rate") double rate) {
        this.name = name;
        this.rate = rate;
    }

    /**
     * Converts a given {@code Currency} into this class for Jackson use.
     */
    public JsonAdaptedCurrency(Currency source) {
        name = source.name;
        rate = source.getRate();
    }

    /**
     * Converts this Jackson-friendly adapted expense object into the model's {@code Currency} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted currency.
     */
    public Currency toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, String.class.getSimpleName()));
        }
        if (!Currency.isValidCurrency(name)) {
            throw new IllegalValueException(Currency.MESSAGE_CONSTRAINTS);
        }
        final String modelName = name;

        if (rate <= 0.0) {
            throw new IllegalValueException(Amount.MESSAGE_CONSTRAINTS);
        }
        final double modelRate = rate;


        return new Currency(modelName, modelRate);
    }

}
