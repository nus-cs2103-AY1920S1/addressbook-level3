package seedu.address.model.statistics;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.function.Predicate;
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

/**
 * Represents a SortType in the finance manager.
 * Guarantees: dateOfList cannot be changed.
 */
public class DailyList {
    private ObservableMap<Category, FilteredList<Expense>> mapOfExpenseCategories;
    private ObservableMap<Category, FilteredList<Income>> mapOfIncomeCategories;
    private List<Category> listOfExpenseCategories;
    private List<Category> listOfIncomeCategories;
    private FilteredList<Expense> listOfExpenses;
    private FilteredList<Income> listOfIncomes;
    private final LocalDate dateOfRecord;
    private double totalExpense;
    private double totalIncome;
    public DailyList(CategoryList listOfCategories, FilteredList<Expense> retrievedExpenseList,
                      FilteredList<Income> retrievedIncomeList, LocalDate date) {
        this.listOfExpenseCategories = listOfCategories.getInternalListForOtherEntries();
        this.listOfIncomeCategories = listOfCategories.getInternalListForIncome();
        mapOfExpenseCategories = FXCollections.observableHashMap();
        mapOfIncomeCategories = FXCollections.observableHashMap();
        listOfExpenses = retrievedExpenseList;
        listOfIncomes = retrievedIncomeList;
        dateOfRecord = date;
        totalExpense = 0.00;
        totalIncome = 0.00;
        initLoadFromFilteredList();
    }

    public DailyList() {
        listOfExpenses = new FilteredList(FXCollections.observableArrayList());
        listOfIncomes = new FilteredList(FXCollections.observableArrayList());
        dateOfRecord = LocalDate.now();
        System.out.println(dateOfRecord);
        totalExpense = 0.00;
        totalIncome = 0.00;
    }

    private void initLoadFromFilteredList() {
        for (int i = 0; i < this.listOfExpenseCategories.size(); i++) {
            Category toFilterCategory = this.listOfExpenseCategories.get(i);
            System.out.println(toFilterCategory.categoryName);
            FilteredList<Expense> filteredByCategory = new FilteredList<Expense>(this.listOfExpenses,
                    new EntryContainsCategoryPredicate(toFilterCategory));
            mapOfExpenseCategories.put(toFilterCategory, filteredByCategory);
        }

        for (int k = 0; k < this.listOfIncomeCategories.size(); k++) {
            Category toFilterCategory = this.listOfIncomeCategories.get(k);
            FilteredList<Income> filteredByCategory = new FilteredList<Income> (this.listOfIncomes,
                    new EntryContainsCategoryPredicate(toFilterCategory));
            mapOfIncomeCategories.put(toFilterCategory, filteredByCategory);
        }
    }

    public FilteredList<Expense> getListOfExpenses() {
        return listOfExpenses;
    }

    public FilteredList<Income> getListOfIncomes() {
        return listOfIncomes;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    /**
     * @return dateOfRecord, the date in which the DailyList was created.
     */
    public LocalDate getDate() {
        return dateOfRecord;
    }

    /**
     * adds the recorded Expense into the listOfExpenses.
     */
    public void addExpense(Expense expenseCreated) {
        requireNonNull(expenseCreated);
        this.listOfExpenses.add(expenseCreated);
        this.totalExpense = this.totalExpense + expenseCreated.getAmount().value;
    }

    /**
     * adds the recorded Income into the listOfExpenses.
     */
    public void addIncome(Income incomeCreated) {
        requireNonNull(incomeCreated);
        this.listOfIncomes.add(incomeCreated);
        this.totalIncome = this.totalIncome + incomeCreated.getAmount().value;
    }

    public double updateListOfStats(Category cat) {
        if (cat.categoryType.equalsIgnoreCase("Income")) {
            FilteredList<Income> toCalculate = this.mapOfIncomeCategories.get(cat);
            double calculatedTotal = 0.00;
            for (int i = 0; i < toCalculate.size(); i++) {
                calculatedTotal = calculatedTotal + toCalculate.get(i).getAmount().value;
            }
            if (calculatedTotal != 0.00) {
                System.out.println(calculatedTotal);
            }
            return calculatedTotal;
        } else {
            FilteredList<Expense> toCalculate = this.mapOfExpenseCategories.get(cat);        double calculatedTotal = 0.00;
            for (int i = 0; i < toCalculate.size(); i++) {
                calculatedTotal = calculatedTotal + toCalculate.get(i).getAmount().value;
            }
            if (calculatedTotal != 0.00) {
                System.out.println(calculatedTotal);
            }
            return calculatedTotal;
        }
    }

}
