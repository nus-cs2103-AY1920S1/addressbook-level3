package seedu.address.financialtracker.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import seedu.address.financialtracker.model.expense.Amount;
import seedu.address.financialtracker.model.expense.Country;
import seedu.address.financialtracker.model.expense.Date;
import seedu.address.financialtracker.model.expense.Description;
import seedu.address.financialtracker.model.expense.Expense;
import seedu.address.financialtracker.model.expense.Time;
import seedu.address.financialtracker.model.expense.Type;
import seedu.address.logic.commands.exceptions.CommandException;

class ExpenseListTest {

    @Test
    void addExpense() throws CommandException {
        ExpenseList expenses = new ExpenseList("Singapore");
        Expense ep1 = new Expense(new Date("27102016"), new Time("1720"), new Amount("4"), new Description("breakfast"),
                new Type("Food"));
        expenses.addExpense(ep1, true);
        assertTrue(expenses.contains(ep1));
    }

    @Test
    void deleteExpense() throws CommandException {
        ExpenseList expenses = new ExpenseList("Singapore");
        Expense ep1 = new Expense(new Date("27102016"), new Time("1720"), new Amount("4"), new Description("breakfast"),
                new Type("Food"), new Country("Singapore"));
        expenses.addExpense(ep1, true);
        expenses.deleteExpense(1);
        assertFalse(expenses.contains(ep1));
    }

    @Test
    void contains() throws CommandException {
        ExpenseList expenses = new ExpenseList("Singapore");
        Expense ep1 = new Expense(new Date("27102016"), new Time("1720"), new Amount("4"), new Description("breakfast"),
                new Type("Food"), new Country("Singapore"));
        Expense ep2 = new Expense(new Date("28102016"), new Time("1720"), new Amount("4"), new Description("breakfast"),
                new Type("Food"), new Country("Singapore"));
        Expense ep3 = new Expense(new Date("29102016"), new Time("1720"), new Amount("4"), new Description("breakfast"),
                new Type("Food"), new Country("Singapore"));
        expenses.addExpense(ep1, true);
        expenses.addExpense(ep2, true);
        assertTrue(expenses.contains(ep1));
        assertTrue(expenses.contains(ep2));
        assertFalse(expenses.contains(ep3));
    }
}