package seedu.address.model.statistics;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.util.logging.Filter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.person.Category;
import seedu.address.model.person.CategoryList;
import seedu.address.model.person.Entry;
import seedu.address.model.person.Expense;
import seedu.address.model.person.Income;
import seedu.address.model.person.SortSequence;
import seedu.address.model.person.SortType;
import seedu.address.model.util.EntryComparator;

/**
 * Contains the entries for the current Month in the idea.
 */
public class MonthList {

    private ObservableMap<Integer, DailyList> dailyRecord;
    private CategoryList listOfCategories;
    private FilteredList<Expense> filteredListForExpense;
    private FilteredList<Income> filteredListForIncome;
    private SortType sortByTime = new SortType("Time");
    private SortSequence sortByDesc = new SortSequence("Descending");
    private Month month;
    private int year;
    private double monthExpenseTotal;

    /**
     * Contains the FilteredList of entries for the month.
     */
    public MonthList(CategoryList listOfCategories, FilteredList<Expense> filteredListOfExpenses,
                     FilteredList<Income> filteredListOfIncome, Month month, int year) {
        this.dailyRecord = FXCollections.observableHashMap();
        this.filteredListForExpense = filteredListOfExpenses;
        this.filteredListForIncome = filteredListOfIncome;
        this.listOfCategories = listOfCategories;
        this.month = month;
        this.year = year;
        initRecords();
    }

    private void initRecords() {
        createObsMap();
    }

    /**
     * Creates an ObservableMap of Dailylists. //TODO
     */
    private void createObsMap() {
        for (int i = 0; i < 31; i++) {
            try {
                LocalDate.of(year, month.getValue(), i);
            } catch (DateTimeException e) {
                continue;
            }
            FilteredList<Expense> filteredExpenseByDay = new FilteredList<>(this.filteredListForExpense,
                    new EntryContainsDayPredicate(i));
            FilteredList<Income> filteredIncomeByDay = new FilteredList<Income>(this.filteredListForIncome,
                    new EntryContainsDayPredicate(i));
//            FilteredList<Income> filteredIncomeByDay = new FilteredList<Income>(this.filteredListForIncome, new EntryContainsDayPredicate(i));
            DailyList dailyList = new DailyList(this.listOfCategories, filteredExpenseByDay, filteredIncomeByDay,
                    LocalDate.of(year, month.getValue(), i));
            monthExpenseTotal = monthExpenseTotal + dailyList.getTotalExpense();
            dailyRecord.put(i, dailyList);
        }
    }

    public void printSize() {
        System.out.println(filteredListForExpense.size() + "MONTH ( ");
    }

    public void printTest() {
        System.out.println(filteredListForExpense);
    }


    public Double updateListOfStats(Category cat) {
        double newTotal = 0.00;
        for (DailyList dailyLists : dailyRecord.values()) {
            newTotal = newTotal + dailyLists.updateListOfStats(cat);
        }
        return (newTotal);
    }
}
