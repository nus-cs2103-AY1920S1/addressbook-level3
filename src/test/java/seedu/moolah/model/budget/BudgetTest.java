package seedu.moolah.model.budget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.testutil.TypicalMooLah.DRINKS;
import static seedu.moolah.testutil.TypicalMooLah.CHICKEN_RICE;
import static seedu.moolah.testutil.TypicalMooLah.OUTSIDE_SCHOOL;
import static seedu.moolah.testutil.TypicalMooLah.SCHOOL;
import static seedu.moolah.testutil.TypicalMooLah.SCHOOL_BUDGET_STRING_ONE;
import static seedu.moolah.testutil.TypicalMooLah.SCHOOL_BUDGET_STRING_TWO;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.testutil.BudgetBuilder;

public class BudgetTest {

    @Test
    public void testAddExpenses() {
        Budget schoolCopy = new BudgetBuilder(SCHOOL).build();
        schoolCopy.addExpense(DRINKS);
        assertTrue(schoolCopy.getExpenses().contains(DRINKS));
    }
    
    @Test
    public void testCalculateExpenseSum() {
        ObservableList<Expense> expenses = FXCollections.observableArrayList();
        expenses.add(CHICKEN_RICE);
        expenses.add(DRINKS);
        Budget editedSchool = new BudgetBuilder(SCHOOL)
                .withExpenses(expenses).build();
        assertEquals(53.50, editedSchool.calculateExpenseSum());
    }

    @Test
    public void testNormalize() {
        Timestamp refreshDate = Timestamp.createTimestampIfValid("17-12-2019").get();
        Budget schoolCopy = new BudgetBuilder(SCHOOL).build().normalize(refreshDate);
        assertEquals(Timestamp.createTimestampIfValid("15-12-2019 noon").get().getDate(),
                schoolCopy.getWindowStartDate().getDate());
        assertEquals(Timestamp.createTimestampIfValid("14-01-2020 noon").get().getDate(),
                schoolCopy.getWindowEndDate().getDate());
    }

    @Test
    public void testRemoveIdentical() {
        ObservableList<Expense> expenses = FXCollections.observableArrayList();
        expenses.add(DRINKS);
        Budget editedSchool = new BudgetBuilder(SCHOOL)
                .withExpenses(expenses).build();
        editedSchool.removeExpense(DRINKS);
        assertFalse(editedSchool.getExpenses().contains(DRINKS));
    }

    @Test
    public void testSetExpense() {
        ObservableList<Expense> expenses = FXCollections.observableArrayList();
        expenses.add(DRINKS);
        Budget editedSchool = new BudgetBuilder(SCHOOL)
                .withExpenses(expenses).build();
        editedSchool.setExpense(DRINKS, CHICKEN_RICE);
        assertTrue(editedSchool.getExpenses().contains(CHICKEN_RICE));
        assertFalse(editedSchool.getExpenses().contains(DRINKS));
    }

    @Test
    public void testIsSameBudget() {
        // same object -> returns true
        assertTrue(SCHOOL.isSameBudget(SCHOOL));

        // null -> returns false
        assertFalse(SCHOOL.isSameBudget(null));

        // different amount -> returns true
        Budget editedSchool = new BudgetBuilder(SCHOOL)
                .withAmount("400").build();
        assertTrue(SCHOOL.isSameBudget(editedSchool));

        // different description -> returns false
        editedSchool = new BudgetBuilder(SCHOOL)
                .withDescription("outside school").build();
        assertFalse(SCHOOL.isSameBudget(editedSchool));

        // different start date -> return true
        editedSchool = new BudgetBuilder(SCHOOL)
                .withStartDate("15-10-2019").build();
        assertTrue(SCHOOL.isSameBudget(editedSchool));

        // different end date -> return true
        editedSchool = new BudgetBuilder(SCHOOL)
                .withEndDate("15-10-2020").build();
        assertTrue(SCHOOL.isSameBudget(editedSchool));

        // different primary status -> return true
        editedSchool = new BudgetBuilder(SCHOOL)
                .withIsPrimary(false).build();
        assertTrue(SCHOOL.isSameBudget(editedSchool));

        // different period -> return true
        editedSchool = new BudgetBuilder(SCHOOL)
                .withPeriod("year").build();
        assertTrue(SCHOOL.isSameBudget(editedSchool));

        // different expense list -> return true
        ObservableList<Expense> expenses = FXCollections.observableArrayList();
        expenses.add(DRINKS);
        editedSchool = new BudgetBuilder(SCHOOL)
                .withExpenses(expenses).build();
        assertTrue(SCHOOL.isSameBudget(editedSchool));

        //different amount, start date, end date, period, primary status, expense list
        // -> returns true
        editedSchool = new BudgetBuilder(SCHOOL)
                .withAmount("400")
                .withStartDate("15-10-2019")
                .withEndDate("15-10-2020")
                .withPeriod("year")
                .withIsPrimary(false)
                .withExpenses(expenses).build();
        assertTrue(SCHOOL.isSameBudget(editedSchool));
    }

    @Test
    public void testEquals() {
        // same values -> returns true
        Budget schoolCopy = new BudgetBuilder(SCHOOL).build();
        assertTrue(SCHOOL.equals(schoolCopy));

        // same object -> returns true
        assertTrue(SCHOOL.equals(SCHOOL));

        // null -> returns false
        assertFalse(SCHOOL.equals(null));

        // different type -> returns false
        assertFalse(SCHOOL.equals(6));

        // different budget -> returns false
        assertFalse(SCHOOL.equals(OUTSIDE_SCHOOL));

        // different description -> returns false
        Budget editedSchool = new BudgetBuilder(SCHOOL)
                .withDescription("outside school").build();
        assertFalse(SCHOOL.equals(editedSchool));

        // different amount -> returns false
        editedSchool = new BudgetBuilder(SCHOOL)
                .withAmount("400").build();
        assertFalse(SCHOOL.equals(editedSchool));

        // different start date -> returns false
        editedSchool = new BudgetBuilder(SCHOOL)
                .withStartDate("18-10-2019").build();
        assertFalse(SCHOOL.equals(editedSchool));

        // different primary status -> returns false
        editedSchool = new BudgetBuilder(SCHOOL)
                .withIsPrimary(false).build();
        assertFalse(SCHOOL.equals(editedSchool));

        // different expense list -> returns false
        ObservableList<Expense> expenses = FXCollections.observableArrayList();
        expenses.add(DRINKS);
        editedSchool = new BudgetBuilder(SCHOOL)
                .withExpenses(expenses).build();
        assertFalse(SCHOOL.equals(editedSchool));
    }

    @Test
    public void testStringConversion() {
        assertTrue(SCHOOL.toString().equals(SCHOOL_BUDGET_STRING_ONE)
                || SCHOOL.toString().equals(SCHOOL_BUDGET_STRING_TWO));
    }
}
