package seedu.address.storage;

import java.io.IOException;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.model.exchangedata.Rates;

/**
 * Jackson-friendly version of {@link Rates}.
 */
public class JsonAdaptedRates {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "%s field of an Rates is missing!";

    private final Map<String, Double> rates;

    /**
     * Constructs a {@code JsonAdaptedRates} with the given Rates details.
     */
    @JsonCreator
    public JsonAdaptedRates(Map<String, Double> rates) {
        this.rates = rates;
    }

    /**
     * Converts this Jackson-friendly adapted Rates object into the model's {@code Rates} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Rates.
     */
    public Rates toModelType() throws IllegalValueException, IOException {
        Rates modelRates = new Rates();
        modelRates.getRates().putAll(rates);
        return modelRates;
    }
}
