package seedu.billboard.ui.charts;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.LineChart;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.statistics.ExpenseTimeline;
import seedu.billboard.model.statistics.Statistics;


public class ExpenseTimelineChart extends ExpenseChart {

    private static final String FXML = "ExpenseTimelineChart.fxml";


    @FXML
    private LineChart<Number, String> timelineChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis YAxis;

    private ObservableList<String> dateNames;


    public ExpenseTimelineChart() {
        super(FXML);
    }

    @FXML
    private void initialize() {
        dateNames = FXCollections.observableArrayList();
    }

    @Override
    public void onDataChange(Statistics stats, ListChangeListener.Change<? extends Expense> change) {
        ExpenseTimeline newTimeline = stats.generateExpenseTimeline(change.getList());
    }

}
