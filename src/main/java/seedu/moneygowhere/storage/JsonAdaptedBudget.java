package seedu.moneygowhere.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.moneygowhere.commons.exceptions.IllegalValueException;
import seedu.moneygowhere.logic.parser.exceptions.ParseException;
import seedu.moneygowhere.model.budget.Budget;

public class JsonAdaptedBudget {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Budget's %s field is missing!";
    public static final String INVALID_FIELD_MESSAGE_FORMAT = "Budget's %s field is invalid!";

    private final String value;
    private final String sum;

    /**
     * Constructs a {@code JsonAdaptedReminder} with the given Reminder details.
     */
    @JsonCreator
    public JsonAdaptedBudget(@JsonProperty("value") String value,
            @JsonProperty("sum") String sum) {
        this.value = value;
        this.sum = sum;
    }

    /**
     * Converts a given {@code Reminder} into this class for Jackson use.
     */
    public JsonAdaptedBudget(Budget source) {
        value = source.getValueString();
        sum = source.getSumString();
    }

    /**
     * Converts this Jackson-friendly adapted Reminder object into the model's {@code Reminder} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Reminder.
     */
    public Budget toModelType() throws IllegalValueException {

        double realValue;
        double realSum;

        if (value == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "value"));
        }

        try {
            realValue = Double.parseDouble(value);
        } catch (Exception e) {
            System.out.println("value: " + value);
            throw new IllegalValueException(String.format(INVALID_FIELD_MESSAGE_FORMAT, "value"));
        }

        if (sum == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "sum"));
        }

        try {
            realSum = Double.parseDouble(sum);
        } catch (Exception e) {
            System.out.println("sum: " + sum);
            throw new IllegalValueException(String.format(INVALID_FIELD_MESSAGE_FORMAT, "value"));
        }

        return new Budget(realValue, realSum);
    }
}
