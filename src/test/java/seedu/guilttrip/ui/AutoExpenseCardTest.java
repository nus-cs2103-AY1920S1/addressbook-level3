package seedu.guilttrip.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.guilttrip.ui.testutil.GuiTestAssert.assertCardDisplaysEntry;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.model.entry.AutoExpense;
import seedu.guilttrip.testutil.AutoExpenseBuilder;
import seedu.guilttrip.ui.autoexpense.AutoExpensesCard;
import seedu.guilttrip.ui.gui.guihandles.AutoExpenseCardHandle;

public class AutoExpenseCardTest extends GuiUnitTest {

    /*@Test
    public void display() {
        // no tags
        AutoExpense autoExpenseWithNoTags = new AutoExpenseBuilder().withTags().build();
        AutoExpensesCard autoExpenseCard = new AutoExpensesCard(autoExpenseWithNoTags, 1);
        uiPartExtension.setUiPart(autoExpenseCard);
        assertCardDisplay(autoExpenseCard, autoExpenseWithNoTags, 1);

        // with tags
        AutoExpense autoExpenseWithTags = new AutoExpenseBuilder().build();
        autoExpenseCard = new AutoExpensesCard(autoExpenseWithTags, 2);
        uiPartExtension.setUiPart(autoExpenseCard);
        assertCardDisplay(autoExpenseCard, autoExpenseWithTags, 2);
    }*/

    @Test
    public void equals() {
        AutoExpense autoExpense = new AutoExpenseBuilder().build();
        AutoExpensesCard autoExpenseCard = new AutoExpensesCard(autoExpense, 0);

        // same autoExpense, same index -> returns true
        AutoExpensesCard copy = new AutoExpensesCard(autoExpense, 0);
        assertEquals(autoExpenseCard, copy);

        // same object -> returns true
        assertEquals(autoExpenseCard, autoExpenseCard);

        // null -> not equals return true
        assertNotEquals(null, autoExpenseCard);

        // different types -> not equals returns true
        assertNotEquals(0, autoExpenseCard);

        // different autoExpense, same index -> not equals returns true
        AutoExpense differentAutoExpense = new AutoExpenseBuilder().withDesc("differentDesc").build();
        assertNotEquals(autoExpenseCard, new AutoExpensesCard(differentAutoExpense, 0));

        // same autoExpense, different index -> not equals returns true
        assertNotEquals(autoExpenseCard, new AutoExpensesCard(autoExpense, 1));
    }

    /**
     * Asserts that {@code autoExpenseCard} displays the details of {@code expectedAutoExpense} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(AutoExpensesCard autoExpenseCard, AutoExpense expectedAutoExpense, int expectedId) {
        guiRobot.pauseForHuman();

        AutoExpenseCardHandle autoExpenseCardHandle = new AutoExpenseCardHandle(autoExpenseCard.getRoot());

        // verify id is displayed correctly
        assertEquals(expectedId + ". ", autoExpenseCardHandle.getId());

        // verify autoExpense details are displayed correctly
        assertCardDisplaysEntry(expectedAutoExpense, autoExpenseCardHandle);
    }
}
