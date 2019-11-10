package seedu.moolah.ui.expense;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.moolah.ui.testutil.GuiTestAssert.assertCardDisplaysExpense;

import org.junit.jupiter.api.Test;

import guitests.guihandles.expense.ExpenseCardHandle;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.testutil.ExpenseBuilder;
import seedu.moolah.ui.GuiUnitTest;

/**
 * Contains tests for {@code ExpenseCard}.
 *
 * Refactored from:
 * https://github.com/se-edu/addressbook-level4/blob/master/src/test/java/seedu/address/ui/PersonCardTest.java
 */
public class ExpenseCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // defaultValues
        Expense defaultSampleExpense = new ExpenseBuilder().build();
        ExpenseCard expenseCard = new ExpenseCard(defaultSampleExpense, 1);
        uiPartExtension.setUiPart(expenseCard);
        assertCardDisplay(expenseCard, defaultSampleExpense, 1);

        // with description
        Expense expenseWithDescription = new ExpenseBuilder().withDescription("description").build();
        expenseCard = new ExpenseCard(expenseWithDescription, 2);
        uiPartExtension.setUiPart(expenseCard);
        assertCardDisplay(expenseCard, expenseWithDescription, 2);
    }

    @Test
    public void equals() {
        Expense expense = new ExpenseBuilder().build();
        ExpenseCard expenseCard = new ExpenseCard(expense, 0);

        // same expense, same index -> returns true
        ExpenseCard copy = new ExpenseCard(expense, 0);
        assertEquals(expenseCard, copy);

        // same object -> returns true
        assertEquals(expenseCard, expenseCard);

        // null -> returns false
        assertNotEquals(null, expenseCard);

        // different types -> returns false
        assertNotEquals(0, expenseCard);

        // similar expense (different unique ID), same index -> returns false
        int len = ExpenseBuilder.DEFAULT_EXPENSE_UNIQUE_IDENTIFIER.length();
        Expense similarExpense = new ExpenseBuilder().withUniqueIdentifier(
                ExpenseBuilder.DEFAULT_EXPENSE_UNIQUE_IDENTIFIER.substring(0, len - 1) + "2").build();

        assertNotEquals(expenseCard, new ExpenseCard(similarExpense, 0));

        // same expense, different index -> returns false
        assertNotEquals(expenseCard, new ExpenseCard(expense, 1));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ExpenseCard personCard, Expense expectedPerson, int expectedId) {
        guiRobot.pauseForHuman();

        ExpenseCardHandle personCardHandle = new ExpenseCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId), personCardHandle.getIndex());

        // verify person details are displayed correctly
        assertCardDisplaysExpense(expectedPerson, personCardHandle);
    }
}
