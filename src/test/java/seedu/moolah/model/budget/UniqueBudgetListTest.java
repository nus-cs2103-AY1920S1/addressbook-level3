package seedu.moolah.model.budget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.testutil.Assert.assertThrows;
import static seedu.moolah.testutil.TypicalMooLah.OUTSIDE_SCHOOL;
import static seedu.moolah.testutil.TypicalMooLah.SCHOOL;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.moolah.model.budget.exceptions.BudgetNotFoundException;
import seedu.moolah.model.budget.exceptions.DeleteDefaultBudgetException;
import seedu.moolah.model.budget.exceptions.DuplicateBudgetException;
import seedu.moolah.model.budget.exceptions.SwitchToFuturePeriodException;
import seedu.moolah.model.general.Timestamp;

public class UniqueBudgetListTest {
    private final UniqueBudgetList uniqueBudgetList = new UniqueBudgetList();

    @Test
    public void contains_nullBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBudgetList.contains(null));
    }

    @Test
    public void contains_budgetNotInList_returnsFalse() {
        assertFalse(uniqueBudgetList.contains(SCHOOL));
    }

    @Test
    public void contains_budgetInList_returnsTrue() {
        uniqueBudgetList.add(SCHOOL);
        assertTrue(uniqueBudgetList.contains(SCHOOL));
    }

    @Test
    public void add_nullBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBudgetList.add(null));
    }

    @Test
    public void add_duplicateBudget_throwsDuplicateBudgetException() {
        uniqueBudgetList.add(SCHOOL);
        assertThrows(DuplicateBudgetException.class, () -> uniqueBudgetList.add(SCHOOL));
    }

    @Test
    public void setBudgets_nullUniqueBudgetList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBudgetList.setBudgets((UniqueBudgetList) null));
    }

    @Test
    public void setBudgets_uniqueBudgetList_replacesOwnListWithProvidedUniqueBudgetList() {
        uniqueBudgetList.add(SCHOOL);
        UniqueBudgetList expectedUniqueBudgetList = new UniqueBudgetList();
        expectedUniqueBudgetList.add(OUTSIDE_SCHOOL);
        uniqueBudgetList.setBudgets(expectedUniqueBudgetList);
        assertEquals(expectedUniqueBudgetList, uniqueBudgetList);
    }

    @Test
    public void setBudgets_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueBudgetList.setBudgets((List<Budget>) null));
    }

    @Test
    public void setBudgets_list_replacesOwnListWithProvidedList() {
        uniqueBudgetList.add(SCHOOL);
        List<Budget> budgetList = Collections.singletonList(OUTSIDE_SCHOOL);
        uniqueBudgetList.setBudgets(budgetList);
        assertTrue(uniqueBudgetList.hasBudgetWithName(OUTSIDE_SCHOOL.getDescription()));
        assertFalse(uniqueBudgetList.hasBudgetWithName(SCHOOL.getDescription()));
    }

    @Test
    public void setBudgets_listWithDuplicateBudgets_throwsDuplicateBudgetException() {
        List<Budget> listWithDuplicateBudgets = Arrays.asList(SCHOOL, SCHOOL);
        assertThrows(DuplicateBudgetException.class, () -> uniqueBudgetList.setBudgets(listWithDuplicateBudgets));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
            uniqueBudgetList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void setPrimaryFromString_validInput_success() {
        uniqueBudgetList.add(OUTSIDE_SCHOOL);
        uniqueBudgetList.add(SCHOOL);
        assertTrue(uniqueBudgetList.getPrimaryBudget().isSameBudget(SCHOOL));
        uniqueBudgetList.setPrimaryFromString("Outside school expenses");
        assertTrue(uniqueBudgetList.getPrimaryBudget().isSameBudget(OUTSIDE_SCHOOL));
    }

    @Test
    public void setPrimaryFromString_invalidInput_throwsBudgetNotFoundException() {
        uniqueBudgetList.add(SCHOOL);
        assertThrows(BudgetNotFoundException.class, () ->
                uniqueBudgetList.setPrimaryFromString("asdf"));
    }

    @Test
    public void remove_inputDefaultBudget_throwsDeleteDefaultBudgetException() {
        assertThrows(DeleteDefaultBudgetException.class, () ->
                uniqueBudgetList.remove(Budget.DEFAULT_BUDGET));
    }

    @Test
    public void remove_inputNotFound_throwsBudgetNotFoundException() {
        uniqueBudgetList.add(SCHOOL);
        assertThrows(BudgetNotFoundException.class, () ->
                uniqueBudgetList.remove(OUTSIDE_SCHOOL));
    }

    @Test
    public void changePrimaryBudgetWindow_inputDateInFuturePeriod_throwsSwitchToFuturePeriodException() {
        uniqueBudgetList.add(SCHOOL);
        Timestamp future = Timestamp.createTimestampIfValid("01-01-2020").get();
        assertThrows(SwitchToFuturePeriodException.class, () ->
                uniqueBudgetList.changePrimaryBudgetWindow(future));
    }

    @Test
    public void testHashCode() {
        uniqueBudgetList.add(OUTSIDE_SCHOOL);
        UniqueBudgetList uniqueBudgetListCopy = new UniqueBudgetList();
        uniqueBudgetListCopy.add(SCHOOL);
        assertFalse(uniqueBudgetList.hashCode() == uniqueBudgetListCopy.hashCode());
    }

    @Test
    public void addBudgetFromStorage_duplicateBudget_throwsDuplicateBudgetException() {
        uniqueBudgetList.add(SCHOOL);
        assertThrows(DuplicateBudgetException.class, () ->
                uniqueBudgetList.addBudgetFromStorage(SCHOOL));
    }

    @Test
    public void iterator() {
        // empty list
        Iterator it = uniqueBudgetList.iterator();
        assertFalse(it.hasNext());

        // non-empty list
        uniqueBudgetList.add(SCHOOL);
        Iterator it2 = uniqueBudgetList.iterator();
        assertTrue(it2.hasNext());
        assertEquals(SCHOOL, it2.next());
    }

    @Test
    public void setBudget_targetNotFound_throwsBudgetNotFoundException() {
        assertThrows(BudgetNotFoundException.class, () ->
                uniqueBudgetList.setBudget(SCHOOL, OUTSIDE_SCHOOL));

    }

    @Test
    public void setBudget_duplicateBudget_throwsDuplicateBudgetException() {
        uniqueBudgetList.add(OUTSIDE_SCHOOL);
        uniqueBudgetList.add(SCHOOL);
        assertThrows(DuplicateBudgetException.class, () ->
                uniqueBudgetList.setBudget(SCHOOL, OUTSIDE_SCHOOL));
    }

    @Test
    public void setBudget_validInput_success() {
        uniqueBudgetList.add(SCHOOL);
        uniqueBudgetList.setBudget(SCHOOL, OUTSIDE_SCHOOL);
        assertFalse(uniqueBudgetList.contains(SCHOOL));
        assertTrue(uniqueBudgetList.contains(OUTSIDE_SCHOOL));
    }
}
