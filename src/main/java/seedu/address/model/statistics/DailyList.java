package seedu.address.model.statistics;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Expense;
import seedu.address.model.person.Income;

/**
 * Represents a SortType in the finance manager.
 * Guarantees: dateOfList cannot be changed.
 */
public class DailyList {
    private ObservableList<Expense> listOfExpenses;
    private ObservableList<Income> listOfIncomes;
    private final LocalDate dateOfRecord;
    private double totalExpense;
    private double totalIncome;

    public DailyList() {
        listOfExpenses = FXCollections.observableArrayList();;
        listOfIncomes = FXCollections.observableArrayList();
        dateOfRecord = LocalDate.now();
        System.out.println(dateOfRecord);
        totalExpense = 0.00;
        totalIncome = 0.00;
    }

    public ObservableList<Expense> getListOfExpenses() {
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

}
