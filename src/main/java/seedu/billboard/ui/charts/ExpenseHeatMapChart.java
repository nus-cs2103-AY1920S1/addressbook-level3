package seedu.billboard.ui.charts;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BubbleChart;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.statistics.generators.HeatMapGenerator;

public class ExpenseHeatMapChart extends ExpenseChart {

    private static final String FXML = "ExpenseHeatMapChart.fxml";

    @FXML
    private BubbleChart<String, String> heatMapChart;

    public ExpenseHeatMapChart(ObservableList<? extends Expense> expenses, HeatMapGenerator heatMapGenerator) {
        super(FXML, expenses);
    }
}
