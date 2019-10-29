package seedu.address.model.statistics;

import javafx.collections.ObservableList;

public interface Statistics {

    ObservableList<CategoryStatistics> getListOfStatsForExpense();

    ObservableList<CategoryStatistics> getListOfStatsForIncome();
}
