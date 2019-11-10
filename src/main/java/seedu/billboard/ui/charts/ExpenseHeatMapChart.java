package seedu.billboard.ui.charts;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.commons.core.date.DateRange;
import seedu.billboard.commons.core.observable.ObservableData;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.statistics.formats.ExpenseGrouping;
import seedu.billboard.model.statistics.formats.ExpenseHeatMap;
import seedu.billboard.model.statistics.generators.HeatMapGenerator;
import seedu.billboard.ui.charts.converters.DayOfWeekConverter;
import seedu.billboard.ui.charts.converters.MonthConverter;


/**
 * A chart showing the heatmap for the currently displayed expenses.
 */
public class ExpenseHeatMapChart extends ExpenseChart {

    private static final String FXML = "ExpenseHeatMapChart.fxml";
    private static final int SCALE_FACTOR = 8;

    @FXML
    private ProportionalBubbleChart<Integer, Integer> heatMapChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private final ObservableData<ExpenseGrouping> expenseGrouping;
    private final HeatMapGenerator heatMapGenerator;
    private final SeriesManager<Integer, Integer> seriesManager;
    private final DateRange currentYearRange = getCurrentYearRange();

    /**
     * Returns a new {@code ExpenseHeatMapChart} with the specified parameters.
     *
     * @param expenses           An observable wrapper of the currently displayed expenses.
     * @param expenseGrouping    An observable wrapper of the currently selected grouping to group expenses by.
     * @param heatMapGenerator   Instance of a generator that generates the heatmap to be viewed.
     */
    public ExpenseHeatMapChart(ObservableList<? extends Expense> expenses,
                               ObservableData<ExpenseGrouping> expenseGrouping,
                               HeatMapGenerator heatMapGenerator) {
        super(FXML, expenses);
        this.expenseGrouping = expenseGrouping;
        expenseGrouping.setValue(ExpenseGrouping.NONE); // Default value

        this.heatMapGenerator = heatMapGenerator;
        this.seriesManager = new SeriesManager<>(
                expenseGrouping.getValue().getGroupingFunction().group(expenses).keySet(),
                heatMapChart);

        xAxis.setTickLabelFormatter(new MonthConverter(currentYearRange.getStartDate(), TextStyle.SHORT));
        yAxis.setTickLabelFormatter(new DayOfWeekConverter(TextStyle.SHORT));

        updateHeatMap(expenses, expenseGrouping.getValue());
        setupListeners();
    }

    /**
     * Sets up listeners to observe for changes in the relevant observables and update the heatmap accordingly.
     */
    private void setupListeners() {
        expenseGrouping.observe(grouping -> updateHeatMap(expenses, grouping));

        expenses.addListener((ListChangeListener<Expense>) c ->
                updateHeatMap(c.getList(), expenseGrouping.getValue()));
    }

    /**
     * Updates the heatmap based on the given parameters.
     */
    private void updateHeatMap(List<? extends Expense> expenses, ExpenseGrouping grouping) {
        var expenseListMap = grouping.getGroupingFunction().group(expenses);
        seriesManager.updateSeriesSet(expenseListMap.keySet());
        seriesManager.updateSeries(series ->
                updateSeries(heatMapGenerator
                        .generateAsync(expenseListMap.get(series.getName()), currentYearRange), series));
    }

    /**
     * Helper method to get the current date range representing the past year adjusted to start on a monday.
     */
    private DateRange getCurrentYearRange() {
        LocalDate currentDate = LocalDate.now();
        return DateRange.fromClosed(currentDate.minusYears(1).with(DateInterval.WEEK.getAdjuster()), currentDate);
    }

    /**
     * Helper method called when the displayed list of expenses change.
     */
    private void updateSeries(Task<ExpenseHeatMap> newDataTask, XYChart.Series<Integer, Integer> series) {
        newDataTask.setOnSucceeded(event -> {
            ExpenseHeatMap heatMap = newDataTask.getValue();
            List<XYChart.Data<Integer, Integer>> data = mapToData(heatMap.getHeatMapValues());
            Platform.runLater(() -> series.getData().setAll(data));
        });
    }

    /**
     * Helper method to convert the heatmap values into a list of {@code XYChart.Data} for the chart to use.
     */
    private List<XYChart.Data<Integer, Integer>> mapToData(
            List<EnumMap<DayOfWeek, Amount>> heatmapValues) {

        return IntStream.range(0, heatmapValues.size())
                .boxed()
                .flatMap(idx -> heatmapValues.get(idx)
                        .entrySet()
                        .stream()
                        .map(entry -> heatMapEntryToData(idx, entry)))
                .collect(Collectors.toList());
    }

    /**
     * Converts a heatmap entry with an index representing the week, to a data item for the chart.
     */
    private XYChart.Data<Integer, Integer> heatMapEntryToData(Integer week, Map.Entry<DayOfWeek, Amount> entry) {
        return new XYChart.Data<>(week + 1, entry.getKey().getValue(), getAmountValueAdjusted(entry));
    }

    /**
     * Gets the value of the given amounted adjusted by an appropriate scale factor to fit on the chart.
     */
    private double getAmountValueAdjusted(Map.Entry<DayOfWeek, Amount> entry) {
        return Math.log10(entry.getValue().amount.doubleValue()) / SCALE_FACTOR;
    }
}
