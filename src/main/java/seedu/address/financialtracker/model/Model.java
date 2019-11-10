package seedu.address.financialtracker.model;

import java.util.HashMap;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.StatisticsUtil;
import seedu.address.financialtracker.model.expense.Expense;
import seedu.address.financialtracker.ui.CountriesDropdown;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Encapsulates Financial Tracker and it's underlying ObservableMap.
 */
public class Model {

    // UI dependencies
    private CountriesDropdown countriesDropdown;

    //financial tracker components
    private FinancialTracker financialTracker;
    private ObservableMap<String, ExpenseList> internalUnmodifiableExpenseListMap;

    public Model() {
        this.financialTracker = new FinancialTracker();
        internalUnmodifiableExpenseListMap = this.financialTracker.getInternalUnmodifiableExpenseListMap();
    }

    /**
     * Initialises model with a default financial tracker.
     */
    public Model(FinancialTracker financialTracker) {
        this.financialTracker = financialTracker;
        internalUnmodifiableExpenseListMap = this.financialTracker.getInternalUnmodifiableExpenseListMap();
    }

    /**
     * Adds an expense into the financial tracker.
     */
    public void addExpense(Expense expense) throws CommandException {
        this.financialTracker.addExpense(expense);
    }

    /**
     * Deletes an expense from the financial tracker.
     */
    public void deleteExpense(int index) {
        this.financialTracker.deleteExpense(index);
    }

    /**
     * Sets the country key in financial tracker.
     */
    public void setCountry(String country) {
        this.financialTracker.setCurrentCountry(country);
    }

    public String getCountry() {
        return this.financialTracker.getCurrentCountry();
    }

    /**
     * Sets the comparator methods in financial tracker.
     */
    public void setComparator(String comparator) {
        this.financialTracker.setComparator(comparator);
    }

    /**
     * Returns an expense list from the underlying Map of financial tracker.
     */
    public ObservableList<Expense> getExpenseList() {
        return internalUnmodifiableExpenseListMap
                .get(financialTracker.getCurrentCountry()).asUnmodifiableObservableList();
    }

    /**
     * Edits an expense from the financial tracker.
     */
    public void setExpense(Expense expenseToEdit, Expense editedExpense) throws CommandException {
        CollectionUtil.requireAllNonNull(expenseToEdit, editedExpense);
        financialTracker.setExpense(expenseToEdit, editedExpense);
    }

    public FinancialTracker getFinancialTracker() {
        return financialTracker;
    }

    public HashMap<String, Double> getSummaryMap() {
        return this.financialTracker.getSummaryMap();
    }

    public void clearExpenseList() {
        this.financialTracker.clearExpenseList();
    }

    /**
     * Undo previous user action.
     */
    public void undo() throws CommandException {
        this.financialTracker.undo();
    }

    /**
     * Introduces Ui dependencies using model.
     * Purpose is to modify the countries dropdown menu based on user input.
     * @param countriesDropdown from financial tracker page
     */
    public void addDependencies(CountriesDropdown countriesDropdown) {
        this.countriesDropdown = countriesDropdown;
    }

    /**
     * Updates countries drop down menu from user input {@Code SwitchCommand}.
     */
    public void updateDropDownMenu(String country) throws CommandException {
        countriesDropdown.handleUpdateFromUserInput(country);
    }

    //=========== Statistics =================================================================================

    public ObservableList<PieChart.Data> getFinancialPieChartData() {
        double total = getSummaryMap().get("Total");
        return StatisticsUtil.getFinancialPieChartData(
                getSummaryMap(), entry -> !entry.getKey().equals("Total"),
                entry -> new PieChart.Data(entry.getKey(), entry.getValue() / total));
    }

    public XYChart.Series<String, Number> getFinancialBarChartData() {
        return StatisticsUtil.getFinancialBarChartData(getSummaryMap(), entry -> !entry.getKey().equals("Total"),
                entry -> new XYChart.Data<String, Number>(entry.getKey(), entry.getValue()));
    }
}
