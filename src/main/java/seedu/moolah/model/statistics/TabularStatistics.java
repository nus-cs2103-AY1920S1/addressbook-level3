package seedu.moolah.model.statistics;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;

import seedu.moolah.model.expense.Category;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.ui.statistics.StatisticsRegionFactory;
import seedu.moolah.ui.statistics.TableViewRegionFactory;

/**
 * Represents the Statistics class that provides a table as its Visual Representation method
 */
public class TabularStatistics implements Statistics {

    private Timestamp firstStartDate;

    private Timestamp firstEndDate;

    private Timestamp secondStartDate;

    private Timestamp secondEndDate;

    private int categorySize;

    private ObservableList<Expense> expenses;

    //after population

    private List<FiveElementTableEntry> unionDifferenceTable;

    private String title;

    public TabularStatistics(ObservableList<Expense> expenses,
                              Timestamp firstStartDate, Timestamp firstEndDate,
                              Timestamp secondStartDate, Timestamp secondEndDate) {

        this.expenses = expenses;
        this.firstStartDate = firstStartDate;
        this.firstEndDate = firstEndDate;
        this.secondStartDate = secondStartDate;
        this.secondEndDate = secondEndDate;
        this.categorySize = Category.getNumValidCategory();
    }

    TabularStatistics(ObservableList<Expense> expenses,
                      Timestamp firstStartDate, Timestamp firstEndDate) {

        this.expenses = expenses;
        this.firstStartDate = firstStartDate;
        this.firstEndDate = firstEndDate;
        this.categorySize = Category.getNumValidCategory();
    }

    public List<FiveElementTableEntry> getUnionDifferenceTable() {
        return unionDifferenceTable;
    }

    /**
     * Gathers the data to be used for the elements of the table
     */
    private void generateTableData() {
        ArrayList<ArrayList<Expense>> firstData = extractRelevantExpenses(firstStartDate, firstEndDate);
        ArrayList<ArrayList<Expense>> secondData = extractRelevantExpenses(secondStartDate, secondEndDate);

        String title = String.format("Statistics Summary: Comparing %s to %s with %s to %s\n",
                firstStartDate.showDate(), firstEndDate.showDate(),
                secondStartDate.showDate(), secondEndDate.showDate());

        ArrayList<ThreeElementTableEntry> firstTable = createEmptyTableWithoutPercentage();
        ArrayList<ThreeElementTableEntry> secondTable = createEmptyTableWithoutPercentage();
        ArrayList<ThreeElementTableEntry> unionTable = createEmptyTableWithoutPercentage();

        convertDataToFigures(firstData, firstTable);
        convertDataToFigures(secondData, secondTable);
        List<ThreeElementTableEntry> differenceTable = secondMinusFirst(firstTable, secondTable);


        Timestamp overlapStartDate;
        Timestamp overlapEndDate;

        if (firstStartDate.isBefore(secondStartDate)) {
            overlapEndDate = firstEndDate;
            overlapStartDate = secondStartDate;
        } else {
            overlapEndDate = secondEndDate;
            overlapStartDate = firstStartDate;
        }

        ArrayList<ArrayList<Expense>> unionData = extractRelevantExpenses(overlapStartDate, overlapEndDate);
        convertDataToFigures(unionData, unionTable);

        this.unionDifferenceTable = combine(unionTable, differenceTable);

        this.setTitle(title);
    }

    /**
     * Creates an empty table
     *
     * @return An list of table entries
     */
    private ArrayList<ThreeElementTableEntry> createEmptyTableWithoutPercentage() {
        ArrayList<ThreeElementTableEntry> table = new ArrayList<>();
        for (int i = 0; i < Category.getNumValidCategory() + 1; i++) {
            table.add(ThreeElementTableEntry.createEmptyEntry());
        }
        return table;
    }

    /**
     * Creates a new table containing the differences in the two tables of basic statistics
     * @param firstTable The basic statistics of the first time period
     * @param secondTable The basic statistics of the second time period
     * @return A table containing the differences in the two tables of basic statistics
     */
    private ArrayList<ThreeElementTableEntry> secondMinusFirst(ArrayList<ThreeElementTableEntry> firstTable,
                                                               ArrayList<ThreeElementTableEntry> secondTable) {
        ArrayList<ThreeElementTableEntry> result = createEmptyTableWithoutPercentage();
        int rowSize = firstTable.size();
        for (int i = 0; i < rowSize; i++) {
            ThreeElementTableEntry firstInput = firstTable.get(i);
            ThreeElementTableEntry secondInput = secondTable.get(i);
            ThreeElementTableEntry difference = secondInput.minus(firstInput);
            result.set(i, difference);
        }
        return result;
    }


    /**
     * Extracts the expenses that are between the 2 dates
     *
     * @return A list of categorised expenses according to their categories
     */
    ArrayList<ArrayList<Expense>> extractRelevantExpenses(Timestamp startDate, Timestamp endDate) {
        ArrayList<ArrayList<Expense>> data = new ArrayList<>();
        for (int i = 0; i <= categorySize; i++) {
            data.add(new ArrayList<>());
        }

        for (Expense expense : expenses) {
            Timestamp date = expense.getTimestamp();
            if (date.compareDateTo(startDate) != -1 && date.compareDateTo(endDate) != 1) {
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
                                      ArrayList<ThreeElementTableEntry> table) {

        table.set(categorySize, new ThreeElementTableEntry("TOTAL", 0, 0));
        ThreeElementTableEntry entryForTotal = table.get(categorySize);

        for (int i = 0; i < categorySize; i++) {
            ArrayList<Expense> categoryStats = data.get(i);

            double categoricalTotal = 0;
            int entryNumber = 0;
            for (Expense expense : categoryStats) {
                categoricalTotal += Double.parseDouble(expense.getPrice().value);
                entryNumber++;
            }
            ThreeElementTableEntry changes = new ThreeElementTableEntry(
                    Category.getValidCategories().get(i), categoricalTotal, entryNumber);
            table.set(i, changes);
            entryForTotal = entryForTotal.add(changes);
        }
        table.set(categorySize, entryForTotal);

    }

    /**
     * Iterates through each row in both the union table and the difference table and create a combined table
     * @param unionTable The union table
     * @param differenceTable The difference table
     */
    private static List<FiveElementTableEntry> combine(List<ThreeElementTableEntry> unionTable,
                                                       List<ThreeElementTableEntry> differenceTable) {
        ArrayList<FiveElementTableEntry> result = new ArrayList<>();
        for (int i = 0; i < unionTable.size(); i++) {
            result.add(FiveElementTableEntry.combine(unionTable.get(i), differenceTable.get(i)));
        }
        return result;
    }

    public String toString() {
        return String.format("%s\n%s", getTitle(), getUnionDifferenceTable());
    }


    /**
     * Creates an empty table for testing purposes.
     */
    public static List<FiveElementTableEntry> createEmptyTableWithTotal() {
        List<FiveElementTableEntry> headers = new ArrayList<>();
        for (Category category : Category.getValidCategories()) {
            headers.add(FiveElementTableEntry.createEmptyWithName(category.getCategoryName()));
        }
        headers.add(FiveElementTableEntry.createEmptyWithName("TOTAL"));
        return headers;
    }

    /**
     * Replaces exactly one row in the table
     * @param table Table containing all data to be put onto TableView
     * @param entry Row that requires changing
     */
    private static List<FiveElementTableEntry> replaceWith(List<FiveElementTableEntry> table,
                                                           FiveElementTableEntry entry) {
        List<FiveElementTableEntry> result = new ArrayList<>();
        for (FiveElementTableEntry rows : table) {
            if (entry.getName().equals(rows.getName())) {
                result.add(entry);
            } else {
                result.add(rows);
            }
        }
        return result;
    }

    /**
     * Replaces the rows of the table if there are table entries that differ from it
     * @param table Table containing all data to be put onto TableView
     * @param rows Rows that require changing
     */
    public static List<FiveElementTableEntry> replaceWith(List<FiveElementTableEntry> table,
                                                          FiveElementTableEntry ... rows) {
        List<FiveElementTableEntry> result = table;
        for (FiveElementTableEntry row: rows) {
            result = replaceWith(result, row);
        }
        return result;
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
        generateTableData();
    }

    @Override
    public StatisticsRegionFactory createFactory() {
        return new TableViewRegionFactory(unionDifferenceTable, title);
    }
}



