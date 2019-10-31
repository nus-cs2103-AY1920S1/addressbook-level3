package seedu.billboard.ui.charts;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.statistics.generators.HeatMapGenerator;

/**
 * A chart showing the heatmap for the currently displayed expenses.
 */
public class ExpenseHeatMapChart extends ExpenseChart {

    private static final String FXML = "ExpenseHeatMapChart.fxml";

    @FXML
    private BubbleChart<String, String> heatMapChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private final HeatMapGenerator heatMapGenerator;
    private final XYChart.Series<String, String> series;


    public ExpenseHeatMapChart(ObservableList<? extends Expense> expenses, HeatMapGenerator heatMapGenerator) {
        super(FXML, expenses);
        this.heatMapGenerator = heatMapGenerator;

        series = new XYChart.Series<>();
        initChart();
    }

    private void initChart() {
        //        Task<ExpenseHeatMap> heatMapTask = heatMapGenerator.generateAsync(expenses);
        //        heatMapTask.setOnSucceeded(event -> {
        //
        //        });
    }
}
