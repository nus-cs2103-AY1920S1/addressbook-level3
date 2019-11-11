package seedu.billboard.ui.charts;

import static java.util.stream.Collectors.summarizingLong;
import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.LongUnaryOperator;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.util.Pair;
import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.commons.core.date.DateRange;
import seedu.billboard.commons.core.observable.ObservableData;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.statistics.formats.ExpenseGrouping;
import seedu.billboard.model.statistics.formats.ExpenseTimeline;
import seedu.billboard.model.statistics.generators.TimelineGenerator;
import seedu.billboard.ui.charts.converters.FormattedDateConverter;
import seedu.billboard.ui.charts.converters.TruncatedNumberConverter;

/**
 * A chart showing the timeline for the currently displayed expenses.
 */
public class ExpenseTimelineChart extends ExpenseChart {

    private static final String FXML = "ExpenseTimelineChart.fxml";

    @FXML
    private LineChart<Long, BigDecimal> timelineChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private final ObservableData<DateInterval> dateInterval;
    private final ObservableData<ExpenseGrouping> expenseGrouping;
    private final TimelineGenerator timelineGenerator;
    private final SeriesManager<Long, BigDecimal> seriesManager;
    private final FormattedDateConverter formattedDateConverter;

    /**
     * Returns a new {@code ExpenseTimelineChart} with the specified parameters.
     *
     * @param expenses          An observable wrapper of the currently displayed expenses.
     * @param dateInterval      Selected date dateInterval to display.
     * @param expenseGrouping   Selected expense grouping to use.
     * @param timelineGenerator Instance of a class that generates the timeline to be viewed.
     */
    public ExpenseTimelineChart(ObservableList<? extends Expense> expenses, ObservableData<DateInterval> dateInterval,
                                ObservableData<ExpenseGrouping> expenseGrouping,
                                TimelineGenerator timelineGenerator) {

        super(FXML, expenses);
        this.dateInterval = dateInterval;
        this.expenseGrouping = expenseGrouping;
        dateInterval.setValue(DateInterval.MONTH);
        expenseGrouping.setValue(ExpenseGrouping.NONE); // Default values

        this.timelineGenerator = timelineGenerator;
        this.seriesManager = new SeriesManager<>(
                expenseGrouping.getValue().getGroupingFunction().group(expenses).keySet(),
                timelineChart);

        formattedDateConverter = new FormattedDateConverter(getDateIntervalFormats(dateInterval.getValue()));
        xAxis.setTickLabelFormatter(formattedDateConverter);
        xAxis.setTickLabelRotation(90);
        yAxis.setTickLabelFormatter(new TruncatedNumberConverter());

        updateTimeline(expenses, expenseGrouping.getValue(), dateInterval.getValue());
        setupListeners();
    }

    /**
     * Sets up listeners to observe for changes in the relevant observables and update the timeline accordingly.
     */
    private void setupListeners() {
        expenseGrouping.observe(grouping -> updateTimeline(expenses, grouping, dateInterval.getValue()));

        expenses.addListener((ListChangeListener<Expense>) c ->
                updateTimeline(c.getList(), expenseGrouping.getValue(), dateInterval.getValue()));

        dateInterval.observe(newInterval -> {
            formattedDateConverter.setFormatter(getDateIntervalFormats(newInterval));
            updateTimeline(expenses, expenseGrouping.getValue(), newInterval);
        });
    }

    /**
     * Updates the timeline upon new expenses, groupings, or intervals. If the numbers/types of series changes,
     * {@code setupSeriesMapping} will be called to reset the mappings.
     */
    private void updateTimeline(List<? extends Expense> expenses, ExpenseGrouping grouping, DateInterval interval) {
        LongSummaryStatistics statistics = expenses.stream()
                .collect(summarizingLong(expense -> expense.getCreated().dateTime.toLocalDate().toEpochDay()));

        double tickUnit = getDateIntervalTickUnit(interval, statistics.getMax() - statistics.getMin());
        if (!expenses.isEmpty()) {
            updateXAxisRange(statistics.getMin(), statistics.getMax(), tickUnit, interval);
        } else {
            long present = LocalDate.now().toEpochDay();
            updateXAxisRange(present, present, tickUnit, interval);
        }

        Map<String, ? extends List<? extends Expense>> expenseListMap = grouping.getGroupingFunction().group(expenses);
        seriesManager.updateSeriesSet(expenseListMap.keySet());
        seriesManager.updateSeries(series ->
                updateSeries(timelineGenerator.generateAsync(expenseListMap.get(series.getName()), interval), series));
    }

    /**
     * Helper method called to asynchronously update a series.
     */
    private void updateSeries(Task<ExpenseTimeline> newTimelineTask, XYChart.Series<Long, BigDecimal> series) {
        newTimelineTask.setOnSucceeded(event -> {
            ExpenseTimeline timeline = newTimelineTask.getValue();
            List<XYChart.Data<Long, BigDecimal>> data = transformToData(timeline);
            Platform.runLater(() -> series.getData().setAll(data));
        });
    }

    /**
     * Updates the xAxis' range with the given values, where min and max should be the min and max of the current range
     * shown on the xAxis, and tick unit is dependent on the selected interval.
     */
    private void updateXAxisRange(long min, long max, double adjustedTickUnit, DateInterval interval) {
        LongUnaryOperator adjuster = x -> LocalDate.ofEpochDay(x)
                .with(interval.getAdjuster())
                .plusDays(interval == DateInterval.DAY ? 0 : 1)
                .toEpochDay();
        xAxis.setLowerBound(adjuster.applyAsLong(min) - adjustedTickUnit);
        xAxis.setUpperBound(adjuster.applyAsLong(max) + adjustedTickUnit);
        xAxis.setTickUnit(adjustedTickUnit);
    }

    /**
     * Maps a list of pairs of date range and amount into a list of formatted data ready to be displayed.
     */
    private List<XYChart.Data<Long, BigDecimal>> transformToData(ExpenseTimeline timeline) {
        return timeline.getTimelineValues()
                .stream()
                .map(this::dataFromPair)
                .collect(toList());
    }

    /**
     * Transforms a single pair of {@code DateRange} and {@code Amount} to a data object. The date range is
     * formatted as a string according to the given date dateInterval.
     */
    private XYChart.Data<Long, BigDecimal> dataFromPair(Pair<DateRange, Amount> entry) {

        return new XYChart.Data<>(entry.getKey().getStartDate().toEpochDay(), entry.getValue().amount);
    }

    /**
     * Helper method to get the appropriate {@code DateTimeFormatter} to format the range represented by the given
     * interval.
     */
    private DateTimeFormatter getDateIntervalFormats(DateInterval interval) {
        switch (interval) {
        case DAY:
            return DateTimeFormatter.ofPattern("dd/MM/yy");
        case WEEK:
            return DateTimeFormatter.ofPattern("dd/MM/yy");
        case MONTH:
            return DateTimeFormatter.ofPattern("MMM/yy");
        case YEAR:
            return DateTimeFormatter.ofPattern("yyyy");
        default:
            assert false : "Missing enum value in switch" + interval;
            throw new UnsupportedOperationException("Date formatter not defined for given date interval.");
        }
    }

    /**
     * Helper method to get the appropriate tick unit to format xAxis of the chart. The units do not have to be exact,
     * and thus are just an approximation.
     */
    private double getDateIntervalTickUnit(DateInterval interval, long range) {
        BinaryOperator<Double> adjustment = (tickUnit, maxTicks) ->
                range / tickUnit > maxTicks ? range / maxTicks : tickUnit;

        switch (interval) {
        case DAY:
            return adjustment.apply(1.0, 25.0);
        case WEEK:
            return adjustment.apply(7.0, 21.0);
        case MONTH:
            return adjustment.apply(30.5, 20.0);
        case YEAR:
            return adjustment.apply(365.0, 15.0);
        default:
            assert false : "Missing enum value in switch" + interval;
            throw new UnsupportedOperationException("Tick unit not defined for given date interval.");
        }
    }

}
