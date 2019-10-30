package seedu.billboard.ui.charts;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.statistics.formats.ExpenseBreakdown;
import seedu.billboard.model.statistics.generators.BreakdownGenerator;
import seedu.billboard.model.tag.Tag;


/**
 * A chart showing the breakdown of expense spending by tag for the currently displayed expenses.
 */
public class ExpenseBreakdownChart extends ExpenseChart {

    private static final String FXML = "ExpenseBreakdownChart.fxml";

    @FXML
    private PieChart pieChart;

    private final BreakdownGenerator breakdownGenerator;
    private final ObservableList<PieChart.Data> dataList;


    /**
     * Returns a new {@code ExpenseBreakdownChart} with the specified parameters.
     *
     * @param expenses           An observable wrapper of the currently displayed expenses.
     * @param breakdownGenerator Instance of a class that generates the breakdown to be viewed.
     */
    public ExpenseBreakdownChart(ObservableList<? extends Expense> expenses, BreakdownGenerator breakdownGenerator) {
        super(FXML, expenses);
        this.breakdownGenerator = breakdownGenerator;
        this.dataList = FXCollections.observableArrayList();

        initChart();
    }

    /**
     * Initializes the breakdown values and adds a listener to observe for changes in the underlying list and update the
     * timeline accordingly.
     */
    private void initChart() {
        CompletableFuture<ExpenseBreakdown> expenseBreakdownFuture = breakdownGenerator.generateAsync(expenses);
        expenseBreakdownFuture.thenAccept(expenseBreakdown -> {
            dataList.setAll(breakdownValuesToList(expenseBreakdown.getTagBreakdownValues()));
            pieChart.setData(dataList);
        });

        expenses.addListener((ListChangeListener<Expense>) c ->
                onDataChange(breakdownGenerator.generateAsync(c.getList())));
    }

    /**
     * Helper method called when the displayed list of expenses change.
     */
    private void onDataChange(CompletableFuture<ExpenseBreakdown> newDataFuture) {
        newDataFuture.thenAccept(newData ->
                dataList.setAll(breakdownValuesToList(newData.getTagBreakdownValues())));
    }

    /**
     * Converts a map of breakdown values into a list of pie chart data
     */
    private List<PieChart.Data> breakdownValuesToList(Map<Tag, List<Expense>> breakdownValues) {
        return breakdownValues.entrySet()
                .stream()
                .map(entry -> new PieChart.Data(entry.getKey().tagName,
                        totalAmount(entry.getValue()).amount.doubleValue()))
                .collect(Collectors.toList());
    }

    /**
     * Returns the {@code Amount} representing the total amount of all the expenses.
     *
     * @param expenses List of expenses.
     * @return Total amount.
     */
    private Amount totalAmount(List<Expense> expenses) {
        return expenses.stream()
                .reduce(new Amount("0"), (amount, expense) -> amount.add(expense.getAmount()),
                        Amount::add);
    }
}
