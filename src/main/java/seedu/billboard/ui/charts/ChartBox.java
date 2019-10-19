package seedu.billboard.ui.charts;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.commons.core.observable.ObservableData;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.statistics.ExpenseTimeline;
import seedu.billboard.model.statistics.Statistics;
import seedu.billboard.model.statistics.StatisticsGenerator;
import seedu.billboard.model.statistics.StatisticsType;
import seedu.billboard.ui.UiPart;

/**
 * Container for charts.
 */
public class ChartBox extends UiPart<Region> {

    private static final String FXML = "ChartBox.fxml";

    @FXML
    private AnchorPane chartContainer;

    private ObservableList<Expense> expenses;
    private ExpenseTimelineChart expenseTimelineChart;

    public ChartBox(ObservableData<StatisticsType> statsType, ObservableList<Expense> expenses) {
        super(FXML);

        this.expenses = expenses;
        statsType.observe(this::onStatsTypeChanged);
    }

    private void onStatsTypeChanged(StatisticsType type) {
        expenseTimelineChart = new ExpenseTimelineChart(
                new StatisticsGenerator().generateExpenseTimeline(expenses, DateInterval.MONTH));

        chartContainer.getChildren().add(expenseTimelineChart.getRoot());
    }
}
