package calofit.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.NavigableMap;
import java.util.OptionalInt;
import java.util.TreeMap;

/**
 * Represents a historical record of calorie budget settings.
 */
public class CalorieBudget {
    private NavigableMap<LocalDate, Integer> budgetHistory;

    /**
     * Construct an empty {@link CalorieBudget}.
     */
    public CalorieBudget() {
        this.budgetHistory = new TreeMap<>();
    }

    /**
     * Utility method to extract an {@link OptionalInt} from a map entry.
     * @param entry Map entry to extract from.
     * @return Value, or {@link OptionalInt::empty} if null.
     */
    private OptionalInt toOptional(Map.Entry<LocalDate, Integer> entry) {
        if (entry == null || entry.getValue() == null) {
            return OptionalInt.empty();
        } else {
            return OptionalInt.of(entry.getValue());
        }
    }

    /**
     * Get the current budget set by the user.
     * @return Budget set by the user, or {@link OptionalInt::empty} if unset.
     */
    public OptionalInt getCurrentBudget() {
        return toOptional(budgetHistory.lastEntry());
    }

    /**
     * Get the budget set by the user, set at the given date.
     * @return Budget set by the user, or {@link OptionalInt::empty} if unset.
     */
    public OptionalInt getBudgetAt(LocalDate date) {
        return toOptional(budgetHistory.floorEntry(date));
    }

    /**
     * Sets a new budget value, taking effect from today onwards.
     * @param budget New budget value
     */
    public void setCurrentBudget(int budget) {
        budgetHistory.put(LocalDate.now(), budget);
    }
}
