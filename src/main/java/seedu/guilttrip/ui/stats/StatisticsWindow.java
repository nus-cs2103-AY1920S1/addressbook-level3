package seedu.guilttrip.ui.stats;

import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.logic.Logic;
import seedu.guilttrip.model.statistics.CategoryStatistics;
import seedu.guilttrip.ui.UiPart;

/**
 * Displays the Statistics of the user's activities in either Table or Pie Form.
 */
public class StatisticsWindow extends UiPart<Region> {

    private static final String FXML = "statistics/Statistics.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Logic logic;
    private StatisticsTablePanel statsTable;
    private StatisticsPieChartPanel statsPie;
    private ObservableList<CategoryStatistics> expenseStats;
    private ObservableList<CategoryStatistics> incomeStats;
    private DoubleProperty totalBalance;

    // Independent Ui parts residing in this Ui container

    @FXML
    private HBox tablePiePlaceHolder;

    @FXML
    private VBox sideStatisticsPanel;

    @FXML
    private Label expenseLabel;

    @FXML
    private Label incomeLabel;

    public StatisticsWindow(ObservableList<CategoryStatistics> expenseStats,
                            ObservableList<CategoryStatistics> incomeStats, DoubleProperty totalExpense,
                            DoubleProperty totalIncome) {
        super(FXML);
        this.statsTable = new StatisticsTablePanel(expenseStats, incomeStats);
        this.statsPie = new StatisticsPieChartPanel(expenseStats, incomeStats);
        // Set dependencies
        this.logic = logic;
        this.expenseLabel.textProperty().bind(Bindings.convert(totalExpense));
        this.incomeLabel.textProperty().bind(Bindings.convert(totalIncome));
        fillStatsTable();
    }

    /**
     * Fills up the tablePiePlaceHolder with the Table chart.
     */
    public void fillStatsTable() {
        tablePiePlaceHolder.getChildren().clear();
        tablePiePlaceHolder.getChildren().add(statsTable.getRoot());
    }

    /**
     * Fills up the tablePiePlaceHolder with the pie chart.
     */
    public void fillStatsPie() {
        tablePiePlaceHolder.getChildren().clear();
        tablePiePlaceHolder.getChildren().add(statsPie.getRoot());
    }

}
