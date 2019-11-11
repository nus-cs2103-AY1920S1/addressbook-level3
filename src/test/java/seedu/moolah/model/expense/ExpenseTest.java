package seedu.moolah.model.expense;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_CATEGORY_CHICKEN;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_CATEGORY_TAXI;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_DESCRIPTION_CHICKEN;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_DESCRIPTION_TAXI;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_PRICE_CHICKEN;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_PRICE_TAXI;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_UNIQUE_IDENTIFIER;
import static seedu.moolah.testutil.Assert.assertThrows;
import static seedu.moolah.testutil.TypicalMooLah.ANNIVERSARY;
import static seedu.moolah.testutil.TypicalMooLah.TRANSPORT;

import org.junit.jupiter.api.Test;

import seedu.moolah.model.general.Category;
import seedu.moolah.model.general.Description;
import seedu.moolah.model.general.Price;
import seedu.moolah.testutil.ExpenseBuilder;

public class ExpenseTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new Expense(null, new Price("1"),
                        new Category("FOOD"), null));
        assertThrows(NullPointerException.class, () ->
                new Expense(new Description("meat"), null,
                        new Category("FOOD"), null));
        assertThrows(NullPointerException.class, () ->
                new Expense(new Description("meat"), new Price("1"),
                        null, null));
    }


    @Test
    public void isSameExpense() {
        // same object -> returns true
        assertTrue(ANNIVERSARY.isSameExpense(ANNIVERSARY));

        // null -> returns false
        assertFalse(ANNIVERSARY.isSameExpense(null));

        // different price -> returns true
        Expense editedAlice = new ExpenseBuilder(ANNIVERSARY)
                .withPrice(VALID_EXPENSE_PRICE_TAXI).build();
        assertTrue(ANNIVERSARY.isSameExpense(editedAlice));

        // different description -> returns true
        editedAlice = new ExpenseBuilder(ANNIVERSARY)
                .withDescription(VALID_EXPENSE_DESCRIPTION_TAXI).build();
        assertTrue(ANNIVERSARY.isSameExpense(editedAlice));

        // different category -> return true
        editedAlice = new ExpenseBuilder(ANNIVERSARY)
                .withCategory(VALID_EXPENSE_CATEGORY_CHICKEN).build();
        assertTrue(ANNIVERSARY.isSameExpense(editedAlice));

        //different price, category, and description -> returns true
        editedAlice = new ExpenseBuilder(ANNIVERSARY)
                .withPrice(VALID_EXPENSE_PRICE_CHICKEN)
                .withDescription(VALID_EXPENSE_DESCRIPTION_CHICKEN)
                .withCategory(VALID_EXPENSE_CATEGORY_TAXI).build();
        assertTrue(ANNIVERSARY.isSameExpense(editedAlice));

        // same everything except different unique identifier -> returns false
        editedAlice = new ExpenseBuilder(ANNIVERSARY)
                .withUniqueIdentifier(VALID_EXPENSE_UNIQUE_IDENTIFIER).build();
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
        Expense editedAlice = new ExpenseBuilder(ANNIVERSARY).withDescription(VALID_EXPENSE_DESCRIPTION_TAXI).build();
        assertFalse(ANNIVERSARY.equals(editedAlice));

        // different price -> returns false
        editedAlice = new ExpenseBuilder(ANNIVERSARY).withPrice(VALID_EXPENSE_PRICE_TAXI).build();
        assertFalse(ANNIVERSARY.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ExpenseBuilder(ANNIVERSARY).withCategory(VALID_EXPENSE_CATEGORY_CHICKEN).build();
        assertFalse(ANNIVERSARY.equals(editedAlice));
    }
}
