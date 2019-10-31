package seedu.billboard.ui.charts;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javafx.util.Pair;
import seedu.billboard.commons.core.date.DateRange;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.statistics.formats.ExpenseHeatMap;
import seedu.billboard.model.statistics.generators.HeatMapGenerator;

import java.time.DayOfWeek;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    private final XYChart.Series<Integer, Integer> series;

    public ExpenseHeatMapChart(ObservableList<? extends Expense> expenses, HeatMapGenerator heatMapGenerator) {
        super(FXML, expenses);
        this.heatMapGenerator = heatMapGenerator;

        series = new XYChart.Series<>();
        initChart();
    }

    private void initChart() {
        Task<ExpenseHeatMap> heatMapTask = heatMapGenerator.generateAsync(expenses);
        heatMapTask.setOnSucceeded(event -> {
            ExpenseHeatMap expenseHeatMap = heatMapTask.getValue();
            List<XYChart.Data<Integer, Integer>> data = mapToData(expenseHeatMap.getHeatMapValues());
            Platform.runLater(() -> series.getData().setAll(data));
        });
    }

    private List<XYChart.Data<Integer, Integer>> mapToData(
            List<Pair<DateRange, EnumMap<DayOfWeek, Amount>>> heatmapValues) {

        return IntStream.range(0, heatmapValues.size())
                .mapToObj(idx -> new Pair<>(idx, heatmapValues.get(idx)))
                .flatMap(pair -> getEntrySet(pair)
                        .stream()
                        .map(entry -> heatMapEntryToData(pair, entry)))
                .collect(Collectors.toList());
    }

    private Set<Map.Entry<DayOfWeek, Amount>> getEntrySet(Pair<Integer, Pair<DateRange, EnumMap<DayOfWeek, Amount>>> pair) {
        return pair.getValue().getValue()
                .entrySet();
    }

    private XYChart.Data<Integer, Integer> heatMapEntryToData(Pair<Integer, Pair<DateRange, EnumMap<DayOfWeek, Amount>>> pair,
                                                              Map.Entry<DayOfWeek, Amount> entry) {

        return new XYChart.Data<>(pair.getKey(), entry.getKey().getValue(), entry.getValue());
    }
}
