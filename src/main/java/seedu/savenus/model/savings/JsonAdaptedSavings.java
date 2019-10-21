package seedu.savenus.model.savings;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.savenus.commons.exceptions.IllegalValueException;

/**
 * Jackson-friendly version of {@link Savings}.
 */
class JsonAdaptedSavings {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Saving's %s field is missing!";

    private final String savings;

    /**
     * Constructs a {@code JsonAdaptedSavings} with the giving saving details.
     */
    @JsonCreator
    public JsonAdaptedSavings(@JsonProperty("savings") String savings) {
        this.savings = savings;
    }

    /**
     * Converts a given {@code Savings} into the class for Jackson use.
     *
     * @param savings Savings to be converted and saved/removed from Jackson File.
     */
    public JsonAdaptedSavings(Savings savings) {
        this.savings = savings.toString();
    }

    /**
     * Converts this Jackson-friendly adapted food object into the model's {@code Savings}
     *
     *
     */
    public Savings toModelType() throws IllegalValueException {
        if (savings == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Savings.class.getSimpleName()));
        }
        if (!Savings.isValidSaving(savings)) {
            throw new IllegalValueException(Savings.MESSAGE_CONSTRAINTS);
        }
        return new Savings(savings);
    }
}
