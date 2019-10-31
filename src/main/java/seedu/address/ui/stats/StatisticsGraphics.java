package seedu.address.ui.stats;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.statistics.CategoryStatistics;
import seedu.address.ui.UiPart;

/**
 * Displays the user's statistics in graphics for the more visual User.
 */
public class StatisticsGraphics extends UiPart<Region> {

    private static final String FXML = "/statistics/StatisticsChart.fxml";
    private final Logger logger = LogsCenter.getLogger(StatisticsGraphics.class);
    private final StatisticsPieChart expenseChart;
    private final StatisticsPieChart incomeChart;
    private final ObservableList<CategoryStatistics> expenseStats;
    private final ObservableList<CategoryStatistics> incomeStats;

    @FXML
    private HBox barChartPanelHolder;

    @FXML
    private SplitPane pieChartPanelHolder;

    @FXML
    private AnchorPane pieChartPanelHolderLeft;

    @FXML
    private AnchorPane pieChartPanelHolderRight;

    public StatisticsGraphics(ObservableList<CategoryStatistics> expenseStats,
                              ObservableList<CategoryStatistics> incomeStats) {
        super(FXML);
        expenseChart = new StatisticsPieChart(expenseStats);
        incomeChart = new StatisticsPieChart(incomeStats);
        this.expenseStats = expenseStats;
        this.incomeStats = incomeStats;
        pieChartPanelHolderLeft.getChildren().add(expenseChart.getRoot());
        pieChartPanelHolderRight.getChildren().add(incomeChart.getRoot());
    }

}
