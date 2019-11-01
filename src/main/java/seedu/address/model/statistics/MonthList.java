package seedu.address.model.statistics;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.person.Category;
import seedu.address.model.person.CategoryList;
import seedu.address.model.person.Expense;
import seedu.address.model.person.Income;

/**
 * Contains the entries for the current Month in the idea.
 */
public class MonthList {

    private List<Category> listOfExpenseCategories;
    private List<Category> listOfIncomeCategories;
    private ObservableMap<Category, FilteredList<Expense>> mapOfExpenseCategories;
    private ObservableMap<Category, FilteredList<Income>> mapOfIncomeCategories;
    private FilteredList<Expense> filteredListForExpense;
    private FilteredList<Income> filteredListForIncome;
    private Month month;
    private int year;
    private double totalExpense;
    private double totalIncome;

    /**
     * Contains the FilteredList of entries for the month.
     */
    public MonthList(CategoryList listOfCategories, FilteredList<Expense> filteredListOfExpenses,
                     FilteredList<Income> filteredListOfIncome, Month month, int year) {
        this.listOfExpenseCategories = listOfCategories.getInternalListForOtherEntries();
        this.listOfIncomeCategories = listOfCategories.getInternalListForIncome();
        this.filteredListForExpense = filteredListOfExpenses;
        this.filteredListForIncome = filteredListOfIncome;
        mapOfExpenseCategories = FXCollections.observableHashMap();
        mapOfIncomeCategories = FXCollections.observableHashMap();
        this.month = month;
        this.year = year;
        totalExpense = 0.00;
        totalIncome = 0.00;
        initRecords();
    }

    /**
     * Initiates the MonthList Record in guiltTrip.
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
    }

    /**
     * Calculates the stats for the particular category for the particular month.
     * @param cat is the category to calculate the statistics for.
     * @return calculatedTotal, the total amount spent for that particular category in the month.
     */
    public Double updateListOfStats(Category cat) {
        double newTotal = 0.00;
        if (cat.categoryType.equalsIgnoreCase("Income")) {
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

    public ArrayList<Double> calculateBarChart() {
        ArrayList<Double> listOfCategories 
        for (int i = 0 )
    }
}
