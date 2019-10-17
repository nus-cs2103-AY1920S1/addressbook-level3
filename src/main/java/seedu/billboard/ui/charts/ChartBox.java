package seedu.billboard.ui.charts;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.statistics.ExpenseTimeline;
import seedu.billboard.model.statistics.Statistics;
import seedu.billboard.ui.UiPart;

/**
 * Container for charts.
 */
public class ChartBox extends UiPart<Region> implements ListChangeListener<Expense> {

    private static final String FXML = "ChartBox.fxml";

    @FXML
    private AnchorPane chartContainer;

    private final Statistics stats;
    private ExpenseTimelineChart expenseTimelineChart;

    public ChartBox(Statistics stats, ObservableList<Expense> expenses) {
        super(FXML);
        this.stats = stats;

        expenses.addListener(this);
        expenseTimelineChart = new ExpenseTimelineChart(stats.generateExpenseTimeline(expenses));
        chartContainer.getChildren().add(expenseTimelineChart.getRoot());
    }

    @Override
    public void onChanged(Change<? extends Expense> c) {
        ExpenseTimeline timeline = stats.generateExpenseTimeline(c.getList());
        expenseTimelineChart.onDataChange(timeline);
    }
}
