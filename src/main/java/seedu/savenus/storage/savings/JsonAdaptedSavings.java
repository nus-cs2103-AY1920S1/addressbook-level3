package seedu.savenus.storage.savings;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.savenus.commons.exceptions.IllegalValueException;

import seedu.savenus.model.savings.Savings;
import seedu.savenus.model.util.Money;

//@@author fatclarence
/**
 * Jackson-friendly version of {@link Savings}.
 */
class JsonAdaptedSavings {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Saving's %s field is missing!";

    private final String savingsAmount;
    private final String timeStamp;

    /**
     * Constructs a {@code JsonAdaptedSavings} with the giving saving details.
     */
    @JsonCreator
    public JsonAdaptedSavings(@JsonProperty("savingsAmount") String savingsAmount,
                              @JsonProperty("timeStamp") String timeStamp) {
        this.savingsAmount = savingsAmount;
        this.timeStamp = timeStamp;
    }

    /**
     * Converts a given {@code Savings} into the class for Jackson use.
     *
     * @param savings Savings to be converted and saved/removed from Jackson File.
     */
    public JsonAdaptedSavings(Savings savings) {
        this.savingsAmount = savings.toString();
        this.timeStamp = savings.getTimeStampString();
    }

    /**
     * Converts this Jackson-friendly adapted food object into the model's {@code Savings}
     *
     */
    public Savings toModelType() throws IllegalValueException {
        if (this.savingsAmount == null || this.timeStamp == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Savings.class.getSimpleName()));
        } else {
            if (this.savingsAmount.contains("-")) {
                BigDecimal temp = new BigDecimal(this.savingsAmount).negate();
                String tempStr = temp.toString();
                try {
                    Money tempo = new Money(temp);
                    return new Savings(tempStr, this.timeStamp, true);
                } catch (IllegalArgumentException e) {
                    throw new IllegalValueException(Money.MESSAGE_CONSTRAINTS);
                }
            } else {
                return new Savings(this.savingsAmount, this.timeStamp, false);
            }
        }
    }
}
