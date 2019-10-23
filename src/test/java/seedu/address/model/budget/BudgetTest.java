package seedu.address.model.budget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalBudgets.OUTSIDE_SCHOOL;
import static seedu.address.testutil.TypicalBudgets.SCHOOL;
import static seedu.address.testutil.TypicalBudgets.SCHOOL_BUDGET_STRING;
import static seedu.address.testutil.TypicalExpenses.ANNIVERSARY;
import static seedu.address.testutil.TypicalExpenses.CHICKEN_RICE;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Timestamp;
import seedu.address.testutil.BudgetBuilder;

public class BudgetTest {

    @Test
    public void testAddExpenses() {
        Budget schoolCopy = new BudgetBuilder(SCHOOL).build();
        schoolCopy.addExpense(ANNIVERSARY);
        assertTrue(schoolCopy.getExpenses().contains(ANNIVERSARY));
    }

    @Test
    public void testGetExpenseSum() {
        ObservableList<Expense> expenses = FXCollections.observableArrayList();
        expenses.add(ANNIVERSARY);
        expenses.add(CHICKEN_RICE);
        Budget editedSchool = new BudgetBuilder(SCHOOL)
                .withExpenses(expenses).build();
        assertEquals(23.50, editedSchool.getExpenseSum());
    }

    @Test
    public void testExpired_inputDateAfterExpiry_returnsTrue() {
        Timestamp dateAfterExpiry = Timestamp.createTimestampIfValid("15-11-2019").get();
        assertTrue(SCHOOL.expired(dateAfterExpiry));
    }

    @Test
    public void testExpired_inputDateBeforeExpiry_returnsFalse() {
        Timestamp dateBeforeExpiry = Timestamp.createTimestampIfValid("15-10-2019").get();
        assertFalse(SCHOOL.expired(dateBeforeExpiry));
    }

    @Test
    public void testRefresh() {
        Timestamp refreshDate = Timestamp.createTimestampIfValid("15-11-2019").get();
        Budget schoolCopy = new BudgetBuilder(SCHOOL).build();
        schoolCopy.refresh(refreshDate);
        assertEquals(Timestamp.createTimestampIfValid("01-11-2019").get(),
                schoolCopy.getStartDate());
        assertEquals(Timestamp.createTimestampIfValid("01-12-2019").get(),
                schoolCopy.getEndDate());
    }

    @Test
    public void testRemoveIdentical() {
        ObservableList<Expense> expenses = FXCollections.observableArrayList();
        expenses.add(ANNIVERSARY);
        Budget editedSchool = new BudgetBuilder(SCHOOL)
                .withExpenses(expenses).build();
        editedSchool.removeIdentical(ANNIVERSARY);
        assertFalse(editedSchool.getExpenses().contains(ANNIVERSARY));
    }

    @Test
    public void testSetExpense() {
        ObservableList<Expense> expenses = FXCollections.observableArrayList();
        expenses.add(ANNIVERSARY);
        Budget editedSchool = new BudgetBuilder(SCHOOL)
                .withExpenses(expenses).build();
        editedSchool.setExpense(ANNIVERSARY, CHICKEN_RICE);
        assertTrue(editedSchool.getExpenses().contains(CHICKEN_RICE));
        assertFalse(editedSchool.getExpenses().contains(ANNIVERSARY));
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

        // different proportion used -> return true
        editedSchool = new BudgetBuilder(SCHOOL)
                .withProportionUsed(new Percentage(90)).build();
        assertTrue(SCHOOL.isSameBudget(editedSchool));

        // different expense list -> return true
        ObservableList<Expense> expenses = FXCollections.observableArrayList();
        expenses.add(ANNIVERSARY);
        editedSchool = new BudgetBuilder(SCHOOL)
                .withExpenses(expenses).build();
        assertTrue(SCHOOL.isSameBudget(editedSchool));

        //different amount, start date, end date, period, primary status, proportion used, expense list
        // -> returns true
        editedSchool = new BudgetBuilder(SCHOOL)
                .withAmount("400")
                .withStartDate("15-10-2019")
                .withEndDate("15-10-2020")
                .withPeriod("year")
                .withIsPrimary(false)
                .withProportionUsed(new Percentage(90))
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
                .withStartDate("15-10-2019").build();
        assertFalse(SCHOOL.equals(editedSchool));

        // different end date -> returns false
        editedSchool = new BudgetBuilder(SCHOOL)
                .withEndDate("15-10-2020").build();
        assertFalse(SCHOOL.equals(editedSchool));

        // different primary status -> returns false
        editedSchool = new BudgetBuilder(SCHOOL)
                .withIsPrimary(false).build();
        assertFalse(SCHOOL.equals(editedSchool));

        // different proportion used -> returns false
        editedSchool = new BudgetBuilder(SCHOOL)
                .withProportionUsed(new Percentage(90)).build();
        assertFalse(SCHOOL.equals(editedSchool));

        // different expense list -> returns false
        ObservableList<Expense> expenses = FXCollections.observableArrayList();
        expenses.add(ANNIVERSARY);
        editedSchool = new BudgetBuilder(SCHOOL)
                .withExpenses(expenses).build();
        assertFalse(SCHOOL.equals(editedSchool));
    }

    @Test
    public void testStringConversion() {
        assertEquals(SCHOOL_BUDGET_STRING, SCHOOL.toString());
    }
}
