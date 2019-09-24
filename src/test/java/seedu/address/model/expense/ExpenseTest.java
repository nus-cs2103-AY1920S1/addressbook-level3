package seedu.address.model.expense;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TRANSPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_TRANSPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CLAIMABLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DISCOUNTED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIQUE_IDENTIFIER;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalExpenses.ANNIVERSARY;
import static seedu.address.testutil.TypicalExpenses.TRANSPORT;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ExpenseBuilder;

public class ExpenseTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Expense expense = new ExpenseBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> expense.getTags().remove(0));
    }

    @Test
    public void isSameExpense() {
        // same object -> returns true
        assertTrue(ANNIVERSARY.isSameExpense(ANNIVERSARY));

        // null -> returns false
        assertFalse(ANNIVERSARY.isSameExpense(null));

        // different price -> returns true
        Expense editedAlice = new ExpenseBuilder(ANNIVERSARY)
                .withPrice(VALID_PRICE_TRANSPORT).build();
        assertTrue(ANNIVERSARY.isSameExpense(editedAlice));

        // different description -> returns true
        editedAlice = new ExpenseBuilder(ANNIVERSARY)
                .withDescription(VALID_DESCRIPTION_TRANSPORT).build();
        assertTrue(ANNIVERSARY.isSameExpense(editedAlice));

        // different tag -> return true
        editedAlice = new ExpenseBuilder(ANNIVERSARY)
                .withTags(VALID_TAG_CLAIMABLE).build();
        assertTrue(ANNIVERSARY.isSameExpense(editedAlice));

        //different price, tag, and description -> returns true
        editedAlice = new ExpenseBuilder(ANNIVERSARY)
                .withPrice(VALID_PRICE_CHICKEN)
                .withDescription(VALID_DESCRIPTION_CHICKEN)
                .withTags(VALID_TAG_DISCOUNTED).build();
        assertTrue(ANNIVERSARY.isSameExpense(editedAlice));

        // same everything except different unique identifier -> returns false
        editedAlice = new ExpenseBuilder(ANNIVERSARY)
                .withUniqueIdentifier(VALID_UNIQUE_IDENTIFIER).build();
        assertFalse(ANNIVERSARY.isSameExpense(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Expense aliceCopy = new ExpenseBuilder(ANNIVERSARY).build();
        assertTrue(ANNIVERSARY.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ANNIVERSARY.equals(ANNIVERSARY));

        // null -> returns false
        assertFalse(ANNIVERSARY.equals(null));

        // different type -> returns false
        assertFalse(ANNIVERSARY.equals(5));

        // different expense -> returns false
        assertFalse(ANNIVERSARY.equals(TRANSPORT));

        // different description -> returns false
        Expense editedAlice = new ExpenseBuilder(ANNIVERSARY).withDescription(VALID_DESCRIPTION_TRANSPORT).build();
        assertFalse(ANNIVERSARY.equals(editedAlice));

        // different price -> returns false
        editedAlice = new ExpenseBuilder(ANNIVERSARY).withPrice(VALID_PRICE_TRANSPORT).build();
        assertFalse(ANNIVERSARY.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ExpenseBuilder(ANNIVERSARY).withTags(VALID_TAG_CLAIMABLE).build();
        assertFalse(ANNIVERSARY.equals(editedAlice));
    }
}
