package seedu.ichifund.model.budget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.testutil.Assert.assertThrows;
import static seedu.ichifund.testutil.TypicalFundBook.BUDGET_ANIME;
import static seedu.ichifund.testutil.TypicalFundBook.BUDGET_FOOD;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.ichifund.model.budget.exceptions.BudgetNotFoundException;
import seedu.ichifund.model.budget.exceptions.DuplicateBudgetException;
import seedu.ichifund.testutil.BudgetBuilder;

public class UniqueBudgetListTest {

    private final UniqueBudgetList uniqueBudgetList = new UniqueBudgetList();

    @Test
    public void contains_nullBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBudgetList.contains(null));
    }

    @Test
    public void contains_budgetNotInList_returnsFalse() {
        assertFalse(uniqueBudgetList.contains(BUDGET_ANIME));
    }

    @Test
    public void contains_budgetInList_returnsTrue() {
        uniqueBudgetList.add(BUDGET_ANIME);
        assertTrue(uniqueBudgetList.contains(BUDGET_ANIME));
    }

    @Test
    public void contains_budgetWithSameIdentityFieldsInList_returnsTrue() {
        uniqueBudgetList.add(BUDGET_ANIME);
        Budget editedAnime = new BudgetBuilder(BUDGET_ANIME).withAmount(BUDGET_FOOD.getAmount().toString()).build();
        assertTrue(uniqueBudgetList.contains(editedAnime));
    }

    @Test
    public void add_nullBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBudgetList.add(null));
    }

    @Test
    public void add_duplicateBudget_throwsDuplicateBudgetException() {
        uniqueBudgetList.add(BUDGET_ANIME);
        assertThrows(DuplicateBudgetException.class, () -> uniqueBudgetList.add(BUDGET_ANIME));
    }

    @Test
    public void setBudget_nullTargetBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBudgetList.setBudget(null, BUDGET_ANIME));
    }

    @Test
    public void setBudget_nullEditedBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBudgetList.setBudget(BUDGET_ANIME, null));
    }

    @Test
    public void setBudget_targetBudgetNotInList_throwsBudgetNotFoundException() {
        assertThrows(BudgetNotFoundException.class, () -> uniqueBudgetList.setBudget(BUDGET_ANIME, BUDGET_ANIME));
    }

    @Test
    public void setBudget_editedBudgetIsSameBudget_success() {
        uniqueBudgetList.add(BUDGET_ANIME);
        uniqueBudgetList.setBudget(BUDGET_ANIME, BUDGET_ANIME);
        UniqueBudgetList expectedUniqueBudgetList = new UniqueBudgetList();
        expectedUniqueBudgetList.add(BUDGET_ANIME);
        assertEquals(expectedUniqueBudgetList, uniqueBudgetList);
    }

    @Test
    public void setBudget_editedBudgetHasSameIdentity_success() {
        uniqueBudgetList.add(BUDGET_ANIME);
        Budget editedAnime = new BudgetBuilder(BUDGET_ANIME).withAmount(BUDGET_FOOD.getAmount().toString()).build();
        uniqueBudgetList.setBudget(BUDGET_ANIME, editedAnime);
        UniqueBudgetList expectedUniqueBudgetList = new UniqueBudgetList();
        expectedUniqueBudgetList.add(editedAnime);
        assertEquals(expectedUniqueBudgetList, uniqueBudgetList);
    }

    @Test
    public void setBudget_editedBudgetHasDifferentIdentity_success() {
        uniqueBudgetList.add(BUDGET_ANIME);
        uniqueBudgetList.setBudget(BUDGET_ANIME, BUDGET_FOOD);
        UniqueBudgetList expectedUniqueBudgetList = new UniqueBudgetList();
        expectedUniqueBudgetList.add(BUDGET_FOOD);
        assertEquals(expectedUniqueBudgetList, uniqueBudgetList);
    }

    @Test
    public void setBudget_editedBudgetHasNonUniqueIdentity_throwsDuplicateBudgetException() {
        uniqueBudgetList.add(BUDGET_ANIME);
        uniqueBudgetList.add(BUDGET_FOOD);
        assertThrows(DuplicateBudgetException.class, () -> uniqueBudgetList.setBudget(BUDGET_ANIME, BUDGET_FOOD));
    }

    @Test
    public void remove_nullBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBudgetList.remove(null));
    }

    @Test
    public void remove_budgetDoesNotExist_throwsBudgetNotFoundException() {
        assertThrows(BudgetNotFoundException.class, () -> uniqueBudgetList.remove(BUDGET_ANIME));
    }

    @Test
    public void remove_existingBudget_removesBudget() {
        uniqueBudgetList.add(BUDGET_ANIME);
        uniqueBudgetList.remove(BUDGET_ANIME);
        UniqueBudgetList expectedUniqueBudgetList = new UniqueBudgetList();
        assertEquals(expectedUniqueBudgetList, uniqueBudgetList);
    }

    @Test
    public void setBudgets_nullUniqueBudgetList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBudgetList.setBudgets((UniqueBudgetList) null));
    }

    @Test
    public void setBudgets_uniqueBudgetList_replacesOwnListWithProvidedUniqueBudgetList() {
        uniqueBudgetList.add(BUDGET_ANIME);
        UniqueBudgetList expectedUniqueBudgetList = new UniqueBudgetList();
        expectedUniqueBudgetList.add(BUDGET_FOOD);
        uniqueBudgetList.setBudgets(expectedUniqueBudgetList);
        assertEquals(expectedUniqueBudgetList, uniqueBudgetList);
    }

    @Test
    public void setBudgets_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBudgetList.setBudgets((List<Budget>) null));
    }

    @Test
    public void setBudgets_list_replacesOwnListWithProvidedList() {
        uniqueBudgetList.add(BUDGET_ANIME);
        List<Budget> budgetList = Collections.singletonList(BUDGET_FOOD);
        uniqueBudgetList.setBudgets(budgetList);
        UniqueBudgetList expectedUniqueBudgetList = new UniqueBudgetList();
        expectedUniqueBudgetList.add(BUDGET_FOOD);
        assertEquals(expectedUniqueBudgetList, uniqueBudgetList);
    }

    @Test
    public void setBudgets_listWithDuplicateBudgets_throwsDuplicateBudgetException() {
        List<Budget> listWithDuplicateBudgets = Arrays.asList(BUDGET_ANIME, BUDGET_ANIME);
        assertThrows(DuplicateBudgetException.class, () -> uniqueBudgetList.setBudgets(listWithDuplicateBudgets));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueBudgetList.asUnmodifiableObservableList().remove(0));
    }
}
