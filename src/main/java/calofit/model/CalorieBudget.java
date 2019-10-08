package calofit.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.NavigableMap;
import java.util.OptionalInt;
import java.util.TreeMap;

public class CalorieBudget {
    private NavigableMap<LocalDate, Integer> budgetHistory;

    public CalorieBudget() {
        this.budgetHistory = new TreeMap<>();
    }

    private OptionalInt toOptional(Map.Entry<LocalDate, Integer> entry) {
        if (entry == null || entry.getValue() == null) {
            return OptionalInt.empty();
        } else {
            return OptionalInt.of(entry.getValue());
        }
    }

    public OptionalInt getCurrentBudget() {
        return toOptional(budgetHistory.lastEntry());
    }

    public OptionalInt getBudgetAt(LocalDate date) {
        return toOptional(budgetHistory.floorEntry(date));
    }

    public void setCurrentBudget(int budget) {
        budgetHistory.put(LocalDate.now(), budget);
    }
}
