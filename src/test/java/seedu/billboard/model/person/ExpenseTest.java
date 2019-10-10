package seedu.billboard.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_AMOUNT_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_NAME_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_TAG_DINNER;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalPersons.BILLS;
import static seedu.billboard.testutil.TypicalPersons.TAXES;

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
        Expense aliceCopy = new ExpenseBuilder(BILLS).build();
        assertTrue(BILLS.equals(aliceCopy));

        // same object -> returns true
        assertTrue(BILLS.equals(BILLS));

        // null -> returns false
        assertFalse(BILLS.equals(null));

        // different type -> returns false
        assertFalse(BILLS.equals(5));

        // different expense -> returns false
        assertFalse(BILLS.equals(TAXES));

        // different name -> returns false
        Expense editedAlice = new ExpenseBuilder(BILLS).withName(VALID_NAME_TAXES).build();
        assertFalse(BILLS.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new ExpenseBuilder(BILLS).withDescription(VALID_DESCRIPTION_TAXES).build();
        assertFalse(BILLS.equals(editedAlice));

        // different email -> returns false
        editedAlice = new ExpenseBuilder(BILLS).build();
        assertFalse(BILLS.equals(editedAlice));

        // different address -> returns false
        editedAlice = new ExpenseBuilder(BILLS).withAmount(VALID_AMOUNT_TAXES).build();
        assertFalse(BILLS.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ExpenseBuilder(BILLS).withTags(VALID_TAG_DINNER).build();
        assertFalse(BILLS.equals(editedAlice));
    }
}
