package seedu.savenus.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import seedu.savenus.model.savings.Savings;

import java.util.ArrayList;
import java.util.List;

/**
 * An Immutable Savings Account that is serializable to JSON format.
 */
@JsonRootName(value = "savings")
public class JsonSerializableSavingsAccount {

    // This enables the tracking of records of past savings made by the user.
    private final List<JsonAdaptedSavings> savingsRecord = new ArrayList<>();
    private JsonAdaptedSavings currSavings;

    /**
     * Constructs a {@code JsonSerializableSavingsAccount} with the given savings history.
     */
    @JsonCreator
    public JsonSerializableSavingsAccount(@JsonProperty("savingsHistory") List<JsonAdaptedSavings> savingsRecord,
                                          @JsonProperty("currentSavings") JsonAdaptedSavings currSavings) {
        this.savingsRecord.addAll(savingsRecord);
        this.currSavings = currSavings;
    }

}
