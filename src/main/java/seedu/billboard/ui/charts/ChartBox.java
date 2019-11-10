package seedu.billboard.ui.charts;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.commons.core.observable.ObservableData;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.statistics.formats.ExpenseGrouping;
import seedu.billboard.model.statistics.formats.StatisticsFormatOptions;
import seedu.billboard.model.statistics.generators.BreakdownGenerator;
import seedu.billboard.model.statistics.generators.HeatMapGenerator;
import seedu.billboard.model.statistics.generators.TimelineGenerator;
import seedu.billboard.model.statistics.formats.StatisticsFormat;
import seedu.billboard.ui.UiPart;


/**
 * Container for charts. Controls type of chart being displayed.
 */
public class ChartBox extends UiPart<Region> {

    private static final String FXML = "ChartBox.fxml";

    @FXML
    private AnchorPane chartContainer;

    private ObservableList<Expense> expenses;
    private ObservableData<DateInterval> dateInterval;
    private ObservableData<ExpenseGrouping> expenseGrouping;
    private ExpenseChart currentChart;

    public ChartBox(ObservableList<Expense> expenses, ObservableData<StatisticsFormat> statsType,
                    ObservableData<StatisticsFormatOptions> statsOptions) {
        super(FXML);
        this.expenses = expenses;
        this.dateInterval = new ObservableData<>();
        this.expenseGrouping = new ObservableData<>();
        dateInterval.setValue(DateInterval.MONTH);
        expenseGrouping.setValue(ExpenseGrouping.NONE);

        statsType.observe(this::onStatsTypeChanged);
        statsOptions.observe(options -> {
            options.getNewDateInterval().ifPresent(value -> dateInterval.setValue(value));
            options.getGrouping().ifPresent(value -> expenseGrouping.setValue(value));
        });
    }

    /**
     * Callback which is called when the observed statistics type changes.
     * @param type New statistic type to display.
     */
    private void onStatsTypeChanged(StatisticsFormat type) {
        if (currentChart != null) {
            chartContainer.getChildren().remove(currentChart.getRoot());
        }

        switch (type) {
        case TIMELINE:
            currentChart = new ExpenseTimelineChart(expenses, dateInterval, expenseGrouping, new TimelineGenerator());
            break;
        case BREAKDOWN:
            currentChart = new ExpenseBreakdownChart(expenses, expenseGrouping, new BreakdownGenerator());
            break;
        case HEAT_MAP:
            currentChart = new ExpenseHeatMapChart(expenses, expenseGrouping, new HeatMapGenerator());
            break;
        default:
            throw new UnsupportedOperationException("Chart not implemented for selected statistic");
        }

        chartContainer.getChildren().add(currentChart.getRoot());
    }
}
