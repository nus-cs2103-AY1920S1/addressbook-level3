package seedu.address.model.statistics;

import static java.util.Objects.requireNonNull;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.category.Category;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Timestamp;

/**
 * Represents the Statistics class that provides a Table as its Visual Representation method
 */
public class TabularStatistics extends Statistics {

    private Timestamp firstStartDate;

    private Timestamp firstEndDate;

    private Timestamp secondStartDate;

    private Timestamp secondEndDate;

    private List<TableEntry> differenceTable;

    private int categorySize;

    private ObservableList<Expense> expenses;

    private TabularStatistics(ObservableList<Expense> expenses, List<Category> validCategories,
                              Timestamp firstStartDate, Timestamp firstEndDate,
                              Timestamp secondStartDate, Timestamp secondEndDate) {

        super(expenses, validCategories);
        this.firstStartDate = firstStartDate;
        this.firstEndDate = firstEndDate;
        this.secondStartDate = secondStartDate;
        this.secondEndDate = secondEndDate;
        this.categorySize = getCategorySize();
        this.expenses = getExpenses();
    }

    public List<TableEntry> getDifferenceTable() {
        return differenceTable;
    }

    /**
     * Compares the difference in basic statistics across 2 time periods
     * @param firstStartDate
     * @param secondStartDate
     * @param period
     */
    public static TabularStatistics run(ObservableList<Expense> expenses, List<Category> validCategories,
                                        Timestamp firstStartDate, Timestamp secondStartDate, Period period) {
        requireNonNull(firstStartDate);
        requireNonNull(secondStartDate);
        requireNonNull(period);

        Timestamp firstEndDate = new Timestamp(firstStartDate.getFullTimestamp().plus(period));
        Timestamp secondEndDate = new Timestamp(secondStartDate.getFullTimestamp().plus(period));

        TabularStatistics statistics = new TabularStatistics(expenses, validCategories,
                firstStartDate, firstEndDate,
                secondStartDate, secondEndDate);
        statistics.generateTableData();
        return statistics;
    }

    /**
     * Gathers the data to be used for the elements of the Table
     */
    private void generateTableData() {
        ArrayList<ArrayList<Expense>> firstData = extractRelevantExpenses(firstStartDate, firstEndDate);
        ArrayList<ArrayList<Expense>> secondData = extractRelevantExpenses(secondStartDate, secondEndDate);

        String title = String.format("Statistics Summary: Comparing %s to %s with %s to %s\n",
                firstStartDate, firstEndDate, secondStartDate, secondEndDate);

        ArrayList<TableEntry> firstTable = createEmptyTableWithoutPercentage();
        ArrayList<TableEntry> secondTable = createEmptyTableWithoutPercentage();

        convertDataToFigures(firstData, firstTable);
        convertDataToFigures(secondData, secondTable);
        this.differenceTable = secondMinusFirst(firstTable, secondTable);
        this.setTitle(title);
    }

    /**
     * Creates an empty table
     *
     * @return An list of table entries
     */
    private ArrayList<TableEntry> createEmptyTableWithoutPercentage() {
        ArrayList<TableEntry> table = new ArrayList<>();
        for (int i = 0; i < getValidCategories().size() + 1; i++) {
            table.add(TableEntry.createEmptyEntry());
        }
        return table;
    }


    /**
     * Creates a new table containing the differences in the two tables of basic statistics
     * @param firstTable The basic statistics of the first time period
     * @param secondTable The basic statistics of the second time period
     * @return A table containing the differences in the two tables of basic statistics
     */
    private ArrayList<TableEntry> secondMinusFirst(ArrayList<TableEntry> firstTable,
                                                   ArrayList<TableEntry> secondTable) {
        ArrayList<TableEntry> result = createEmptyTableWithoutPercentage();
        int rowSize = firstTable.size();
        for (int i = 0; i < rowSize; i++) {
            TableEntry firstInput = firstTable.get(i);
            TableEntry secondInput = secondTable.get(i);
            TableEntry difference = secondInput.minus(firstInput);
            result.set(i, difference);
        }
        return result;
    }


    /**
     * Extracts the expenses that are between the 2 dates
     *
     * @return A list of categorised expenses according to their categories
     */
    private ArrayList<ArrayList<Expense>> extractRelevantExpenses(Timestamp startDate, Timestamp endDate) {
        ArrayList<ArrayList<Expense>> data = new ArrayList<>();
        for (int i = 0; i <= categorySize; i++) {
            data.add(new ArrayList<>());
        }

        for (Expense expense : expenses) {
            Timestamp date = expense.getTimestamp();
            if (date.compareTo(startDate) != -1 && date.compareTo(endDate) != 1) {
                data.get(categorySize).add(expense);
                int index = Category.indexOfInList(expense.getCategory());
                data.get(index).add(expense);
            }
        }
        return data;
    }






    /**
     * Fills in the table with calculations from the expenses
     */
    private void convertDataToFigures(ArrayList<ArrayList<Expense>> data,
                                      ArrayList<TableEntry> table) {

        table.set(categorySize, new TableEntry("Total", 0, 0));
        TableEntry entryForTotal = table.get(categorySize);

        for (int i = 0; i < categorySize; i++) {
            ArrayList<Expense> categoryStats = data.get(i);

            double categoricalTotal = 0;
            int entryNumber = 0;
            for (Expense expense : categoryStats) {
                categoricalTotal += Double.parseDouble(expense.getPrice().value);
                entryNumber++;
            }
            TableEntry changes = new TableEntry(getValidCategories().get(i), categoricalTotal, entryNumber);
            table.set(i, changes);
            entryForTotal = entryForTotal.add(changes);
        }
        table.set(categorySize, entryForTotal);

    }


}



