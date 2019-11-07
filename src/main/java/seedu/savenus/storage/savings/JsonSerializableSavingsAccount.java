package seedu.savenus.storage.savings;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.model.savings.CurrentSavings;
import seedu.savenus.model.savings.ReadOnlySavingsAccount;
import seedu.savenus.model.savings.SavingsAccount;
import seedu.savenus.model.util.Money;

/**
 * Immutable Savings Account that is serializable to JSON format.
 */
@JsonRootName(value = "savings-account")
public class JsonSerializableSavingsAccount {

    public static final String MESSAGE_MISSING_FIELD = "Missing current savings field";

    private final String currentSaving;

    /**
     * Constructs a {@code JsonSerializableSavingsAccount} with a current savings input.
     */
    @JsonCreator
    public JsonSerializableSavingsAccount(@JsonProperty("current savings") String currSaving) {
        this.currentSaving = currSaving;
    }

    /**
     * Converts a given {@code ReadOnlySavingsAccount} into this class for Jackson use.
     */
    public JsonSerializableSavingsAccount(ReadOnlySavingsAccount source) {
        this.currentSaving = source.getCurrentSavings().get().toString();
    }

    /**
     * Converts this savings account into the model's {@code SavingsAccount} object.
     *
     * @throws IllegalValueException if there are any data violations.
     */
    public SavingsAccount toModelType() throws IllegalValueException {

        SavingsAccount savingsAccount = new SavingsAccount();

        if (this.currentSaving == null) {
            throw new IllegalValueException((String.format(MESSAGE_MISSING_FIELD,
                    CurrentSavings.class.getSimpleName())));
        }

        if (CurrentSavings.isValidCurrentSaving(this.currentSaving)) {
            throw new IllegalValueException((String.format(CurrentSavings.MESSAGE_CONSTRAINTS)));
        }
        if (new Money(this.currentSaving).isOutOfBounds()) {
            throw new IllegalValueException(String.format(CurrentSavings.FLOATING_POINT_CONSTRAINTS));
        }

        return new SavingsAccount(new CurrentSavings(new Money(this.currentSaving)));
    }
}
