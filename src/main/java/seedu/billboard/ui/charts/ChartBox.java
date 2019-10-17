package seedu.billboard.ui.charts;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.statistics.Statistics;
import seedu.billboard.ui.UiPart;


public class ChartBox extends UiPart<Region> implements ListChangeListener<Expense> {

    private static final String FXML = "ChartBox.fxml";

    @FXML
    private AnchorPane chartContainer;

    private final Statistics stats;
    private ChartType chartType;
    private ExpenseChart expenseChart;

    public ChartBox(Statistics stats, ObservableList<Expense> expenses) {
        super(FXML);
        this.stats = stats;
        this.chartType = ChartType.TIMELINE;
        expenses.addListener(this);
    }

    @FXML
    public void initialize() {
        expenseChart = ChartType.TIMELINE.getChart();
        chartContainer.getChildren().add(expenseChart.getRoot());
    }

    public void setChartType(ChartType type) {
        if (type != chartType) {
            chartContainer.getChildren().remove(expenseChart.getRoot());
            expenseChart = type.getChart();
            chartContainer.getChildren().add(expenseChart.getRoot());
        }
    }

    @Override
    public void onChanged(Change<? extends Expense> c) {
        expenseChart.onDataChange(stats, c);
    }
}
