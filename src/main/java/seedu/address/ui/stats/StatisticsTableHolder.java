package seedu.address.ui.stats;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.statistics.CategoryStatistics;
import seedu.address.ui.UiPart;

public class StatisticsTableHolder extends UiPart<Region> {
    private static final String FXML = "statistics/StatisticsPlaceHolder.fxml";
    private final Logger logger = LogsCenter.getLogger(StatisticsPieChartHolder.class);
    private StatisticsTable expenseStatsTable;
    private StatisticsTable incomeStatsTable;

    @FXML
    private GridPane gridPanePlaceHolder;

    public StatisticsTableHolder(ObservableList<CategoryStatistics> expenseStats,
                                    ObservableList<CategoryStatistics> incomeStats) {
        super(FXML);
        this.expenseStatsTable = new StatisticsTable(expenseStats);
        this.incomeStatsTable = new StatisticsTable(incomeStats);
        fillInnerParts();
    }

    private void fillInnerParts() {
        GridPane.setRowIndex(expenseStatsTable.getRoot(), 0);
        GridPane.setColumnIndex(expenseStatsTable.getRoot(), 0);
        GridPane.setRowIndex(incomeStatsTable.getRoot(), 0);
        GridPane.setColumnIndex(incomeStatsTable.getRoot(), 1);
        gridPanePlaceHolder.getChildren().addAll(expenseStatsTable.getRoot(), incomeStatsTable.getRoot());
    }
}
