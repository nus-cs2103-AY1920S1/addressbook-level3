package seedu.moolah.model.statistics;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.budget.BudgetPeriod;
import seedu.moolah.model.expense.Category;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.ui.statistics.LineChartBudgetRegionFactory;
import seedu.moolah.ui.statistics.LineChartCategoriesRegionFactory;
import seedu.moolah.ui.statistics.StatisticsRegionFactory;

/**
 * Represents the Statistics class that provides a trend line as its Visual Representation method.
 */
public class TrendStatistics implements Statistics {

    public static final int INTERVAL_COUNT = 34;

    private Timestamp startDate;

    private Timestamp endDate;

    private Budget primaryBudget;

    private boolean budgetLimitMode;

    private ObservableList<Expense> expenses;

    //after population

    private List<Timestamp> dates = new ArrayList<>();

    private List<Double> periodicTotalExpenditures = new ArrayList<>();

    private List<Double> periodicBudgetLimits = new ArrayList<>();

    private List<ArrayList<Double>> periodicCategoricalExpenses = new ArrayList<>();

    private String title;

    public TrendStatistics(Timestamp startDate, Timestamp endDate, Budget primaryBudget, boolean isBudgetMode) {

        requireNonNull(startDate);
        requireNonNull(endDate);
        requireNonNull(primaryBudget);

        this.expenses = primaryBudget.getExpenses();
        this.startDate = startDate;
        this.endDate = endDate;
        this.primaryBudget = primaryBudget;
        this.budgetLimitMode = isBudgetMode;
        if (!budgetLimitMode) {
            for (int i = 0; i < Category.getNumValidCategory(); i++) {
                periodicCategoricalExpenses.add(new ArrayList<>());
            }
        }
    }

    /**
     * Gathers the data to be used for the elements of the trend line
     */
    private void generateTrendLine() {
        ArrayList<ArrayList<ArrayList<Expense>>> data = new ArrayList<>();


        BudgetPeriod period = primaryBudget.getBudgetPeriod();
        Timestamp windowStartDate = primaryBudget.getWindowStartDate();

        Timestamp validDate = findClosestWindowStartDate(startDate, windowStartDate, period);

        int intervalCount = 0;

        while (hasInterval(validDate, endDate, period) && intervalCount < INTERVAL_COUNT) {
            Timestamp localStartDate = validDate;

            Timestamp nextLocalStartDate = localStartDate.plus(period.getPeriod());
            Timestamp localEndDate = nextLocalStartDate.minusDays(1);

            ArrayList<ArrayList<Expense>> categorisedPeriodicExpenses =
                    getCategorisedPeriodicExpenses(localStartDate, localEndDate);

            if (budgetLimitMode) {
                double periodicTotalExpenditure = getTotalExpenditure(categorisedPeriodicExpenses);
                this.periodicTotalExpenditures.add(periodicTotalExpenditure);
                periodicBudgetLimits.add(primaryBudget.getAmount().getAsDouble());
            } else {
                List<Double> categorisedExpenditureAtPeriod = getCategoricalExpenditure(categorisedPeriodicExpenses);
                flatMapAdd(categorisedExpenditureAtPeriod);
            }
            dates.add(localStartDate);
            intervalCount++;
            validDate = nextLocalStartDate;

        }
        //shown to the user, should be exactly what they counted
        this.setTitle(String.format("Periodic trendline from %s to %s in the unit of %ss",
                startDate.showDate(), endDate.showDate(),
                period));
    }

    /**
     * Finds the window start date that is closest to the start date of interest
     * @param startDate The start date for the trend line
     * @param windowStartDate The start date for the current winndow
     * @param period The duration of the window
     */
    private Timestamp findClosestWindowStartDate(Timestamp startDate, Timestamp windowStartDate, BudgetPeriod period) {
        Timestamp changedWindowDate = windowStartDate.createBackwardTimestamp(period);
        while (windowStartDate.dateIsAfter(startDate) && changedWindowDate.dateIsAfter(startDate)) {
            windowStartDate = changedWindowDate;
            changedWindowDate = changedWindowDate.createBackwardTimestamp(period);
        }
        //edge case
        if (changedWindowDate.getDate().equals(startDate.getDate())) {
            windowStartDate = changedWindowDate;
        }

        return windowStartDate;
    }

    /**
     * Adds the periodic expenditure from each category to its respective category list
     * @param categorisedExpenditureAtPeriod A list of total expenditure for each category
     */
    private void flatMapAdd(List<Double> categorisedExpenditureAtPeriod) {
        for (int i = 0; i < categorisedExpenditureAtPeriod.size(); i++) {
            ArrayList<Double> categoricalExpenses = periodicCategoricalExpenses.get(i);
            categoricalExpenses.add(categorisedExpenditureAtPeriod.get(i));
        }
    }

    private ArrayList<ArrayList<Expense>> getCategorisedPeriodicExpenses(Timestamp startDate, Timestamp endDate) {
        TabularStatistics statistics = new TabularStatistics(expenses, startDate, endDate);
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
        return periodicTotalExpenditures;
    }

    public List<Double> getPeriodicBudgetLimits() {
        return periodicBudgetLimits;
    }

    public boolean isBudgetLimitMode() {
        return budgetLimitMode;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public String toString() {
        return String.format("%s\n%s", getTitle(), getPeriodicTotalExpenditure());
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void populateData() {
        generateTrendLine();
    }

    @Override
    public StatisticsRegionFactory createFactory() {
        if (budgetLimitMode) {
            return new LineChartBudgetRegionFactory(dates, periodicTotalExpenditures, periodicBudgetLimits, title);
        } else {
            return new LineChartCategoriesRegionFactory(dates, periodicCategoricalExpenses, title);
        }
    }
}



