package calofit.storage;

import java.time.LocalDate;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import calofit.commons.exceptions.IllegalValueException;
import calofit.model.CalorieBudget;

/**
 * Jackson-friendly version of {@link CalorieBudget}.
 */
@JsonRootName(value = "budgetHistory")
public class JsonSerializableCalorieBudget {
    public static final String NEGATIVE_BUDGET_VAL = "Negative stored budget value.";
    private NavigableMap<LocalDate, Integer> budgetHistory;

    @JsonCreator
    public JsonSerializableCalorieBudget(@JsonProperty("budgetHistory") Map<LocalDate, Integer> budgets) {
        this.budgetHistory = new TreeMap<>(budgets);
    }

    /**
     * Construct a {@link JsonSerializableCalorieBudget} from a source {@link CalorieBudget}.
     * @param source Source data
     */
    public JsonSerializableCalorieBudget(CalorieBudget source) {
        this.budgetHistory = new TreeMap<>(source.getBudgets());
    }

    /**
     * Converts this {@link JsonSerializableCalorieBudget} to a {@link CalorieBudget} model.
     * @return Converted model
     * @throws IllegalValueException if any invalid data is given.
     */
    public CalorieBudget toModelType() throws IllegalValueException {
        for (int budgetVal : budgetHistory.values()) {
            if (budgetVal < 0) {
                throw new IllegalValueException(NEGATIVE_BUDGET_VAL);
            }
        }
        CalorieBudget budget = new CalorieBudget(budgetHistory);
        return budget;
    }
}
