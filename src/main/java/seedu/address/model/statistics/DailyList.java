package seedu.address.model.statistics;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Expense;
import seedu.address.model.person.Income;

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

    public double getTotalExpense(){
        return totalExpense;
    }

    public double getTotalIncome(){
        return totalIncome;
    }

    public LocalDate getDate(){
        return dateOfRecord;
    }

    public void addExpense(Expense expenseCreated) {
        requireNonNull(expenseCreated);
        this.listOfExpenses.add(expenseCreated);
        this.totalExpense = this.totalExpense + expenseCreated.getAmount().value;
    }

    public void addIncome(Income incomeCreated) {
        requireNonNull(incomeCreated);
        this.listOfIncomes.add(incomeCreated);
        this.totalIncome = this.totalIncome + incomeCreated.getAmount().value;
    }

}
