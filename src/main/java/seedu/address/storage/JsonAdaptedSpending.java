package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.finance.Spending;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.finance.Spending.DATE_FORMAT;

/**
 * Jackson-friendly version of {@link Spending}.
 */
public class JsonAdaptedSpending {

    private final String spending;
    private final String date;
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedSpending} with the given {@code spending, @code date, @code description}.
     */
    @JsonCreator
    public JsonAdaptedSpending(@JsonProperty("spending") String spending, @JsonProperty("date") String date, @JsonProperty("description") String description) {
        requireAllNonNull(spending, date, description);
        this.spending = spending;
        this.date = date;
        this.description = description;
    }

    public String getSpending() {
        return spending;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }


    /**
     * Converts a given {@code Spending} into this class for Jackson use.
     */
    public JsonAdaptedSpending(Spending source) {
        spending = source.getSpending().toString();
        date = DATE_FORMAT.format(source.getDate());
        description = source.getDescription();
    }

    /**
     * Converts this Jackson-friendly adapted spending object into the model's {@code Spending} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Spending.
     */
    public Spending toModelType() throws IllegalValueException {
        Date result = new Date();
        if (!Spending.isValidAmount(spending)) {
            throw new IllegalValueException(Spending.MESSAGE_CONSTRAINTS);
        }
        if (!Spending.isValidDate(date)) {
            throw new IllegalValueException(Spending.MESSAGE_CONSTRAINTS);
        }
        try {
            result = DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Spending(new BigDecimal(spending), result, description);
    }

}
