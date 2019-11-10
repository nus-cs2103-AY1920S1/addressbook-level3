package seedu.address.model.budget;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET_AMOUNT_EGYPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET_CURRENCY_EGYPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET_END_DATE_EGYPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET_NAME_EGYPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET_START_DATE_EGYPT;
import static seedu.address.testutil.TypicalBudgets.JAPAN;
import static seedu.address.testutil.TypicalBudgets.KOREA;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.BudgetBuilder;

public class BudgetTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Budget budget = new BudgetBuilder().build();
    }

    @Test
    public void isSameBudget() {
        // same object -> returns true
        assertTrue(KOREA.isSameBudget(KOREA));

        // null -> returns false
        assertFalse(KOREA.isSameBudget(null));

        // different amount and currency -> returns false
        Budget editedKorea = new BudgetBuilder(KOREA).withAmount(VALID_BUDGET_AMOUNT_EGYPT)
            .withCurrency(VALID_BUDGET_CURRENCY_EGYPT).build();
        assertFalse(KOREA.isSameBudget(editedKorea));

        // different name -> returns false
        editedKorea = new BudgetBuilder(KOREA).withName(VALID_BUDGET_NAME_EGYPT).build();
        assertFalse(KOREA.isSameBudget(editedKorea));

        // different currency -> returns false
        editedKorea = new BudgetBuilder(KOREA).withCurrency(VALID_BUDGET_CURRENCY_EGYPT).build();
        assertFalse(KOREA.isSameBudget(editedKorea));

        // different amount -> returns false
        editedKorea = new BudgetBuilder(KOREA).withAmount(VALID_BUDGET_AMOUNT_EGYPT).build();
        assertFalse(KOREA.isSameBudget(editedKorea));

        // different start date -> returns false
        editedKorea = new BudgetBuilder(KOREA).withStartDate(VALID_BUDGET_START_DATE_EGYPT).build();
        assertFalse(KOREA.isSameBudget(editedKorea));

        // different end date -> returns false
        editedKorea = new BudgetBuilder(KOREA).withEndDate(VALID_BUDGET_END_DATE_EGYPT).build();
        assertFalse(KOREA.isSameBudget(editedKorea));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Budget budgetCopy = new BudgetBuilder(KOREA).build();
        assertTrue(KOREA.equals(budgetCopy));

        // same object -> returns true
        assertTrue(KOREA.equals(KOREA));

        // null -> returns false
        assertFalse(KOREA.equals(null));

        // different type -> returns false
        assertFalse(KOREA.equals(5));

        // different Budget -> returns false
        assertFalse(KOREA.equals(JAPAN));

        // different name -> returns false
        Budget editedKorea = new BudgetBuilder(KOREA).withName(VALID_BUDGET_NAME_EGYPT).build();
        assertFalse(KOREA.equals(editedKorea));

        // different amount -> returns false
        editedKorea = new BudgetBuilder(KOREA).withAmount(VALID_BUDGET_AMOUNT_EGYPT).build();
        assertFalse(KOREA.equals(editedKorea));

        // different currency -> returns false
        editedKorea = new BudgetBuilder(KOREA).withCurrency(VALID_BUDGET_CURRENCY_EGYPT).build();
        assertFalse(KOREA.equals(editedKorea));

        // different start date -> returns false
        editedKorea = new BudgetBuilder(KOREA).withStartDate(VALID_BUDGET_START_DATE_EGYPT).build();
        assertFalse(KOREA.equals(editedKorea));

        // different end date -> returns false
        editedKorea = new BudgetBuilder(KOREA).withEndDate(VALID_BUDGET_END_DATE_EGYPT).build();
        assertFalse(KOREA.equals(editedKorea));
    }
}
