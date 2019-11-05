package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBudgets.BUDGET_ONE;
import static seedu.address.testutil.TypicalBudgets.BUDGET_TWO;

import org.junit.jupiter.api.Test;

import seedu.address.model.transaction.exceptions.BudgetNotFoundException;
import seedu.address.model.transaction.exceptions.DuplicateBudgetException;
import seedu.address.testutil.BudgetBuilder;

public class UniqueBudgetListTest {

    private final UniqueBudgetList uniqueBudgetList = new UniqueBudgetList();

    @Test
    public void contains_nullBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBudgetList.contains(null));
    }

    @Test
    public void contains_transactionNotInList_returnsFalse() {
        assertFalse(uniqueBudgetList.contains(BUDGET_ONE));
    }

    @Test
    public void contains_transactionNotInList_returnsTrue() {
        uniqueBudgetList.add(BUDGET_ONE);
        assertTrue(uniqueBudgetList.contains(BUDGET_ONE));
    }

    @Test
    public void contains_budgetWithSameIdentityFieldsInList_returnsTrue() {
        uniqueBudgetList.add(BUDGET_ONE);
        Budget editedBudgetOne = new BudgetBuilder(BUDGET_ONE).build();
        assertTrue(uniqueBudgetList.contains(editedBudgetOne));
    }

    @Test
    public void add_nullBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBudgetList.add(null));
    }

    @Test
    public void add_duplicateBudget_throwsDuplicateBudgetException() {
        uniqueBudgetList.add(BUDGET_ONE);
        assertThrows(DuplicateBudgetException.class, () -> uniqueBudgetList.add(BUDGET_ONE));
    }

    @Test
    public void setBudget_nullTargetBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBudgetList.setBudget(null, BUDGET_ONE));
    }

    @Test
    public void setBudget_nullEditedBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBudgetList.setBudget(BUDGET_ONE, null));
    }

    @Test
    public void setBudget_targetBudgetNotInList_throwsBudgetNotFoundException() {
        assertThrows(BudgetNotFoundException.class, () -> uniqueBudgetList.setBudget(BUDGET_ONE, BUDGET_ONE));
    }

    @Test
    public void setBudget_editedBudgetIsSameBudget_success() {
        uniqueBudgetList.add(BUDGET_ONE);
        uniqueBudgetList.setBudget(BUDGET_ONE, BUDGET_ONE);
        UniqueBudgetList expectedUniqueBudgetList = new UniqueBudgetList();
        expectedUniqueBudgetList.add(BUDGET_ONE);
        assertEquals(expectedUniqueBudgetList, uniqueBudgetList);
    }

    @Test
    public void setTransaction_editedTransactionHasSameIdentity_success() {
        uniqueBudgetList.add(BUDGET_ONE);
        Budget editedBudgetOne = new BudgetBuilder(BUDGET_ONE).build();
        uniqueBudgetList.setBudget(BUDGET_ONE, editedBudgetOne);
        UniqueBudgetList expectedUniqueBudgetList = new UniqueBudgetList();
        expectedUniqueBudgetList.add(editedBudgetOne);
        assertEquals(expectedUniqueBudgetList, uniqueBudgetList);
    }

    @Test
    public void setBudget_editedBudgetHasDifferentIdentity_success() {
        uniqueBudgetList.add(BUDGET_ONE);
        uniqueBudgetList.setBudget(BUDGET_ONE, BUDGET_TWO);
        UniqueBudgetList expectedUniqueBudgetList = new UniqueBudgetList();
        expectedUniqueBudgetList.add(BUDGET_TWO);
        assertEquals(expectedUniqueBudgetList, uniqueBudgetList);
    }

    @Test
    public void setBudget_editedBudgetHasNonUniqueIdentity_throwsDuplicateTransactionException() {
        uniqueBudgetList.add(BUDGET_ONE);
        uniqueBudgetList.add(BUDGET_TWO);
        assertThrows(DuplicateBudgetException.class, () -> uniqueBudgetList.setBudget(BUDGET_ONE, BUDGET_TWO));
    }

    @Test
    public void remove_nullBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBudgetList.remove(null));
    }

    @Test
    public void remove_budgetDoesNotExist_throwsBudgetNotFoundException() {
        assertThrows(BudgetNotFoundException.class, () -> uniqueBudgetList.remove(BUDGET_ONE));
    }

    @Test
    public void remove_existingBudget_removesBudget() {
        uniqueBudgetList.add(BUDGET_ONE);
        uniqueBudgetList.remove(BUDGET_ONE);
        UniqueBudgetList expectedUniqueBudgetList = new UniqueBudgetList();
        assertEquals(expectedUniqueBudgetList, uniqueBudgetList);
    }
}
