package seedu.guilttrip.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.guilttrip.ui.testutil.GuiTestAssert.assertCardDisplaysEntry;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.testutil.ExpenseBuilder;
import seedu.guilttrip.ui.expense.ExpenseCard;
import seedu.guilttrip.ui.gui.guihandles.ExpenseCardHandle;

public class ExpenseCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Expense expenseWithNoTags = new ExpenseBuilder().withTags().build();
        ExpenseCard expenseCard = new ExpenseCard(expenseWithNoTags, 1);
        uiPartExtension.setUiPart(expenseCard);
        assertCardDisplay(expenseCard, expenseWithNoTags, 1);

        // with tags
        Expense expenseWithTags = new ExpenseBuilder().build();
        expenseCard = new ExpenseCard(expenseWithTags, 2);
        uiPartExtension.setUiPart(expenseCard);
        assertCardDisplay(expenseCard, expenseWithTags, 2);
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

        // null -> not equals return true
        assertNotEquals(null, expenseCard);

        // different types -> not equals returns true
        assertNotEquals(0, expenseCard);

        // different expense, same index -> not equals returns true
        Expense differentExpense = new ExpenseBuilder().withDesc("differentDesc").build();
        assertNotEquals(expenseCard, new ExpenseCard(differentExpense, 0));

        // same expense, different index -> not equals returns true
        assertNotEquals(expenseCard, new ExpenseCard(expense, 1));
    }

    /**
     * Asserts that {@code expenseCard} displays the details of {@code expectedExpense} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ExpenseCard expenseCard, Expense expectedExpense, int expectedId) {
        guiRobot.pauseForHuman();

        ExpenseCardHandle expenseCardHandle = new ExpenseCardHandle(expenseCard.getRoot());

        // verify id is displayed correctly
        assertEquals(expectedId + ". ", expenseCardHandle.getId());

        // verify expense details are displayed correctly
        assertCardDisplaysEntry(expectedExpense, expenseCardHandle);
    }
}
