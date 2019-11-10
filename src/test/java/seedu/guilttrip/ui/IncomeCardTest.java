package seedu.guilttrip.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.guilttrip.ui.testutil.GuiTestAssert.assertCardDisplaysEntry;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.model.entry.Income;
import seedu.guilttrip.testutil.IncomeBuilder;
import seedu.guilttrip.ui.gui.guihandles.IncomeCardHandle;
import seedu.guilttrip.ui.income.IncomeCard;

public class IncomeCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Income incomeWithNoTags = new IncomeBuilder().withTags().build();
        IncomeCard incomeCard = new IncomeCard(incomeWithNoTags, 1);
        uiPartExtension.setUiPart(incomeCard);
        assertCardDisplay(incomeCard, incomeWithNoTags, 1);

        // with tags
        Income incomeWithTags = new IncomeBuilder().build();
        incomeCard = new IncomeCard(incomeWithTags, 2);
        uiPartExtension.setUiPart(incomeCard);
        assertCardDisplay(incomeCard, incomeWithTags, 2);
    }

    @Test
    public void equals() {
        Income income = new IncomeBuilder().build();
        IncomeCard incomeCard = new IncomeCard(income, 0);

        // same income, same index -> returns true
        IncomeCard copy = new IncomeCard(income, 0);
        assertEquals(incomeCard, copy);

        // same object -> returns true
        assertEquals(incomeCard, incomeCard);

        // null -> not equals return true
        assertNotEquals(null, incomeCard);

        // different types -> not equals returns true
        assertNotEquals(0, incomeCard);

        // different income, same index -> not equals returns true
        Income differentIncome = new IncomeBuilder().withDesc("differentDesc").build();
        assertNotEquals(incomeCard, new IncomeCard(differentIncome, 0));

        // same income, different index -> not equals returns true
        assertNotEquals(incomeCard, new IncomeCard(income, 1));
    }

    /**
     * Asserts that {@code incomeCard} displays the details of {@code expectedIncome} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(IncomeCard incomeCard, Income expectedIncome, int expectedId) {
        guiRobot.pauseForHuman();

        IncomeCardHandle incomeCardHandle = new IncomeCardHandle(incomeCard.getRoot());

        // verify id is displayed correctly
        assertEquals(expectedId + ". ", incomeCardHandle.getId());

        // verify income details are displayed correctly
        assertCardDisplaysEntry(expectedIncome, incomeCardHandle);
    }
}
