package seedu.address.model.statistics;

import javafx.collections.ObservableList;

/**
 * The API of the Statistics Component.
 */
public interface Statistics {

    /**
     * Returns the list of statistics for Expense.
     *
     * @return the list of the statistics by Category for Expense.
     */
    ObservableList<CategoryStatistics> getListOfStatsForExpense();

    /**
     * Returns the list of statistics for Income.
     *
     * @return the list of the statistics by Category for Income.
     */
    ObservableList<CategoryStatistics> getListOfStatsForIncome();
}
