package seedu.address.financialtracker.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import seedu.address.financialtracker.model.expense.Amount;
import seedu.address.financialtracker.model.expense.Date;
import seedu.address.financialtracker.model.expense.Description;
import seedu.address.financialtracker.model.expense.Expense;
import seedu.address.financialtracker.model.expense.Time;
import seedu.address.financialtracker.model.expense.Type;

class ExpenseListTest {

    @Test
    void addExpense() {
        ExpenseList expenses = new ExpenseList("Singapore");
        Expense ep1 = new Expense(new Date("271016"), new Time("1720"), new Amount("4"), new Description("breakfast"),
                new Type("Food"));
        expenses.addExpense(ep1);
        assertTrue(expenses.contains(ep1));
    }

    @Test
    void deleteExpense() {
        ExpenseList expenses = new ExpenseList("Singapore");
        Expense ep1 = new Expense(new Date("271016"), new Time("1720"), new Amount("4"), new Description("breakfast"),
                new Type("Food"));
        expenses.addExpense(ep1);
        expenses.deleteExpense(1);
        assertFalse(expenses.contains(ep1));
    }

    @Test
    void contains() {
        ExpenseList expenses = new ExpenseList("Singapore");
        Expense ep1 = new Expense(new Date("271016"), new Time("1720"), new Amount("4"), new Description("breakfast"),
                new Type("Food"));
        Expense ep2 = new Expense(new Date("281016"), new Time("1720"), new Amount("4"), new Description("breakfast"),
                new Type("Food"));
        Expense ep3 = new Expense(new Date("291016"), new Time("1720"), new Amount("4"), new Description("breakfast"),
                new Type("Food"));
        expenses.addExpense(ep1);
        expenses.addExpense(ep2);
        assertTrue(expenses.contains(ep1));
        assertTrue(expenses.contains(ep2));
        assertFalse(expenses.contains(ep3));
    }
}