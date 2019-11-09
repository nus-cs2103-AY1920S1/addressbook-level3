package dukecooks.model.dashboard;

import static dukecooks.testutil.dashboard.TypicalDashboard.TASK1;
import static dukecooks.testutil.dashboard.TypicalDashboard.TASK2;
import static dukecooks.testutil.dashboard.TypicalDashboard.getTypicalDashboard;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import dukecooks.model.dashboard.components.Dashboard;
import dukecooks.model.dashboard.components.UniqueDashboardList;
import dukecooks.model.dashboard.exceptions.DashboardNotFoundException;
import dukecooks.model.dashboard.exceptions.DuplicateDashboardException;
import dukecooks.testutil.Assert;

public class UniqueDashboardListTest {

    private final UniqueDashboardList uniqueDashboardList = new UniqueDashboardList();

    @Test
    public void contains_nullDashboard_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueDashboardList.contains(null));
    }

    @Test
    public void contains_dashboardNotInList_returnsFalse() {
        assertFalse(uniqueDashboardList.contains(TASK1));
    }

    @Test
    public void contains_dashboardInList_returnsTrue() {
        uniqueDashboardList.add(TASK1);
        assertTrue(uniqueDashboardList.contains(TASK1));
    }

    @Test
    public void add_nullDashboard_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueDashboardList.add(null));
    }

    @Test
    public void add_duplicateDashboard_throwsDuplicateRecipeException() {
        uniqueDashboardList.add(TASK1);
        Assert.assertThrows(DuplicateDashboardException.class, () -> uniqueDashboardList.add(TASK1));
    }

    @Test
    public void setDashboard_nullTargetRecipe_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueDashboardList.setDashboard(null, TASK1));
    }

    @Test
    public void setDashboard_nullEditedRecipe_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, ()
            -> uniqueDashboardList.setDashboard(TASK1, null));
    }

    @Test
    public void setDashboard_targetDashboardNotInList_throwsDashboardNotFoundException() {
        Assert.assertThrows(DashboardNotFoundException.class, () -> uniqueDashboardList.setDashboard(TASK1, TASK1));
    }

    @Test
    public void setDashboard_editedDashboardIsSameDashboard_success() {
        uniqueDashboardList.add(TASK1);
        uniqueDashboardList.setDashboard(TASK1, TASK1);
        UniqueDashboardList expectedUniqueDashboardList = new UniqueDashboardList();
        expectedUniqueDashboardList.add(TASK1);
        assertEquals(expectedUniqueDashboardList, uniqueDashboardList);
    }

    @Test
    public void remove_nullDashboard_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueDashboardList.remove(null));
    }

    @Test
    public void remove_dashboardDoesNotExist_throwsDashboardNotFoundException() {
        Assert.assertThrows(DashboardNotFoundException.class, () -> uniqueDashboardList.remove(TASK1));
    }

    @Test
    public void remove_existingDashboard_removesDashboard() {
        uniqueDashboardList.add(TASK1);
        uniqueDashboardList.remove(TASK1);
        UniqueDashboardList expectedUniqueDashboardList = new UniqueDashboardList();
        assertEquals(expectedUniqueDashboardList, uniqueDashboardList);
    }

    @Test
    public void setDashboards_nullUniqueDashboardList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, ()
            -> uniqueDashboardList.setDashboards((UniqueDashboardList) null));
    }

    @Test
    public void setDashboards_nullList_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, ()
            -> uniqueDashboardList.setDashboards((List<Dashboard>) null));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, ()
            -> uniqueDashboardList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void sortDashboard_success() {
        List<Dashboard> list1 = new ArrayList<>();
        List<Dashboard> list2 = new ArrayList<>();
        list1.add(TASK1);
        list2.add(TASK2);
        list1.add(TASK2);
        list2.add(TASK1);
        UniqueDashboardList uniqueDashboardList1 = new UniqueDashboardList();

        uniqueDashboardList.sortDashboard(list1);
        uniqueDashboardList1.sortDashboard(list2);

        assertEquals(list1, list2);
    }

    @Test
    public void doneFiveContainsNoComplete_isFalse() {
        List<Dashboard> list = new ArrayList<>();
        list.add(TASK1);
        assertEquals(false, uniqueDashboardList.doneFive(list));
    }

    @Test
    public void doneFiveListIsEmpty_isFalse() {
        List<Dashboard> list = new ArrayList<>();
        assertEquals(false, uniqueDashboardList.doneFive(list));
    }

    @Test
    public void testUniqueDashboardListHashCode() {
        UniqueDashboardList list1 = new UniqueDashboardList();
        list1.setDashboards(getTypicalDashboard());
        UniqueDashboardList list2 = new UniqueDashboardList();
        list2.setDashboards(getTypicalDashboard());

        assertEquals(list1.hashCode(), list2.hashCode());
    }

    @Test
    public void testUniqueDashboardListIterator() {
        UniqueDashboardList list = new UniqueDashboardList();

        assertTrue(list.iterator() instanceof Iterator);
    }
}
