package seedu.address.model.expense;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_RUM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_RUM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_RUM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ALCOHOL;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalExpenses.FOOD;
import static seedu.address.testutil.TypicalExpenses.RUM;

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
        assertTrue(FOOD.isSameExpense(FOOD));

        // null -> returns false
        assertFalse(FOOD.isSameExpense(null));

        // different amount and date -> returns false
        Expense editedFood = new ExpenseBuilder(FOOD).withAmount(VALID_AMOUNT_RUM).withDate(VALID_DATE_RUM).build();
        assertFalse(FOOD.isSameExpense(editedFood));

        // different name -> returns false
        editedFood = new ExpenseBuilder(FOOD).withName(VALID_NAME_RUM).build();
        assertFalse(FOOD.isSameExpense(editedFood));

        // same name, same amount, different attributes -> returns true
        editedFood = new ExpenseBuilder(FOOD).withDate(VALID_DATE_RUM)
                .withTags(VALID_TAG_ALCOHOL).build();
        assertTrue(FOOD.isSameExpense(editedFood));

        // same name, same date, different attributes -> returns true
        editedFood = new ExpenseBuilder(FOOD).withAmount(VALID_AMOUNT_RUM)
                .withTags(VALID_TAG_ALCOHOL).build();
        assertTrue(FOOD.isSameExpense(editedFood));

        // same name, same amount, same date, different attributes -> returns true
        editedFood = new ExpenseBuilder(FOOD).withTags(VALID_TAG_ALCOHOL).build();
        assertTrue(FOOD.isSameExpense(editedFood));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Expense foodCopy = new ExpenseBuilder(FOOD).build();
        assertTrue(FOOD.equals(foodCopy));

        // same object -> returns true
        assertTrue(FOOD.equals(FOOD));

        // null -> returns false
        assertFalse(FOOD.equals(null));

        // different type -> returns false
        assertFalse(FOOD.equals(5));

        // different expense -> returns false
        assertFalse(FOOD.equals(RUM));

        // different name -> returns false
        Expense editedFood = new ExpenseBuilder(FOOD).withName(VALID_NAME_RUM).build();
        assertFalse(FOOD.equals(editedFood));

        // different amount -> returns false
        editedFood = new ExpenseBuilder(FOOD).withAmount(VALID_AMOUNT_RUM).build();
        assertFalse(FOOD.equals(editedFood));

        // different date -> returns false
        editedFood = new ExpenseBuilder(FOOD).withDate(VALID_DATE_RUM).build();
        assertFalse(FOOD.equals(editedFood));

        // different tags -> returns false
        editedFood = new ExpenseBuilder(FOOD).withTags(VALID_TAG_ALCOHOL).build();
        assertFalse(FOOD.equals(editedFood));
    }
}
