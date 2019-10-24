package seedu.billboard.model.statistics.formats;

import java.util.Map;
import java.util.List;

import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.tag.Tag;

/**
 * Interface representing the breakdown view of a collection of expenses.
 */
public interface ExpenseBreakdown {

    /**
     * Getter for the the overall breakdown of expenses grouped by tags.
     * @return A Map of tags to a list of expenses containing that tag.
     */
    Map<Tag, List<Expense>> getTagBreakdownValues();

}
