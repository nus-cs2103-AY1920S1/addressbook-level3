package seedu.billboard.ui.charts;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.Pair;
import javafx.util.StringConverter;

import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.commons.core.date.DateRange;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.statistics.formats.ExpenseHeatMap;
import seedu.billboard.model.statistics.generators.HeatMapGenerator;


/**
 * A chart showing the heatmap for the currently displayed expenses.
 */
public class ExpenseHeatMapChart extends ExpenseChart {

    private static final String FXML = "ExpenseHeatMapChart.fxml";

    @FXML
    private ProportionalBubbleChart<Integer, Integer> heatMapChart;

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
        series.setName("All expenses");
        setUpAxes();
        initChart();
    }

    private void setUpAxes() {
        yAxis.setAutoRanging(false);
        yAxis.setTickUnit(1);
        yAxis.setMinorTickCount(0);
        yAxis.setLowerBound(1);
        yAxis.setUpperBound(7);
        yAxis.setTickLabelFormatter(new StringConverter<>() {
            @Override
            public String toString(Number number) {
                return DayOfWeek.of(number.intValue()).toString();
            }

            @Override
            public Number fromString(String string) {
                return DayOfWeek.valueOf(string).getValue();
            }
        });

        xAxis.setAutoRanging(false);
        xAxis.setTickUnit(4);
        xAxis.setMinorTickCount(4);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(54);
    }

    private void initChart() {
        DateRange currentYearRange = getCurrentYearRange();
        ExpenseHeatMap expenseHeatMap = heatMapGenerator.generate(expenses, currentYearRange);

        series.getData().setAll(mapToData(expenseHeatMap.getHeatMapValues()));
        heatMapChart.getData().add(series);

        expenses.addListener((ListChangeListener<Expense>) c -> {
            onDataChange(heatMapGenerator.generateAsync(c.getList(), getCurrentYearRange()));
        });
    }

    /**
     * Helper method called when the displayed list of expenses change.
     */
    private void onDataChange(Task<ExpenseHeatMap> newDataTask) {
        newDataTask.setOnSucceeded(event -> {
            ExpenseHeatMap heatMap = newDataTask.getValue();
            List<XYChart.Data<Integer, Integer>> data = mapToData(heatMap.getHeatMapValues());
            Platform.runLater(() -> series.getData().setAll(data));
        });
    }

    private DateRange getCurrentYearRange() {
        LocalDate currentDate = LocalDate.now();
        return DateRange.fromClosed(currentDate.minusYears(1).with(DateInterval.WEEK.getAdjuster()), currentDate);
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

    private Set<Map.Entry<DayOfWeek, Amount>> getEntrySet(
            Pair<Integer, Pair<DateRange, EnumMap<DayOfWeek, Amount>>> pair) {
        return pair.getValue().getValue()
                .entrySet();
    }

    private XYChart.Data<Integer, Integer> heatMapEntryToData(Pair<Integer, Pair<DateRange, EnumMap<DayOfWeek, Amount>>> pair,
                                                              Map.Entry<DayOfWeek, Amount> entry) {

        return new XYChart.Data<>(pair.getKey(), entry.getKey().getValue(), getAmountValueAdjusted(entry));
    }

    private double getAmountValueAdjusted(Map.Entry<DayOfWeek, Amount> entry) {
        return Math.log10(entry.getValue().amount.doubleValue()) / 2;
    }
}
