package seedu.billboard.ui.charts;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
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
    private final DateRange currentYearRange = getCurrentYearRange();

    public ExpenseHeatMapChart(ObservableList<? extends Expense> expenses, HeatMapGenerator heatMapGenerator) {
        super(FXML, expenses);
        this.heatMapGenerator = heatMapGenerator;

        series = new XYChart.Series<>();
        series.setName("All expenses");

        setUpAxesFormatting();
        initChart();
    }

    private void setUpAxesFormatting() {
        xAxis.setTickLabelFormatter(new StringConverter<>() {
            @Override
            public String toString(Number number) {
                return Month.of((int) Math.ceil(((number.doubleValue() / 4.5)  + currentYearRange.getStartDate().getMonthValue()) % 12))
                        .getDisplayName(TextStyle.SHORT, Locale.getDefault());
            }

            @Override
            public Number fromString(String string) {
                return Month.valueOf(string).getValue();
            }
        });

        yAxis.setTickLabelFormatter(new StringConverter<>() {
            @Override
            public String toString(Number number) {
                return DayOfWeek.of(number.intValue()).getDisplayName(TextStyle.SHORT, Locale.getDefault());
            }

            @Override
            public Number fromString(String string) {
                return DayOfWeek.valueOf(string).getValue();
            }
        });
    }

    private void initChart() {
        ExpenseHeatMap expenseHeatMap = heatMapGenerator.generate(expenses, currentYearRange);

        series.getData().setAll(mapToData(expenseHeatMap.getHeatMapValues()));
        heatMapChart.getData().add(series);

        expenses.addListener((ListChangeListener<Expense>) c ->
                onDataChange(heatMapGenerator.generateAsync(c.getList(), currentYearRange)));
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

    /**
     * Helper method to get the current date range representing the past year adjusted to start on a monday.
     */
    private DateRange getCurrentYearRange() {
        LocalDate currentDate = LocalDate.now();
        return DateRange.fromClosed(currentDate.minusYears(1).with(DateInterval.WEEK.getAdjuster()), currentDate);
    }

    /**
     * Helper method to convert the heatmap values into a list of {@code XYChart.Data} for the chart to use.
     */
    private List<XYChart.Data<Integer, Integer>> mapToData(
            List<Pair<DateRange, EnumMap<DayOfWeek, Amount>>> heatmapValues) {

        return IntStream.range(0, heatmapValues.size())
                .boxed()
                .flatMap(idx -> heatmapValues.get(idx)
                        .getValue()
                        .entrySet()
                        .stream()
                        .map(entry -> heatMapEntryToData(idx, entry)))
                .collect(Collectors.toList());
    }

    /**
     * Converts a heatmap entry with an index representing the week, to a data item for the chart.
     */
    private XYChart.Data<Integer, Integer> heatMapEntryToData(Integer week, Map.Entry<DayOfWeek, Amount> entry) {
        return new XYChart.Data<>(week, entry.getKey().getValue(), getAmountValueAdjusted(entry));
    }


    private double getAmountValueAdjusted(Map.Entry<DayOfWeek, Amount> entry) {
        return Math.log10(entry.getValue().amount.doubleValue()) / 2;
    }
}
