package seedu.address.ui.stats;

import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.model.statistics.CategoryStatistics;
import seedu.address.ui.UiPart;

/**
 * Displays the Statistics of the user's activities in Table form.
 */
public class StatisticsWindow extends UiPart<Region> {

    private static final String FXML = "statistics/Statistics.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Logic logic;
    private StatisticsTableHolder statsTable;
    private StatisticsPieChartHolder statsPie;
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
        this.statsTable = new StatisticsTableHolder(expenseStats, incomeStats);
        this.statsPie = new StatisticsPieChartHolder(expenseStats, incomeStats);
        // Set dependencies
        this.logic = logic;
        this.expenseLabel.textProperty().bind(Bindings.convert(totalExpense));
        this.expenseLabel.textProperty().bind(Bindings.convert(totalIncome));
        fillStatsTable();
    }

    public void fillStatsTable() {
        tablePiePlaceHolder.getChildren().clear();
        tablePiePlaceHolder.getChildren().add(statsTable.getRoot());
    }

    public void fillStatsPie() {
        tablePiePlaceHolder.getChildren().clear();
        tablePiePlaceHolder.getChildren().add(statsPie.getRoot());
    }

}
