package seedu.address.model.budget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalMooLah.OUTSIDE_SCHOOL;
import static seedu.address.testutil.TypicalMooLah.SCHOOL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.budget.exceptions.DuplicateBudgetException;

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
        //UniqueBudgetList expectedUniqueBudgetList = new UniqueBudgetList();
        //expectedUniqueBudgetList.add(OUTSIDE_SCHOOL);
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
}
