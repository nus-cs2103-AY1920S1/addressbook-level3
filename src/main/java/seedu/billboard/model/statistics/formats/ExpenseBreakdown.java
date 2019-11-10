package seedu.billboard.model.statistics.formats;

import java.util.Map;
import java.util.List;

import seedu.billboard.model.expense.Expense;

/**
 * Interface representing the breakdown view of a collection of expenses.
 */
public interface ExpenseBreakdown {

    /**
     * Getter for the the overall breakdown of expenses grouped according to some criteria.
     * @return A map of string names of groupings to the list of expenses that are in that grouping
     */
    Map<String, ? extends List< ? extends Expense>> getBreakdownValues();

}
