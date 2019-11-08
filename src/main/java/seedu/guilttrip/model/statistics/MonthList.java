package seedu.guilttrip.model.statistics;

import java.time.Month;
import java.time.YearMonth;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.CategoryList;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.entry.Income;
import seedu.guilttrip.model.util.CategoryType;

/**
 * Contains the entries for the current Month in the idea.
 */
public class MonthList {

    private List<Category> listOfExpenseCategories;
    private List<Category> listOfIncomeCategories;
    private ObservableMap<Category, FilteredList<Expense>> mapOfExpenseCategories;
    private ObservableMap<Category, FilteredList<Income>> mapOfIncomeCategories;
    private ObservableList<DailyStatistics> listOfDailyStatistics;
    private ObservableMap<Integer, DailyList> mapOfDailyLists;
    private FilteredList<Expense> filteredListForExpense;
    private FilteredList<Income> filteredListForIncome;
    private YearMonth yearMonth;
    private double totalExpense;
    private double totalIncome;

    /**
     * Contains the FilteredList of entries for the month.
     */
    public MonthList(CategoryList listOfCategories, FilteredList<Expense> filteredListOfExpenses,
                     FilteredList<Income> filteredListOfIncome, Month month, int year) {
        this.listOfExpenseCategories = listOfCategories.getInternalListForOtherEntries();
        this.listOfIncomeCategories = listOfCategories.getInternalListForIncome();
        this.filteredListForExpense = new FilteredList<>(filteredListOfExpenses,
                new EntryContainsMonthYearPredicate(month.getValue(), year));
        this.filteredListForIncome =  new FilteredList<>(filteredListOfIncome,
                new EntryContainsMonthYearPredicate(month.getValue(), year));
        mapOfExpenseCategories = FXCollections.observableHashMap();
        mapOfIncomeCategories = FXCollections.observableHashMap();
        listOfDailyStatistics = FXCollections.observableArrayList();
        mapOfDailyLists = FXCollections.observableHashMap();
        this.yearMonth = YearMonth.of(year, month);
        totalExpense = 0.00;
        totalIncome = 0.00;
        initRecords();
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public FilteredList<Expense> getFilteredListForExpense() {
        return this.filteredListForExpense;
    }

    public FilteredList<Income> getFilteredListForIncome() {
        return this.filteredListForIncome;
    }

    /**
     * Initiates the MonthList Record in guilttrip.
     */
    private void initRecords() {
        for (int i = 0; i < this.listOfExpenseCategories.size(); i++) {
            Category toFilterCategory = this.listOfExpenseCategories.get(i);
            FilteredList<Expense> filteredByCategory = new FilteredList<Expense>(this.filteredListForExpense,
                    new EntryContainsCategoryPredicate(toFilterCategory));
            mapOfExpenseCategories.put(toFilterCategory, filteredByCategory);
        }

        for (int k = 0; k < this.listOfIncomeCategories.size(); k++) {
            Category toFilterCategory = this.listOfIncomeCategories.get(k);
            FilteredList<Income> filteredByCategory = new FilteredList<Income> (this.filteredListForIncome,
                    new EntryContainsCategoryPredicate(toFilterCategory));
            mapOfIncomeCategories.put(toFilterCategory, filteredByCategory);
        }

        for (int j = 1; j <= this.yearMonth.lengthOfMonth(); j++) {
            FilteredList<Expense> dailyExpense = new FilteredList<Expense> (filteredListForExpense,
                    new EntryContainsDayPredicate(j, yearMonth.getMonthValue(), yearMonth.getYear()));
            FilteredList<Income> dailyIncome = new FilteredList<Income> (filteredListForIncome,
                    new EntryContainsDayPredicate(j, yearMonth.getMonthValue(), yearMonth.getYear()));
            DailyList dailyListOfMonth = new DailyList(dailyExpense, dailyIncome, j,
                    this.yearMonth.getMonth(), this.yearMonth.getYear());
            this.mapOfDailyLists.put(j, dailyListOfMonth);
        }

        for (int i = 1; i <= yearMonth.lengthOfMonth(); i++) {
            DailyList dl = this.mapOfDailyLists.get(i);
            DailyStatistics statsCalculate = dl.calculateBarChart();
            this.listOfDailyStatistics.add(statsCalculate);
        }
    }

    /**
     * Calculates the stats for the particular category for the particular month.
     *
     * @param cat is the category to calculate the statistics for.
     * @return calculatedTotal, the total amount spent for that particular category in the month.
     */
    public Double updateListOfStats(Category cat) {
        double newTotal = 0.00;
        updateMapOfEntries(cat);
        if (cat.categoryType.equals(CategoryType.INCOME)) {
            FilteredList<Income> toCalculate = this.mapOfIncomeCategories.get(cat);
            double calculatedTotal = 0.00;
            for (int i = 0; i < toCalculate.size(); i++) {
                calculatedTotal = calculatedTotal + toCalculate.get(i).getAmount().value;
            }
            return calculatedTotal;
        } else {
            FilteredList<Expense> toCalculate = this.mapOfExpenseCategories.get(cat);
            double calculatedTotal = 0.00;
            for (int i = 0; i < toCalculate.size(); i++) {
                calculatedTotal = calculatedTotal + toCalculate.get(i).getAmount().value;
            }
            return calculatedTotal;
        }
    }

    /**
     *  If the category doesn't exist in the categoryList, update the categoryList to reflect the newly added Category.
     *
     * @param cat Category to update.
     */
    private void updateMapOfEntries(Category cat) {
        if (cat.categoryType.equals(CategoryType.INCOME)) {
            if (!mapOfIncomeCategories.containsKey(cat.categoryName)) {
                FilteredList<Income> filteredByCategory = new FilteredList<Income> (this.filteredListForIncome,
                        new EntryContainsCategoryPredicate(cat));
                this.mapOfIncomeCategories.put(cat, filteredByCategory);
            }
        } else {
            if (!mapOfExpenseCategories.containsKey(cat.categoryName)) {
                FilteredList<Expense> filteredByCategory = new FilteredList<Expense>(this.filteredListForExpense,
                        new EntryContainsCategoryPredicate(cat));
                this.mapOfExpenseCategories.put(cat, filteredByCategory);
            }
        }
    }


    /**
     * Calculates the statistics everyday to be used in the bar chart.
     *
     * @return listOfDailyStatistics a ObservableList of the statistics of income and expense per day in the month to
     * be used in the bar chart.
     */
    public ObservableList<DailyStatistics> calculateBarChart() {
        for (int i = 1; i <= yearMonth.lengthOfMonth(); i++) {
            DailyList dl = this.mapOfDailyLists.get(i);
            DailyStatistics statsCalculate = dl.calculateBarChart();
            if (!listOfDailyStatistics.get(i - 1).equals(statsCalculate)) {
                listOfDailyStatistics.set(i - 1, statsCalculate);
            }
        }
        return listOfDailyStatistics;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MonthList)) {
            return false;
        }

        MonthList otherMonthList = (MonthList) other;

        return otherMonthList.getFilteredListForExpense().equals(getFilteredListForExpense())
                && otherMonthList.getFilteredListForIncome().equals(getFilteredListForIncome())
                && otherMonthList.getYearMonth().equals(getYearMonth());
    }
}
