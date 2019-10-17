package seedu.billboard.ui.charts;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.commons.core.date.DateRange;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.statistics.ExpenseTimeline;

/**
 * Represents a chart showing the timeline for expenses.
 */
public class ExpenseTimelineChart extends ExpenseChart<ExpenseTimeline> {

    private static final String FXML = "ExpenseTimelineChart.fxml";

    @FXML
    private LineChart<String, BigDecimal> timelineChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private final EnumMap<DateInterval, DateTimeFormatter> dateIntervalFormats;
    private XYChart.Series<String, BigDecimal> series;


    public ExpenseTimelineChart(ExpenseTimeline expenseTimeline) {
        super(FXML);

        dateIntervalFormats = new EnumMap<>(DateInterval.class);
        dateIntervalFormats.put(DateInterval.DAY, DateTimeFormatter.ofPattern("dd/MM/yy"));
        dateIntervalFormats.put(DateInterval.WEEK, DateTimeFormatter.ofPattern("dd/MM/yy"));
        dateIntervalFormats.put(DateInterval.MONTH, DateTimeFormatter.ofPattern("MM/yy"));
        dateIntervalFormats.put(DateInterval.YEAR, DateTimeFormatter.ofPattern("yyyy"));

        series = new XYChart.Series<>();
        series.getData().setAll(getData(expenseTimeline.getTimeline(), expenseTimeline.getDateInterval()));
        timelineChart.getData().add(series);
    }


    @Override
    public void onDataChange(ExpenseTimeline newData) {
        Map<DateRange, Amount> timeline = newData.getTimeline();
        List<XYChart.Data<String, BigDecimal>> data = getData(timeline, newData.getDateInterval());
        series.getData().setAll(data);
    }

    private List<XYChart.Data<String, BigDecimal>> getData(Map<DateRange, Amount> timeline, DateInterval interval) {
        return timeline.entrySet()
                .stream()
                .map(entry -> entryToData(entry, interval))
                .collect(Collectors.toList());
    }

    private XYChart.Data<String, BigDecimal> entryToData(Map.Entry<DateRange, Amount> entry,
                                                         DateInterval interval) {

        return new XYChart.Data<>(formatDate(entry.getKey().getStartDate(), interval), entry.getValue().amount);
    }

    private String formatDate(LocalDate date, DateInterval dateInterval) {
        return dateIntervalFormats.get(dateInterval).format(date);
    }
}
