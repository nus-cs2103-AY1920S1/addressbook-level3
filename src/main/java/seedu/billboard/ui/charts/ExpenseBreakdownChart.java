package seedu.billboard.ui.charts;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import seedu.billboard.commons.core.observable.ObservableData;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.statistics.formats.ExpenseBreakdown;
import seedu.billboard.model.statistics.formats.ExpenseGrouping;
import seedu.billboard.model.statistics.generators.BreakdownGenerator;


/**
 * A chart showing the breakdown of expense spending by tag for the currently displayed expenses.
 */
public class ExpenseBreakdownChart extends ExpenseChart {

    private static final String FXML = "ExpenseBreakdownChart.fxml";

    @FXML
    private PieChart pieChart;

    private final ObservableData<ExpenseGrouping> expenseGrouping;
    private final BreakdownGenerator breakdownGenerator;
    private final ObservableList<PieChart.Data> dataList;


    /**
     * Returns a new {@code ExpenseBreakdownChart} with the specified parameters.
     *
     * @param expenses           An observable wrapper of the currently displayed expenses.
     * @param expenseGrouping    An observable wrapper of the currently selected grouping to group expenses by.
     * @param breakdownGenerator Instance of a generator that generates the breakdown to be viewed.
     */
    public ExpenseBreakdownChart(ObservableList<? extends Expense> expenses,
                                 ObservableData<ExpenseGrouping> expenseGrouping,
                                 BreakdownGenerator breakdownGenerator) {
        super(FXML, expenses);
        this.expenseGrouping = expenseGrouping;
        this.breakdownGenerator = breakdownGenerator;
        this.dataList = FXCollections.observableArrayList();

        ExpenseBreakdown expenseBreakdown = breakdownGenerator.generate(expenses);
        dataList.setAll(breakdownValuesToList(expenseBreakdown.getBreakdownValues()));
        pieChart.setData(dataList);

        setupListeners();
    }

    /**
     * Sets up listeners to observe for changes in the relevant observables and update the breakdown accordingly.
     */
    private void setupListeners() {
        expenseGrouping.observe(grouping ->
                updateBreakdown(breakdownGenerator.generateAsync(expenses, grouping)));

        expenses.addListener((ListChangeListener<Expense>) c ->
                updateBreakdown(breakdownGenerator.generateAsync(c.getList(), expenseGrouping.getValue())));
    }

    /**
     * Helper method called when the displayed list of expenses change.
     */
    private void updateBreakdown(Task<ExpenseBreakdown> newDataTask) {
        newDataTask.setOnSucceeded(event -> {
            ExpenseBreakdown newData = newDataTask.getValue();
            List<PieChart.Data> data = breakdownValuesToList(newData.getBreakdownValues());
            Platform.runLater(() -> dataList.setAll(data));
        });
    }

    /**
     * Converts a map of breakdown values into a list of pie chart data
     */
    private List<PieChart.Data> breakdownValuesToList(Map<String, ? extends List<? extends Expense>> breakdownValues) {
        return breakdownValues.entrySet()
                .stream()
                .map(entry -> new PieChart.Data(entry.getKey(),
                        totalAmount(entry.getValue()).amount.doubleValue()))
                .collect(Collectors.toList());
    }

    /**
     * Returns the {@code Amount} representing the total amount of all the expenses.
     *
     * @param expenses List of expenses.
     * @return Total amount.
     */
    private Amount totalAmount(List<? extends Expense> expenses) {
        return expenses.stream()
                .reduce(new Amount("0"), (amount, expense) -> amount.add(expense.getAmount()),
                        Amount::add);
    }
}
