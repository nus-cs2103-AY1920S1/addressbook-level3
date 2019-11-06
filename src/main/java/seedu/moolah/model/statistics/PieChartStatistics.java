package seedu.moolah.model.statistics;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Category;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.expense.Timestamp;

/**
 * Represents the Statistics class that provides a pie chart as its Visual Representation method
 */
public class PieChartStatistics extends Statistics {


    private List<String> formattedCategories;

    private List<Double> formattedPercentages;

    private List<Category> budgetCategories;

    private Timestamp startDate;

    private Timestamp endDate;

    private ObservableList<Expense> expenses;

    private PieChartStatistics(ObservableList<Expense> expenses, List<Category> validCategories,
                               Timestamp startDate, Timestamp endDate) {

        super(expenses, validCategories);
        this.startDate = startDate;
        this.endDate = endDate;
        this.expenses = getExpenses();
    }

    /**
     * A method to practise defensive programming
     * @param expenses List of expenses
     * @param validCategories List of allowed categories in MooLah
     * @param startDate The start date of the tracking period
     * @param endDate The end date of the tracking period
     */
    private static PieChartStatistics verify(ObservableList<Expense> expenses, List<Category> validCategories,
                               Timestamp startDate, Timestamp endDate) {
        requireNonNull(startDate);
        requireNonNull(endDate);
        requireNonNull(expenses);
        requireNonNull(validCategories);

        return new PieChartStatistics(expenses, validCategories, startDate, endDate);
    }

    /**
     * Creates a PieChartStatistics object with all the required information filled in its attributes
     * @param validCategories List of allowed categories in MooLah
     * @param startDate The start date of the tracking period
     * @param endDate The end date of the tracking period
     * @param primaryBudget The primary budget whose statistics is taken
     */
    public static PieChartStatistics run(List<Category>
            validCategories, Timestamp startDate, Timestamp endDate, Budget primaryBudget) {

        requireNonNull(primaryBudget);

        boolean isStartPresent = startDate != null;
        boolean isEndPresent = endDate != null;

        if (!isStartPresent && !isEndPresent) {
            startDate = primaryBudget.getWindowStartDate();
            endDate = primaryBudget.getWindowEndDate();
        } else if (isStartPresent && !isEndPresent) {
            endDate = startDate.createForwardTimestamp(primaryBudget.getBudgetPeriod()).minusDays(1);
        } else if (!isStartPresent) {
            startDate = endDate.createBackwardTimestamp(primaryBudget.getBudgetPeriod()).plusDays(1);
        }

        PieChartStatistics statistics = PieChartStatistics.verify(primaryBudget.getExpenses(),
                validCategories, startDate, endDate);
        statistics.generatePieChartData();
        return statistics;
    }



    /**
     * Gathers the data to be used for the elements of the PieChart
     */
    private void generatePieChartData() {

        this.budgetCategories = collateBudgetCategories(expenses);

        ArrayList<ArrayList<Expense>> expensesInCategories = extractRelevantExpenses(startDate, endDate);
        String title = String.format("Statistics Summary from %s to %s\n", startDate.showDate(), endDate.showDate());

        ArrayList<Double> percentages = new ArrayList<>();
        ArrayList<Integer> numberOfEntries = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();

        for (Category category : budgetCategories) {
            percentages.add(0.0);
            numberOfEntries.add(0);
            names.add(category.getCategoryName());
        }

        generatePercentages(expensesInCategories, percentages, numberOfEntries, names, title);


    }

    /**
     * Extracts the expenses that are between the 2 dates
     *
     * @return A list of categorised expenses according to their categories
     */
    private ArrayList<ArrayList<Expense>> extractRelevantExpenses(Timestamp startDate, Timestamp endDate) {
        ArrayList<ArrayList<Expense>> expensesInCategories = new ArrayList<>();

        for (int i = 0; i < budgetCategories.size(); i++) {
            expensesInCategories.add(new ArrayList<>());
        }

        for (Expense expense : expenses) {
            Timestamp date = expense.getTimestamp();


            if (date.compareDateTo(startDate) != -1 && date.compareDateTo(endDate) != 1) {

                int index = budgetCategories.indexOf(expense.getCategory());
                expensesInCategories.get(index).add(expense);

            }
        }
        return expensesInCategories;
    }

    /**
     * Returns a list of categories used among all expenses. Meant for PieChart usage
     */
    private static List<Category> collateBudgetCategories(ObservableList<Expense> expenses) {
        Set<Category> categories = new HashSet<>();
        for (Expense expense: expenses) {
            categories.add(expense.getCategory());
        }

        return new ArrayList<>(categories);
    }

    /**
     * Fills in the data to be passed to a GUI
     * @param data Expenses grouped together under their Categories
     * @param percentages List of all percentages under each category
     * @param numberOfEntries List of number of entries under each category
     * @param names List of all names to be shown in the legend representing the category
     * @param titleWithPeriod String containing the period of time the statistics is taken
     */
    private void generatePercentages(ArrayList<ArrayList<Expense>> data, ArrayList<Double> percentages,
                                     ArrayList<Integer> numberOfEntries, ArrayList<String> names,
                                     String titleWithPeriod) {

        double totalAmount = 0.0;




        for (int i = 0; i < percentages.size(); i++) {

            ArrayList<Expense> categoryStats = data.get(i);


            for (Expense expense : categoryStats) {
                double oldCategoricalTotal = percentages.get(i);
                double price = Double.parseDouble(expense.getPrice().value);
                percentages.set(i, oldCategoricalTotal + price);
                totalAmount += price;
                int oldNumberOfEntries = numberOfEntries.get(i);
                numberOfEntries.set(i, oldNumberOfEntries + 1);


            }
        }


        this.formattedCategories = new ArrayList<>();
        this.formattedPercentages = new ArrayList<>();


        for (int i = 0; i < percentages.size(); i++) {

            double categoricalTotal = percentages.get(i);
            double roundedResult = Math.round(categoricalTotal * 10000 / totalAmount) / 100.0;


            if (roundedResult != 0.00) {
                String oldName = names.get(i);
                formattedCategories.add(String.format("%s(%.2f%%)", oldName, roundedResult));
                formattedPercentages.add(roundedResult);

            }
        }

        setTitle(String.format("%s\nTotal amount: $%.2f", titleWithPeriod, totalAmount));



    }

    /**
     * Returns the formatted validCategories to be used as labels for the PieChart
     */
    public List<String> getFormattedCategories() {
        return formattedCategories;
    }

    public List<Double> getFormattedPercentages() {
        return formattedPercentages;
    }

    public String toString() {
        return String.format("%s\n%s\n%s", getTitle(), getFormattedCategories(), getFormattedPercentages());
    }
}






