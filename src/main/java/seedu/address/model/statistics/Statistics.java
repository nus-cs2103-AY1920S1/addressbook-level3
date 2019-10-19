package seedu.address.model.statistics;

import static java.util.Objects.requireNonNull;

import java.time.Period;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.transformation.FilteredList;
import seedu.address.model.category.Category;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Timestamp;



/**
 * Represents Statistics in Moolah.
 */
public class Statistics {

    public static final String MESSAGE_CONSTRAINTS_END_DATE = "Start date must be before end date.";

    private FilteredList<Expense> expenses;

    private List<Category> categories;

    private StringBuilder statsBuilder = new StringBuilder();

    /**
     * Creates a Statistics object
     * @param expenses A list of expenses in the current budget
     * @param categories A list of tags used among all expenses
     */
    private Statistics(FilteredList<Expense> expenses, List<Category> categories) {
        requireNonNull(categories);
        this.expenses = expenses;
        this.categories = categories;
    }

    /**
     * Returns the lists of all expenses in the current budget
     */
    public FilteredList<Expense> getExpenses() {
        return expenses;
    }

    /**
     * Returns the tags used among all expenses
     */
    public List<Category> getCategories() {
        return categories;
    }


    /**
     * A factory method for creating a Statistics object
     * @param expenses A list of expenses in the current budget
     * @return Statistics object
     */
    public static Statistics startStatistics(FilteredList<Expense> expenses) {
        requireNonNull(expenses);
        List<Category> categories = collateTagNames(expenses);
        return new Statistics(expenses, categories);
    }

    /**
     * Calculates the statistics based on the command word given by the user
     * @param command The command word
     * @param startDate The starting date
     * @param endDate The ending date
     * @return The calculated statistics in the form of a StringBuilder
     */
    public StringBuilder calculateStats(String command, Timestamp startDate, Timestamp endDate, Period period) {
        switch (command) {
        case "stats":
            basicStats(startDate, endDate);
            break;
        case "statscompare":
            compareStats(startDate, endDate, period);
            break;
        default:
            break;
        }
        return statsBuilder;
    }

    /**
     * Compares the difference in basic statistics across 2 time periods
     * @param firstStartDate
     * @param secondStartDate
     * @param period
     */
    private void compareStats(Timestamp firstStartDate, Timestamp secondStartDate, Period period) {
        requireNonNull(firstStartDate);
        requireNonNull(secondStartDate);
        requireNonNull(period);

        Timestamp firstEndDate = new Timestamp(firstStartDate.timestamp.plus(period));
        Timestamp secondEndDate = new Timestamp(secondStartDate.timestamp.plus(period));

        ArrayList<ArrayList<Expense>> firstData = extractRelevantExpenses(firstStartDate, firstEndDate);
        ArrayList<ArrayList<Expense>> secondData = extractRelevantExpenses(secondStartDate, secondEndDate);

        statsBuilder.append("Statistics Summary\n");
        statsBuilder.append(String.format("Comparing %s to %s\n", firstStartDate, firstEndDate));
        statsBuilder.append(String.format("With %s to %s\n", secondStartDate, secondEndDate));

        ArrayList<ArrayList<Object>> firstTable = createEmptyTableWithoutPercentage();
        ArrayList<ArrayList<Object>> secondTable = createEmptyTableWithoutPercentage();

        List<String> headers = List.of("Category: ",
                "Amount Spent ($): ",
                "Number of entries: ");

        convertDataToFigures(firstData, firstTable, false);
        convertDataToFigures(secondData, secondTable, false);
        ArrayList<ArrayList<Object>> result = secondMinusFirst(firstTable, secondTable);
        convertTableToString(result, headers);
    }

    /**
     * Creates a new table containing the differences in the two tables of basic statistics
     * @param firstTable The basic statistics of the first time period
     * @param secondTable The basic statistics of the second time period
     * @return A table containing the differences in the two tables of basic statistics
     */
    private ArrayList<ArrayList<Object>> secondMinusFirst(ArrayList<ArrayList<Object>> firstTable,
                                                          ArrayList<ArrayList<Object>> secondTable) {
        ArrayList<ArrayList<Object>> result = createEmptyTableWithoutPercentage();
        int rowSize = firstTable.size();
        int columnSize = firstTable.get(0).size();
        for (int i = 0; i < rowSize; i++) {
            ArrayList<Object> tableEntry = result.get(i);
            for (int j = 0; j < columnSize; j++) {
                Object firstInput = firstTable.get(i).get(j);
                Object secondInput = secondTable.get(i).get(j);
                tableEntry.set(j, handleInputType(firstInput, secondInput));
            }
        }
        return result;
    }

    /**
     * Handles the interaction between the first input and the second input by their type
     * @param firstInput The input from the first table
     * @param secondInput The input from the second table
     * @return The correct result to be placed into the result table
     */
    private Object handleInputType(Object firstInput, Object secondInput) {
        if (firstInput instanceof Category) {
            return firstInput;
        } else if (firstInput instanceof Double) {
            return (Double) secondInput - (Double) firstInput;
        } else if (firstInput instanceof Integer) {
            return (Integer) secondInput - (Integer) firstInput;
        } else {
            return null;
        }
    }

    /**
     * Calculates the basic statistics between 2 dates and stores the results in the statsBuilder field
     * @param startDate The starting date
     * @param endDate The ending date
     */
    private void basicStats(Timestamp startDate, Timestamp endDate) {
        requireNonNull(startDate);
        requireNonNull(endDate);

        ArrayList<ArrayList<Expense>> data = extractRelevantExpenses(startDate, endDate);


        statsBuilder.append("Statistics Summary\n");
        statsBuilder.append(String.format("From %s to %s\n", startDate, endDate));


        ArrayList<ArrayList<Object>> table = createEmptyTableWithPercentage();

        List<String> headers = List.of("Category: ",
                "Amount Spent ($): ",
                "Number of entries: ",
                "Percentage: ");
        convertDataToFigures(data, table, true);
        convertTableToString(table, headers);
    }

    /**
     * Creates an empty table
     *
     * @return An list of table entries
     */
    private ArrayList<ArrayList<Object>> createEmptyTableWithPercentage() {
        ArrayList<ArrayList<Object>> table = new ArrayList<>();
        for (int i = 0; i < categories.size() + 1; i++) {
            ArrayList<Object> tableEntry = new ArrayList<>();
            table.add(tableEntry);
            tableEntry.add(null);
            tableEntry.add(0.0);
            tableEntry.add(0);
            tableEntry.add(0.0);

        }
        return table;
    }

    /**
     * Creates an empty table
     *
     * @return An list of table entries
     */
    private ArrayList<ArrayList<Object>> createEmptyTableWithoutPercentage() {
        ArrayList<ArrayList<Object>> table = new ArrayList<>();
        for (int i = 0; i < categories.size() + 1; i++) {
            ArrayList<Object> tableEntry = new ArrayList<>();
            table.add(tableEntry);
            tableEntry.add(null);
            tableEntry.add(0.0);
            tableEntry.add(0);
        }
        return table;
    }


    /**
     * Fills in the table with calculations from the expenses
     */
    private void convertDataToFigures(ArrayList<ArrayList<Expense>> data,
                                      ArrayList<ArrayList<Object>> table, boolean hasFourColumns) {

        ArrayList<Object> entryForTotal = table.get(categories.size());
        entryForTotal.set(0, "Total");

        for (int i = 0; i < categories.size(); i++) {
            ArrayList<Expense> categoryStats = data.get(i);
            ArrayList<Object> tableEntry = table.get(i);

            double categoricalTotal = 0;
            int entryNumber = 0;
            for (Expense expense : categoryStats) {
                categoricalTotal += Double.parseDouble(expense.getPrice().value);
                entryNumber++;
            }

            tableEntry.set(0, categories.get(i));
            tableEntry.set(1, categoricalTotal);
            tableEntry.set(2, entryNumber);
            if (hasFourColumns) {
                tableEntry.set(3, categoricalTotal);
            }

            addToTableEntry(entryForTotal, categoricalTotal, entryNumber, hasFourColumns);
        }

        double totalAmount = (Double) entryForTotal.get(1);

        if (hasFourColumns) {
            for (int i = 0; i < categories.size() + 1; i++) {
                ArrayList<Object> tableEntry = table.get(i);
                double categoricalTotal = (Double) tableEntry.get(3);
                double roundedResult = Math.round(categoricalTotal * 10000 / totalAmount) / 100.0;
                tableEntry.set(3, roundedResult);
            }
        }

    }

    /**
     * Adds the new values to the existing values in the table entry
     */
    private void addToTableEntry(ArrayList<Object> entryForTotal,
                                 double categoricalTotal, int entryNumber, boolean hasFourColumns) {
        double oldTotal = (Double) entryForTotal.get(1);
        int oldCount = (Integer) entryForTotal.get(2);
        entryForTotal.set(1, oldTotal + categoricalTotal);
        entryForTotal.set(2, oldCount + entryNumber);
        if (hasFourColumns) {
            double updatedTotal = (Double) entryForTotal.get(1);
            entryForTotal.set(3, updatedTotal);
        }
    }

    /**
     * Prints out the table to the console such that table entry element comes after its header
     * @param table The filled table to be printed
     * @param headers The headers to label the table entries
     */
    private void convertTableToString(ArrayList<ArrayList<Object>> table, List<String> headers) {
        for (List<Object> tableEntry: table) {
            for (int i = 0; i < tableEntry.size(); i++) {
                statsBuilder.append(headers.get(i));
                statsBuilder.append(tableEntry.get(i));
                statsBuilder.append("\n");
            }
            statsBuilder.append("\n");
        }
    }

    /**
     * Extracts the expenses that are between the 2 dates
     *
     * @return A list of categorised expenses according to their categories
     */
    private ArrayList<ArrayList<Expense>> extractRelevantExpenses(Timestamp startDate, Timestamp endDate) {
        ArrayList<ArrayList<Expense>> data = new ArrayList<>();

        List<Category> categories = collateTagNames(expenses);
        for (int i = 0; i <= categories.size(); i++) {
            data.add(new ArrayList<>());
        }

        for (Expense expense : expenses) {
            Timestamp date = expense.getTimestamp();
            if (date.compareTo(startDate) != -1 && date.compareTo(endDate) != 1) {
                data.get(categories.size()).add(expense);
                for (Category category : new ArrayList<>(expense.getCategories())) {
                    int index = categories.indexOf(category);
                    data.get(index).add(expense);
                }

            }
        }
        return data;
    }

    /**
     * Returns a list of tags used among all expenses
     */
    private static List<Category> collateTagNames(FilteredList<Expense> expenses) {
        Set<Category> categories = new HashSet<>();
        for (Expense expense: expenses) {
            categories.addAll(expense.getCategories());
        }

        List<Category> result = new ArrayList<>(categories);
        return result;
    }


}
