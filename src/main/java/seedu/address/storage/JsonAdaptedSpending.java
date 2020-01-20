package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.finance.Money;
import seedu.address.model.finance.Spending;
import seedu.address.model.project.Time;

import java.math.BigDecimal;
import java.text.ParseException;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Jackson-friendly version of {@link Spending}.
 */
public class JsonAdaptedSpending {

    private final String spending;
    private final String time;
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedSpending} with the given {@code spending, @code date, @code description}.
     */
    @JsonCreator
    public JsonAdaptedSpending(@JsonProperty("spending") String spending, @JsonProperty("time") String time, @JsonProperty("description") String description) {
        requireAllNonNull(spending, time, description);
        this.spending = spending;
        this.time = time;
        this.description = description;
    }

    public String getSpending() {
        return spending;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }


    /**
     * Converts a given {@code Spending} into this class for Jackson use.
     */
    public JsonAdaptedSpending(Spending source) {
        spending = source.getMoney().toString();
        time = source.getTime().toString();
        description = source.getDescription();
    }

    /**
     * Converts this Jackson-friendly adapted spending object into the model's {@code Spending} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Spending.
     */
    public Spending toModelType() throws IllegalValueException, ParseException {
        if (!Money.isValidAmount(spending)) {
            throw new IllegalValueException(Spending.MESSAGE_CONSTRAINTS);
        }

        String t = time.split(" ")[1];
        String d = time.split(" ")[0];

        if (time.trim().split(" ").length < 2 || time == null || time.equals("")) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
        if (!Time.isValidTimeAndDate(time.trim())) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
        if (!Time.isValidDate(d)) {
            throw new IllegalValueException(Time.DATE_CONSTRAINTS);
        }
        if (!Time.isValidTime(t)) {
            throw new IllegalValueException(Time.TIME_CONSTRAINTS);
        }

        Time result = new Time(this.time);
        Money amount = new Money(new BigDecimal(spending));
        return new Spending(amount, result, description);
    }

}
