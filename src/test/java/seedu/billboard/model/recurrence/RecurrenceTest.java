package seedu.billboard.model.recurrence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_AMOUNT_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_DATE_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_NAME_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_TAG_DINNER;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalExpenses.BILLS;
import static seedu.billboard.testutil.TypicalRecurrences.RECUR_BILLS;
import static seedu.billboard.testutil.TypicalRecurrences.RECUR_TAXES;

import org.junit.jupiter.api.Test;

import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.tag.Tag;
import seedu.billboard.testutil.RecurrenceBuilder;

public class RecurrenceTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Recurrence recurrence = new RecurrenceBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> recurrence.getTags().add(new Tag("test")));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Expense expense = BILLS;
        Recurrence recurBillsCopy = new RecurrenceBuilder(RECUR_BILLS).build();
        Recurrence recurrence = RECUR_BILLS;
        assertEquals(RECUR_BILLS.getExpenses(), recurBillsCopy.getExpenses());
        assertEquals(RECUR_BILLS, recurBillsCopy);

        // same object -> returns true
        assertEquals(RECUR_BILLS, RECUR_BILLS);

        // null -> returns false
        assertNotEquals(null, RECUR_BILLS);

        // different type -> returns false
        assertNotEquals(5, RECUR_BILLS);

        // different recurrences -> returns false
        assertNotEquals(RECUR_BILLS, RECUR_TAXES);

        // different name -> returns false
        Recurrence editedBills = new RecurrenceBuilder(RECUR_BILLS).withName(VALID_NAME_TAXES).build();
        assertNotEquals(BILLS, editedBills);

        // different description -> returns false
        editedBills = new RecurrenceBuilder(RECUR_BILLS).withDescription(VALID_DESCRIPTION_TAXES).build();
        assertNotEquals(BILLS, editedBills);

        // different amount -> returns false
        editedBills = new RecurrenceBuilder(RECUR_BILLS).withAmount(VALID_AMOUNT_TAXES).build();
        assertNotEquals(BILLS, editedBills);

        // different date -> returns false
        editedBills = new RecurrenceBuilder(RECUR_BILLS).withDate(VALID_DATE_TAXES).build();
        assertNotEquals(BILLS, editedBills);

        // different tags -> returns false
        editedBills = new RecurrenceBuilder(RECUR_BILLS).withTag(VALID_TAG_DINNER).build();
        assertNotEquals(BILLS, editedBills);
    }
}
