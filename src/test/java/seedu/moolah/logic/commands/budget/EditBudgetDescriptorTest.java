package seedu.moolah.logic.commands.budget;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.logic.commands.CommandTestUtil.DESC_HOLIDAY;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_BUDGET_AMOUNT_SCHOOL;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_BUDGET_DESCRIPTION_SCHOOL;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_BUDGET_PERIOD_SCHOOL;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_BUDGET_START_DATE_SCHOOL;

import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.budget.EditBudgetCommand.EditBudgetDescriptor;
import seedu.moolah.testutil.EditBudgetDescriptorBuilder;

public class EditBudgetDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        EditBudgetDescriptor descriptorWithSameValues = new EditBudgetDescriptor(DESC_HOLIDAY);
        assertTrue(DESC_HOLIDAY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_HOLIDAY.equals(DESC_HOLIDAY));

        // null -> returns false
        assertFalse(DESC_HOLIDAY.equals(null));

        // different types -> returns false
        assertFalse(DESC_HOLIDAY.equals(6));

        // different description -> returns false
        EditBudgetDescriptor editedHoliday = new EditBudgetDescriptorBuilder(DESC_HOLIDAY)
                .withDescription(VALID_BUDGET_DESCRIPTION_SCHOOL).build();
        assertFalse(DESC_HOLIDAY.equals(editedHoliday));

        // different amount -> returns false
        editedHoliday = new EditBudgetDescriptorBuilder(DESC_HOLIDAY)
                .withAmount(VALID_BUDGET_AMOUNT_SCHOOL).build();
        assertFalse(DESC_HOLIDAY.equals(editedHoliday));

        // different start date -> returns false
        editedHoliday = new EditBudgetDescriptorBuilder(DESC_HOLIDAY)
                .withStartDate(VALID_BUDGET_START_DATE_SCHOOL).build();
        assertFalse(DESC_HOLIDAY.equals(editedHoliday));

        // different period -> returns false
        editedHoliday = new EditBudgetDescriptorBuilder(DESC_HOLIDAY)
                .withPeriod(VALID_BUDGET_PERIOD_SCHOOL).build();
        assertFalse(DESC_HOLIDAY.equals(editedHoliday));
    }
}
