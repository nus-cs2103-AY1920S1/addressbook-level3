package seedu.address.model.budget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET_AMOUNT_EGYPT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBudgets.KOREA;
import static seedu.address.testutil.TypicalBudgets.JAPAN;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import seedu.address.model.budget.exceptions.DuplicateBudgetException;
import seedu.address.model.budget.exceptions.BudgetNotFoundException;
import seedu.address.testutil.BudgetBuilder;

public class UniqueBudgetListTest {

    private final UniqueBudgetList uniqueBudgetList = new UniqueBudgetList();

    @Test
    public void contains_nullBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBudgetList.contains(null));
    }

    @Test
    public void contains_budgetNotInList_returnsFalse() {
        assertFalse(uniqueBudgetList.contains(KOREA));
    }

    @Test
    public void contains_budgetInList_returnsTrue() {
        uniqueBudgetList.add(KOREA);
        assertTrue(uniqueBudgetList.contains(KOREA));
    }

    @Test
    public void contains_budgetWithSameIdentityFieldsInList_returnsTrue() {
        uniqueBudgetList.add(KOREA);
        Budget newKorea = new BudgetBuilder(KOREA).build();
        assertTrue(uniqueBudgetList.contains(newKorea));
    }

    @Test
    public void add_nullBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBudgetList.add(null));
    }

    @Test
    public void add_duplicateBudget_throwsDuplicateBudgetException() {
        uniqueBudgetList.add(KOREA);
        assertThrows(DuplicateBudgetException.class, () -> uniqueBudgetList.add(KOREA));
    }

    @Test
    public void setBudget_nullTargetBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBudgetList.setBudget(null, KOREA));
    }

    @Test
    public void setBudget_nullEditedBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBudgetList.setBudget(KOREA, null));
    }

    @Test
    public void setBudget_targetBudgetNotInList_throwsBudgetNotFoundException() {
        assertThrows(BudgetNotFoundException.class, () -> uniqueBudgetList.setBudget(KOREA, KOREA));
    }

    @Test
    public void setBudget_editedBudgetIsSameBudget_success() {
        uniqueBudgetList.add(KOREA);
        uniqueBudgetList.setBudget(KOREA, KOREA);
        UniqueBudgetList expectedUniqueBudgetList = new UniqueBudgetList();
        expectedUniqueBudgetList.add(KOREA);
        assertEquals(expectedUniqueBudgetList, uniqueBudgetList);
    }

    @Test
    public void setBudget_editedBudgetHasSameIdentity_success() {
        uniqueBudgetList.add(KOREA);
        Budget editedKorea = new BudgetBuilder(KOREA).withAmount(VALID_BUDGET_AMOUNT_EGYPT).build();
        uniqueBudgetList.setBudget(KOREA, editedKorea);
        UniqueBudgetList expectedUniqueBudgetList = new UniqueBudgetList();
        expectedUniqueBudgetList.add(editedKorea);
        assertEquals(expectedUniqueBudgetList, uniqueBudgetList);
    }

    @Test
    public void setBudget_editedBudgetHasDifferentIdentity_success() {
        uniqueBudgetList.add(KOREA);
        uniqueBudgetList.setBudget(KOREA, JAPAN);
        UniqueBudgetList expectedUniqueBudgetList = new UniqueBudgetList();
        expectedUniqueBudgetList.add(JAPAN);
        assertEquals(expectedUniqueBudgetList, uniqueBudgetList);
    }

    @Test
    public void setBudget_editedBudgetHasNonUniqueIdentity_throwsDuplicateBudgetException() {
        uniqueBudgetList.add(KOREA);
        uniqueBudgetList.add(JAPAN);
        assertThrows(DuplicateBudgetException.class, () -> uniqueBudgetList.setBudget(KOREA, JAPAN));
    }

    @Test
    public void remove_nullBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBudgetList.remove(null));
    }

    @Test
    public void remove_budgetDoesNotExist_throwsBudgetNotFoundException() {
        assertThrows(BudgetNotFoundException.class, () -> uniqueBudgetList.remove(KOREA));
    }

    @Test
    public void remove_existingBudget_removesBudget() {
        uniqueBudgetList.add(KOREA);
        uniqueBudgetList.remove(KOREA);
        UniqueBudgetList expectedUniqueBudgetList = new UniqueBudgetList();
        assertEquals(expectedUniqueBudgetList, uniqueBudgetList);
    }

    @Test
    public void setBudgets_nullUniqueBudgetList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBudgetList.setBudgets((UniqueBudgetList) null));
    }

    @Test
    public void setBudgets_uniqueBudgetList_replacesOwnListWithProvidedUniqueBudgetList() {
        uniqueBudgetList.add(KOREA);
        UniqueBudgetList expectedUniqueBudgetList = new UniqueBudgetList();
        expectedUniqueBudgetList.add(JAPAN);
        uniqueBudgetList.setBudgets(expectedUniqueBudgetList);
        assertEquals(expectedUniqueBudgetList, uniqueBudgetList);
    }

    @Test
    public void setBudgets_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBudgetList.setBudgets((List<Budget>) null));
    }

    @Test
    public void setBudgets_list_replacesOwnListWithProvidedList() {
        uniqueBudgetList.add(KOREA);
        List<Budget> budgetList = Collections.singletonList(JAPAN);
        uniqueBudgetList.setBudgets(budgetList);
        UniqueBudgetList expectedUniqueBudgetList = new UniqueBudgetList();
        expectedUniqueBudgetList.add(JAPAN);
        assertEquals(expectedUniqueBudgetList, uniqueBudgetList);
    }

    @Test
    public void setBudgets_listWithDuplicateBudgets_throwsDuplicateBudgetException() {
        List<Budget> listWithDuplicateBudgets = Arrays.asList(KOREA, KOREA);
        assertThrows(DuplicateBudgetException.class, () -> uniqueBudgetList.setBudgets(listWithDuplicateBudgets));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueBudgetList.asUnmodifiableObservableList().remove(0));
    }
}
