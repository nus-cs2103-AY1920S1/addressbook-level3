package seedu.billboard.ui.charts;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import seedu.billboard.model.expense.Expense;

public class ExpenseBreakdownChart extends ExpenseChart {

    private static final String FXML = "ExpenseBreakdownChart.fxml";

    @FXML
    private PieChart breakdownChart;


    public ExpenseBreakdownChart(ObservableList<? extends Expense> expenses) {
        super(FXML, expenses);
    }
}
