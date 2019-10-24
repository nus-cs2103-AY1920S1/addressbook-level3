package seedu.billboard.model.statistics.formats;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.tag.Tag;

/**
 * Represents the breakdown of spending of a collection of expenses grouped by tags.
 */
public class FilledExpenseBreakdown implements ExpenseBreakdown {

    private Map<Tag, List<Expense>> tagBreakdownValues;

    /**
     * Creates a new FilledExpenseBreakdown with the given map.
     * @param tagBreakdownValues A map of tags to the list of expenses that are tagged with that specific tag.
     */
    public FilledExpenseBreakdown(Map<Tag, List<Expense>> tagBreakdownValues) {
        Objects.requireNonNull(tagBreakdownValues);
        this.tagBreakdownValues = tagBreakdownValues;
    }


    @Override
    public Map<Tag, List<Expense>> getTagBreakdownValues() {
        return tagBreakdownValues;
    }
}
