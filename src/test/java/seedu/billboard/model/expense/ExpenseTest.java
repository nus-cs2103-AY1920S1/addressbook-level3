package seedu.billboard.model.expense;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_AMOUNT_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_NAME_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_TAG_DINNER;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalExpenses.BILLS;
import static seedu.billboard.testutil.TypicalExpenses.TAXES;

import org.junit.jupiter.api.Test;

import seedu.billboard.testutil.ExpenseBuilder;

public class ExpenseTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Expense expense = new ExpenseBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> expense.getTags().remove(0));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Expense billsCopy = new ExpenseBuilder(BILLS).build();
        assertEquals(BILLS, billsCopy);

        // same object -> returns true
        assertEquals(BILLS, BILLS);

        // null -> returns false
        assertNotEquals(null, BILLS);

        // different type -> returns false
        assertNotEquals(5, BILLS);

        // different expense -> returns false
        assertNotEquals(BILLS, TAXES);

        // different name -> returns false
        Expense editedBills = new ExpenseBuilder(BILLS).withName(VALID_NAME_TAXES).build();
        assertNotEquals(BILLS, editedBills);

        // different description -> returns false
        editedBills = new ExpenseBuilder(BILLS).withDescription(VALID_DESCRIPTION_TAXES).build();
        assertNotEquals(BILLS, editedBills);

        // different amount -> returns false
        editedBills = new ExpenseBuilder(BILLS).withAmount(VALID_AMOUNT_TAXES).build();
        assertNotEquals(BILLS, editedBills);

        // different tags -> returns false
        editedBills = new ExpenseBuilder(BILLS).withTags(VALID_TAG_DINNER).build();
        assertNotEquals(BILLS, editedBills);
    }
}
