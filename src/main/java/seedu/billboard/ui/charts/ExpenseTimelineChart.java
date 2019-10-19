package seedu.billboard.ui.charts;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
import seedu.billboard.model.statistics.ExpenseTimeline;
import seedu.billboard.model.statistics.TimelineGenerator;

/**
 * Represents a chart showing the timeline for expenses.
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
    private final TimelineGenerator timelineGenerator;

    private XYChart.Series<String, BigDecimal> series;


    public ExpenseTimelineChart(ObservableList<? extends Expense> expenses, ObservableData<DateInterval> interval,
                                TimelineGenerator timelineGenerator) {

        super(FXML, expenses);
        this.interval = interval;
        this.timelineGenerator = timelineGenerator;

        dateIntervalFormats = new EnumMap<>(DateInterval.class);
        setupDateIntervalFormats(dateIntervalFormats);

        series = new XYChart.Series<>();
        initChart();
    }

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
        ExpenseTimeline expenseTimeline = timelineGenerator.generate(expenses, interval.getValue());

        series.getData().setAll(mapToData(expenseTimeline.getTimelineValues(), expenseTimeline.getDateInterval()));
        timelineChart.getData().add(series);

        expenses.addListener((ListChangeListener<Expense>) c ->
                onDataChange(timelineGenerator.generate(expenses, interval.getValue())));
    }


    private void onDataChange(ExpenseTimeline newData) {
        List<Pair<DateRange, Amount>> timeline = newData.getTimelineValues();
        List<XYChart.Data<String, BigDecimal>> data = mapToData(timeline, newData.getDateInterval());
        series.getData().setAll(data);
    }

    /**
     * Transforms a list of pairs date range and amount from the expense timeline into a list of formatted data
     * ready to be displayed.
     */
    private List<XYChart.Data<String, BigDecimal>> mapToData(List<Pair<DateRange, Amount>> timeline,
                                                             DateInterval interval) {
        return timeline.stream()
                .map(entry -> dataFromPair(entry, interval))
                .collect(Collectors.toList());
    }

    /**
     * Transforms a single map entry of {@code DateRange} and {@code Amount} to a data object. The date range is
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
