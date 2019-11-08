package seedu.savenus.storage.savings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.model.savings.ReadOnlySavingsHistory;
import seedu.savenus.model.savings.Savings;
import seedu.savenus.model.savings.SavingsHistory;

/**
 * An Immutable Savings Account that is serializable to JSON format.
 */
@JsonRootName(value = "savings")
public class JsonSerializableSavingsHistory {

    // Save all the savings that have been made so far by the user.
    private final List<JsonAdaptedSavings> savingsHistory = new ArrayList<>();

    /**
     * Construct a {@code JsonSerializableSavingsAccount} with the given savingsHistory.
     */
    @JsonCreator
    public JsonSerializableSavingsHistory(@JsonProperty("savingsHistory") List<JsonAdaptedSavings> savingsHistory) {
        this.savingsHistory.addAll(savingsHistory);
    }

    /**
     * Converts a given {@code ReadOnlySavingsAccount} into this class for Jackson use.
     *
     * @param savingsHistoryList future changes will not affect the created {@code JsonSerializableSavingsAccount}.
     */
    public JsonSerializableSavingsHistory(ReadOnlySavingsHistory savingsHistoryList) {
        savingsHistory.addAll(savingsHistoryList.getSavingsHistory().stream()
                .map(JsonAdaptedSavings::new).collect(Collectors.toList()));
    }

    /**
     * Converts this savingsAccount into the model's {@code SavingsAccount} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public SavingsHistory toModelType() throws IllegalValueException {
        SavingsHistory savingsHistory = new SavingsHistory();
        for (JsonAdaptedSavings jsonAdaptedSavings : this.savingsHistory) {
            Savings savings = jsonAdaptedSavings.toModelType();
            savingsHistory.addToHistory(savings);
        }
        return savingsHistory;
    }
}
