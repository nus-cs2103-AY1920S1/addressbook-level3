package seedu.address.model.statistics;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.budget.BudgetPeriod;
import seedu.address.model.category.Category;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Timestamp;

/**
 * Represents the Statistics class that provides a trend line as its Visual Representation method
 */
public class TrendStatistics extends Statistics {

    public static final int INTERVAL_COUNT = 34;

    private static final double BUDGET_LIMIT = 10;
    //hard-code to wait for Budget support
    private Timestamp startDate;

    private Timestamp endDate;

    private BudgetPeriod period;

    private boolean budgetLimitMode;

    private ObservableList<Expense> expenses;

    private List<Timestamp> dates = new ArrayList<>();

    private List<Double> periodicTotalExpenditure = new ArrayList<>();

    private List<Double> periodicBudgetLimits = new ArrayList<>();

    private List<ArrayList<Double>> periodicCategoricalExpenses = new ArrayList<>();

    private TrendStatistics(ObservableList<Expense> expenses, List<Category> validCategories,
                            Timestamp startDate, Timestamp endDate, BudgetPeriod period, boolean isBudgetMode) {

        super(expenses, validCategories);
        this.startDate = startDate;
        this.endDate = endDate;
        this.expenses = getExpenses();
        this.period = period;
        this.budgetLimitMode = isBudgetMode;
        if (!budgetLimitMode) {
            for (int i = 0; i < validCategories.size(); i++) {
                periodicCategoricalExpenses.add(new ArrayList<>());
            }
        }
    }



    /**
     * Creates 2 trend lines to provide visual aid on the occurrence of total expenses relative to the budget limit
     *
     * @param expenses List of expenses
     * @param validCategories List of allowed categories in MooLah
     * @param startDate The start date of the tracking period
     * @param endDate The end date of the tracking period
     * @param period The period of a required interval
     */
    public static TrendStatistics run(ObservableList<Expense> expenses, List<Category> validCategories,
                                      Timestamp startDate, Timestamp endDate,
                                      BudgetPeriod period, boolean isBudgetMode) {

        requireNonNull(startDate);
        requireNonNull(endDate);
        requireNonNull(period);

        TrendStatistics statistics = new TrendStatistics(expenses, validCategories,
                startDate, endDate, period, isBudgetMode);
        statistics.generateTrendLine();
        return statistics;
    }


    /**
     * Gathers the data to be used for the elements of the trend line
     */
    private void generateTrendLine() {
        ArrayList<ArrayList<ArrayList<Expense>>> data = new ArrayList<>();
        Timestamp validDate = startDate;

        int intervalCount = 0;
        while (hasInterval(validDate, endDate, period) && intervalCount < INTERVAL_COUNT) {
            Timestamp localStartDate = validDate;

            //maybe in future I could just use the filter from Budget component
            Timestamp localEndDate = localStartDate.plus(period.getPeriod());

            ArrayList<ArrayList<Expense>> periodicCategorisedExpenses =
                    getPeriodicCategorisedExpenses(localStartDate, localEndDate);


            if (budgetLimitMode) {
                double periodicTotalExpenditure = getTotalExpenditure(periodicCategorisedExpenses);
                this.periodicTotalExpenditure.add(periodicTotalExpenditure);
                periodicBudgetLimits.add(BUDGET_LIMIT);
            } else {
                List<Double> periodicCategoricalExpenditure = getCategoricalExpenditure(periodicCategorisedExpenses);
                flatMapAdd(periodicCategoricalExpenditure);
            }
            dates.add(localStartDate);
            intervalCount++;
            validDate = localEndDate;
        }

        this.setTitle(String.format("Periodic trendline from %s to %s in the unit of %ss",
                startDate.showDate(), endDate.showDate(),
                period));
    }

    /**
     * Adds the periodic expenditure from each category to its respective category list
     * @param periodicCategoricalExpenditure
     */
    private void flatMapAdd(List<Double> periodicCategoricalExpenditure) {
        for (int i = 0; i < periodicCategoricalExpenditure.size(); i++) {
            ArrayList<Double> categoricalExpenses = periodicCategoricalExpenses.get(i);
            categoricalExpenses.add(periodicCategoricalExpenditure.get(i));
        }
    }

    private ArrayList<ArrayList<Expense>> getPeriodicCategorisedExpenses(Timestamp startDate, Timestamp endDate) {
        TabularStatistics statistics = new TabularStatistics(expenses, getValidCategories(), startDate, endDate);
        ArrayList<ArrayList<Expense>> dataWithTotal = statistics.extractRelevantExpenses(startDate, endDate);
        dataWithTotal.remove(dataWithTotal.size() - 1);
        return dataWithTotal;
    }

    private double getExpenditureForCategory(ArrayList<Expense> categorisedExpenses) {
        double total = 0;
        for (Expense expense : categorisedExpenses) {
            total += Double.parseDouble(expense.getPrice().value);
        }
        return total;
    }

    private double getTotalExpenditure(ArrayList<ArrayList<Expense>> data) {
        double total = 0;
        for (ArrayList<Expense> categorisedExpenses : data) {
            total += getExpenditureForCategory(categorisedExpenses);
        }
        return total;
    }

    private List<Double> getCategoricalExpenditure(ArrayList<ArrayList<Expense>> data) {
        ArrayList<Double> result = new ArrayList<>();
        for (ArrayList<Expense> categorisedExpenses : data) {
            result.add(getExpenditureForCategory(categorisedExpenses));
        }
        return result;
    }

    public List<ArrayList<Double>> getPeriodicCategoricalExpenses() {
        return periodicCategoricalExpenses;
    }

    private static boolean hasInterval (Timestamp validDate, Timestamp endDate, BudgetPeriod period) {
        return validDate.isBefore(endDate) && (validDate.plus(period.getPeriod())).isBefore(endDate);
    }

    public List<Timestamp> getDates() {
        return dates;
    }

    public List<Double> getPeriodicTotalExpenditure() {
        return periodicTotalExpenditure;
    }

    public List<Double> getPeriodicBudgetLimits() {
        return periodicBudgetLimits;
    }

    public boolean isBudgetLimitMode() {
        return budgetLimitMode;
    }

    public String toString () {
        return String.format("%s\n%s", getTitle(), getPeriodicTotalExpenditure());
    }
}



