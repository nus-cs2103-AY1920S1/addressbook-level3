package seedu.guilttrip.model.statistics;

import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import seedu.guilttrip.model.entry.Date;

/**
 * The API of the Statistics Component.
 */
public interface Statistics {

    DoubleProperty getTotalExpenseForPeriod();

    DoubleProperty getTotalIncomeForPeriod();

    ObservableList<DailyStatistics> getListOfStatsForBarChart();

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

    void updateListOfStats();

    void updateListOfStats(List<Date> listOfPeriods);

    void updateBarCharts();

    void updateBarCharts(Date monthToShow);
}
