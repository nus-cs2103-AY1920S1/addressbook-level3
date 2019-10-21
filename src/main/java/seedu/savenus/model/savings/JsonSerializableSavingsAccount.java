package seedu.savenus.model.savings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.savenus.commons.exceptions.IllegalValueException;

/**
 * An Immutable Savings Account that is serializable to JSON format.
 */
@JsonRootName(value = "savings")
class JsonSerializableSavingsAccount {

    // Save all the savings that have been made so far by the user.
    private final List<JsonAdaptedSavings> savingsHistory = new ArrayList<>();

    /**
     * Construct a {@code JsonSerializableSavingsAccount} with the given savingsHistory.
     */
    @JsonCreator
    public JsonSerializableSavingsAccount(@JsonProperty("savingsHistory") List<JsonAdaptedSavings> savingsHistory) {
        this.savingsHistory.addAll(savingsHistory);
    }

    /**
     * Converts a given {@code ReadOnlySavingsAccount} into this class for Jackson use.
     *
     * @param savingsAccount future changes will not affect the created {@code JsonSerializableSavingsAccount}.
     */
    public JsonSerializableSavingsAccount(ReadOnlySavingsAccount savingsAccount) {
        savingsHistory.addAll(savingsAccount.getSavingsHistory().stream()
                .map(JsonAdaptedSavings::new).collect(Collectors.toList()));
    }

    /**
     * Converts this savingsAccount into the model's {@code SavingsAccount} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public SavingsAccount toModelType() throws IllegalValueException {
        SavingsAccount savingsAccount = new SavingsAccount();
        for (JsonAdaptedSavings jsonAdaptedSavings : savingsHistory) {
            Savings savings = jsonAdaptedSavings.toModelType();
            savingsAccount.addSavings(savings);
        }
        return savingsAccount;
    }
}
