package seedu.savenus.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.savenus.model.savings.Savings;
import seedu.savenus.commons.exceptions.IllegalValueException;

/**
 * Jackson-friendly version of {@link Savings}.
 */
class JsonAdaptedSavings {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Savings' %s field is missing.";

    /**
     * TODO @FATCLARENCE save time as well.
     */
    //private final String timeOfSaving;
    private final String savingsAmount;

    /**
     * Constructs a {@code JsonAdaptedSaving} with details of savings.
     */
    @JsonCreator
    public JsonAdaptedSavings(@JsonProperty("savingsAmount") String savingsAmount) {
        this.savingsAmount = savingsAmount;
    }

    /**
     *  Converts a given {@code Saving} into this class for Jackson use.
     * @param savingsAmount
     */
    public JsonAdaptedSavings(Savings savingsAmount) {
        this.savingsAmount = savingsAmount.toString();
    }

    /**
     * Converts this Jackson-friendly adapted savings object into the model's {@code Savings} object.
     *
     * @throws IllegalValueException if there are any data constraints violated in the adapted savings.
     */
    public Savings toModelType() throws IllegalValueException {
        if (savingsAmount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Savings.class.getSimpleName()));
        }
        if (!Savings.isValidSaving(savingsAmount)) {
            throw new IllegalValueException(Savings.MESSAGE_CONSTRAINTS);
        }
        final Savings savings = new Savings(savingsAmount);
        return savings;
    }
}
