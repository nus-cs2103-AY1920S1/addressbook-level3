package seedu.billboard.ui.charts;

import static java.util.stream.Collectors.summarizingLong;
import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;

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
    private final Map<String, XYChart.Series<Long, BigDecimal>> seriesMap;
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
        this.timelineGenerator = timelineGenerator;
        this.seriesMap = new HashMap<>();

        formattedDateConverter = new FormattedDateConverter(getDateIntervalFormats(dateInterval.getValue()));
        xAxis.setTickLabelFormatter(formattedDateConverter);

        setupSeriesMapping(expenseGrouping.getValue().getGroupingFunction().group(expenses));
        updateTimeline(expenses, expenseGrouping.getValue(), dateInterval.getValue());

        setupListeners();
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
            throw new UnsupportedOperationException("Date formatter not defined for given date interval.");
        }
    }

    /**
     * Helper method to get the appropriate tick unit to format xAxis of the chart. The units do not have to be exact,
     * and thus are just an approximation.
     */
    private int getDateIntervalTickUnit(DateInterval interval) {
        switch (interval) {
        case DAY:
            return 1;
        case WEEK:
            return 7;
        case MONTH:
            return 30;
        case YEAR:
            return 365;
        default:
            throw new UnsupportedOperationException("Tick unit not defined for given date interval.");
        }
    }

    /**
     * Idempotent method which updates {@code seriesMap} with new series names from the keys of the input map, and
     * creates new series based on those names to replace the current series.
     */
    private void setupSeriesMapping(Map<String, ? extends List<? extends Expense>> expenseListMap) {
        seriesMap.clear();
        for (var entry : expenseListMap.entrySet()) {
            XYChart.Series<Long, BigDecimal> series = new XYChart.Series<>();
            series.setName(entry.getKey());
            seriesMap.put(entry.getKey(), series);
        }

        timelineChart.getData().setAll(seriesMap.values());
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

        int tickUnit = getDateIntervalTickUnit(interval);
        updateXAxisRange(statistics.getMin(), statistics.getMax(), tickUnit);


        Map<String, ? extends List<? extends Expense>> expenseListMap = grouping.getGroupingFunction().group(expenses);
        if (!seriesMap.keySet().equals(expenseListMap.keySet())) {
            setupSeriesMapping(expenseListMap);
        }

        List<XYChart.Series<Long, BigDecimal>> unusedSeries = new ArrayList<>();

        for (var series : timelineChart.getData()) {
            String name = series.getName();
            if (expenseListMap.containsKey(name)) {
                updateSeries(timelineGenerator.generateAsync(expenseListMap.get(name), interval), name);
            } else {
                unusedSeries.add(series);
            }
        }
        timelineChart.getData().removeAll(unusedSeries);
    }

    private void updateXAxisRange(long min, long max, double tickUnit) {
        double maxTicks = 20.0;
        long range = max - min;
        double adjustedTickUnit = range / tickUnit > maxTicks ? range / maxTicks : tickUnit;
        xAxis.setLowerBound(min - adjustedTickUnit);
        xAxis.setUpperBound(max + adjustedTickUnit);
        xAxis.setTickUnit(adjustedTickUnit);
    }


    /**
     * Helper method called to asynchronously update each series.
     */
    private void updateSeries(Task<ExpenseTimeline> newTimelineTask, String seriesName) {
        newTimelineTask.setOnSucceeded(event -> {
            ExpenseTimeline timeline = newTimelineTask.getValue();
            List<XYChart.Data<Long, BigDecimal>> data = transformToData(timeline);

            if (!seriesMap.containsKey(seriesName)) {
                logger.warning("Series: " + seriesName + " does not exist in chart.");
                return;
            }
            Platform.runLater(() -> seriesMap.get(seriesName).getData().setAll(data));
        });
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
}
