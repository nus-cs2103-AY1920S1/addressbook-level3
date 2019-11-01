package seedu.address.model.statistics;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.Month;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.person.Entry;
import seedu.address.model.person.Expense;
import seedu.address.model.person.Income;

/**
 * Represents a SortType in the finance manager.
 * Guarantees: dateOfList cannot be changed.
 */
public class DailyList {
    private FilteredList<Expense> listOfExpenses;
    private FilteredList<Income> listOfIncomes;
    private double totalExpense;
    private double totalIncome;
    private int day;
    private Month month;
    private int year;

    public DailyList(FilteredList<Expense> retrievedExpenseList, FilteredList<Income> retrievedIncomeList, int day,
                     Month month, int year) {
        listOfExpenses = new FilteredList<Expense>(retrievedExpenseList);
        listOfIncomes = new FilteredList<Income>(retrievedIncomeList);
        this.day = day;
        this.month = month;
        this.year = year;
        totalExpense = 0.00;
        totalIncome = 0.00;
        initLoadFromFilteredList(retrievedExpenseList);
    }


    private void initLoadFromFilteredList(FilteredList<Entry> entryList) {
        for (int i = 0; i < entryList.size(); i++) {
            totalExpense = totalExpense + entryList.get(i).getAmount().value;
        }
    }

    public ObservableList<Entry> getListOfExpenses() {
        return listOfExpenses;
    }

    public ObservableList<Income> getListOfIncomes() {
        return listOfIncomes;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public double calcuulat

}
