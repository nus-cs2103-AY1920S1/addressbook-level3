package seedu.address.storage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.commons.Currency;
import seedu.address.model.commons.Date;
import seedu.address.model.exchangedata.ExchangeData;
import seedu.address.model.rates.Rates;


/**
 * Jackson-friendly version of {@link ExchangeData}.
 */
public class JsonAdaptedExchangeData {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "%s field of an exchangeRates is missing!";

    private final String lastUpdatedDate;
    private final String baseCurrency;
    private final JsonAdaptedRates rates;

    /**
     * Constructs a {@code JsonAdaptedexchangeRates} with the given exchangeRates details.
     */
    @JsonCreator
    public JsonAdaptedExchangeData(@JsonProperty("date") String lastUpdatedDate,
                                   @JsonProperty("base") String baseCurrency,
                                   @JsonProperty("rates") JsonAdaptedRates rates) {
        this.lastUpdatedDate = lastUpdatedDate;
        this.baseCurrency = baseCurrency;
        this.rates = rates;
    }

    /**
     * Converts this Jackson-friendly adapted ExchangeRates object into the model's {@code ExchangeRates} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted ExchangeRates.
     */
    public ExchangeData toModelType() throws IllegalValueException, IOException, ParseException {

        final Rates modelRates = rates.toModelType();

        if (lastUpdatedDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        SimpleDateFormat formaterFrom = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formaterTo = new SimpleDateFormat("dd/MM/yyyy");
        if (!Date.isValidDate(formaterTo.format(formaterFrom.parse(lastUpdatedDate)))) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(formaterTo.format(formaterFrom.parse(lastUpdatedDate)));

        if (baseCurrency == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Currency.class.getSimpleName()));
        }
        if (!Currency.isValidCurrency(baseCurrency)) {
            throw new IllegalValueException(Currency.MESSAGE_CONSTRAINTS);
        }
        final Currency modelCurrency = new Currency(baseCurrency);


        return new ExchangeData(modelDate, modelCurrency, modelRates);
    }
}
