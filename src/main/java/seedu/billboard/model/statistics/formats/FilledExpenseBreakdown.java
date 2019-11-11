package seedu.billboard.model.statistics.formats;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import seedu.billboard.model.expense.Expense;

/**
 * Represents the breakdown of spending of a collection of expenses grouped by tags.
 */
public class FilledExpenseBreakdown implements ExpenseBreakdown {

    private Map<String, ? extends List< ? extends Expense>> breakdownValues;

    /**
     * Creates a new FilledExpenseBreakdown with the given map.
     * @param breakdownValues A map of tags to the list of expenses that are tagged with that specific tag.
     */
    public FilledExpenseBreakdown(Map<String, ? extends List<? extends Expense>> breakdownValues) {
        Objects.requireNonNull(breakdownValues);
        this.breakdownValues = breakdownValues;
    }


    @Override
    public Map<String, ? extends List< ? extends Expense>> getBreakdownValues() {
        return breakdownValues;
    }
}
