package seedu.savenus.storage.savings;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.model.savings.ReadOnlySavingsAccount;
import seedu.savenus.model.savings.SavingsAccount;

/**
 * An Immutable Savings Account that is serializable to JSON format.
 */
@JsonRootName(value = "savings-account")
public class JsonSerializableSavingsAccount {

    private JsonAdaptedSavingsAccount savingsAccount;

    /**
     * Construct a {@code JsonSerializableSavingsAccount} with the given savingsHistory.
     */
    @JsonCreator
    public JsonSerializableSavingsAccount(@JsonProperty("savingsAccount") JsonAdaptedSavingsAccount savingsAccount) {
        this.savingsAccount = savingsAccount;
    }

    /**
     * Converts a given {@code SavingsAccount} into this class for Jackson use.
     *
     * @param savingsAccount future changes will not affect the created {@code JsonSerializableSavingsAccount}.
     */
    public JsonSerializableSavingsAccount(ReadOnlySavingsAccount savingsAccount) {
        this.savingsAccount = new JsonAdaptedSavingsAccount(savingsAccount);
    }

    /**
     * Converts this savingsAccount into the model's {@code SavingsAccount} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public SavingsAccount toModelType() throws IllegalValueException {
        return this.savingsAccount.toModelType();
    }
}
