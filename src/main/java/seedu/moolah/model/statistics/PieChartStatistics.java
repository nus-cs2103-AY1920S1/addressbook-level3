package seedu.moolah.model.statistics;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.moolah.model.expense.Category;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.ui.statistics.PieChartRegionFactory;

/**
 * Represents the Statistics class that provides a pie chart as its Visual Representation method
 */
public class PieChartStatistics implements Statistics {

    private Timestamp startDate;

    private Timestamp endDate;

    private ObservableList<Expense> expenses;

    //after population

    private String title;

    private List<String> formattedCategories;

    private List<Double> formattedPercentages;

    private List<Category> budgetCategories;


    /**
     * Creates a PieChartStatistics object with all the required information filled in its attributes
     * @param expenses The expenses tracked under the primary budget
     * @param startDate The start date of the tracking period
     * @param endDate The end date of the tracking period
     */
    public PieChartStatistics(ObservableList<Expense> expenses,
                               Timestamp startDate, Timestamp endDate) {

        requireNonNull(expenses);
        requireNonNull(startDate);
        requireNonNull(endDate);
        this.expenses = expenses;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    @Override
    public void populateData() {
        generatePieChartData();
    }

    @Override
    public PieChartRegionFactory createFactory() {
        return new PieChartRegionFactory(formattedCategories, formattedPercentages, title);
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

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }


}






