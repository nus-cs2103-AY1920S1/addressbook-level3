package seedu.guilttrip.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.guilttrip.ui.testutil.GuiTestAssert.assertCardDisplaysEntry;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.model.entry.Budget;
import seedu.guilttrip.testutil.BudgetBuilder;
import seedu.guilttrip.ui.budget.BudgetCard;
import seedu.guilttrip.ui.gui.guihandles.BudgetCardHandle;

public class BudgetCardTest extends GuiUnitTest {

    /*@Test
    public void display() {
        // no tags
        Budget budgetWithNoTags = new BudgetBuilder().withTags().build();
        BudgetCard budgetCard = new BudgetCard(budgetWithNoTags, 1);
        uiPartExtension.setUiPart(budgetCard);
        assertCardDisplay(budgetCard, budgetWithNoTags, 1);

        // with tags
        Budget budgetWithTags = new BudgetBuilder().build();
        budgetCard = new BudgetCard(budgetWithTags, 2);
        uiPartExtension.setUiPart(budgetCard);
        assertCardDisplay(budgetCard, budgetWithTags, 2);
    }*/

    @Test
    public void equals() {
        Budget budget = new BudgetBuilder().build();
        BudgetCard budgetCard = new BudgetCard(budget, 0);

        // same budget, same index -> returns true
        BudgetCard copy = new BudgetCard(budget, 0);
        assertEquals(budgetCard, copy);

        // same object -> returns true
        assertEquals(budgetCard, budgetCard);

        // null -> not equals return true
        assertNotEquals(null, budgetCard);

        // different types -> not equals returns true
        assertNotEquals(0, budgetCard);

        // different budget, same index -> not equals returns true
        Budget differentBudget = new BudgetBuilder().withDesc("differentDesc").build();
        assertNotEquals(budgetCard, new BudgetCard(differentBudget, 0));

        // same budget, different index -> not equals returns true
        assertNotEquals(budgetCard, new BudgetCard(budget, 1));
    }

    /**
     * Asserts that {@code budgetCard} displays the details of {@code expectedBudget} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(BudgetCard budgetCard, Budget expectedBudget, int expectedId) {
        guiRobot.pauseForHuman();

        BudgetCardHandle budgetCardHandle = new BudgetCardHandle(budgetCard.getRoot());

        // verify id is displayed correctly
        assertEquals(expectedId + ". ", budgetCardHandle.getId());

        // verify budget details are displayed correctly
        assertCardDisplaysEntry(expectedBudget, budgetCardHandle);
    }
}
