package seedu.billboard.ui.charts;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
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

/**
 * A chart showing the timeline for the currently displayed expenses.
 */
public class ExpenseTimelineChart extends ExpenseChart {

    private static final String FXML = "ExpenseTimelineChart.fxml";

    @FXML
    private LineChart<String, BigDecimal> timelineChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private final EnumMap<DateInterval, DateTimeFormatter> dateIntervalFormats;
    private final ObservableData<DateInterval> interval;
    private final ObservableData<ExpenseGrouping> expenseGrouping;
    private final TimelineGenerator timelineGenerator;
    private final Map<String, XYChart.Series<String, BigDecimal>> seriesMap;


    /**
     * Returns a new {@code ExpenseTimelineChart} with the specified parameters.
     *
     * @param expenses          An observable wrapper of the currently displayed expenses.
     * @param interval          Selected date interval to display.
     * @param expenseGrouping   Selected expense grouping to use.
     * @param timelineGenerator Instance of a class that generates the timeline to be viewed.
     */
    public ExpenseTimelineChart(ObservableList<? extends Expense> expenses, ObservableData<DateInterval> interval,
                                ObservableData<ExpenseGrouping> expenseGrouping,
                                TimelineGenerator timelineGenerator) {

        super(FXML, expenses);
        this.interval = interval;
        this.expenseGrouping = expenseGrouping;
        this.timelineGenerator = timelineGenerator;
        this.seriesMap = new HashMap<>();

        dateIntervalFormats = new EnumMap<>(DateInterval.class);
        setupDateIntervalFormats(dateIntervalFormats);

        initChart();
    }

    /**
     * Helper method to setup an enum map of date intervals to a formatter to format the date ranges for displaying
     * on the chart.
     */
    private void setupDateIntervalFormats(EnumMap<DateInterval, DateTimeFormatter> dateIntervalFormats) {
        dateIntervalFormats.put(DateInterval.DAY, DateTimeFormatter.ofPattern("dd/MM/yy"));
        dateIntervalFormats.put(DateInterval.WEEK, DateTimeFormatter.ofPattern("dd/MM/yy"));
        dateIntervalFormats.put(DateInterval.MONTH, DateTimeFormatter.ofPattern("MMM/yy"));
        dateIntervalFormats.put(DateInterval.YEAR, DateTimeFormatter.ofPattern("yyyy"));
    }

    /**
     * Initializes the timeline values and adds a listener to observe for changes in the underlying list and update the
     * timeline accordingly.
     */
    private void initChart() {
        logger.info("init timeline");
        Map<String, ? extends List<? extends Expense>> map = expenseGrouping.getValue()
                .getGroupingFunction()
                .group(expenses);

        for (var entry : map.entrySet()) {
            ExpenseTimeline expenseTimeline = timelineGenerator.generate(entry.getValue(), interval.getValue());
            XYChart.Series<String, BigDecimal> series = new XYChart.Series<>();
            series.setName(entry.getKey());
            series.getData().setAll(transformToData(expenseTimeline));

            seriesMap.put(entry.getKey(), series);
        }

        timelineChart.getData().setAll(seriesMap.values());

        expenses.addListener((ListChangeListener<Expense>) c -> {
            logger.info("list changed : " + c.getList());
            Map<String, ? extends List<? extends Expense>> map2 = expenseGrouping.getValue()
                    .getGroupingFunction()
                    .group(c.getList());

            for (var entry : map2.entrySet()) {
                onDataChange(timelineGenerator.generateAsync(entry.getValue(), interval.getValue()), entry.getKey());
            }
        });
    }


    /**
     * Helper method called when the displayed list of expenses change.
     */
    private void onDataChange(Task<ExpenseTimeline> newTimelineTask, String seriesName) {
        newTimelineTask.setOnSucceeded(event -> {
            ExpenseTimeline timeline = newTimelineTask.getValue();
            List<XYChart.Data<String, BigDecimal>> data = transformToData(timeline);

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
    private List<XYChart.Data<String, BigDecimal>> transformToData(ExpenseTimeline timeline) {
        List<Pair<DateRange, Amount>> values = timeline.getTimelineValues();
        return values.stream()
                .map(entry -> dataFromPair(entry, timeline.getDateInterval()))
                .collect(toList());
    }

    /**
     * Transforms a single pair of {@code DateRange} and {@code Amount} to a data object. The date range is
     * formatted as a string according to the given date interval.
     */
    private XYChart.Data<String, BigDecimal> dataFromPair(Pair<DateRange, Amount> entry,
                                                          DateInterval interval) {

        return new XYChart.Data<>(formatDate(entry.getKey().getStartDate(), interval), entry.getValue().amount);
    }

    /**
     * Formats a date as a string using the given date interval, as defined in {@code dateIntervalFormats}.
     */
    private String formatDate(LocalDate date, DateInterval dateInterval) {
        return dateIntervalFormats.get(dateInterval).format(date);
    }
}
