package dukecooks.model.dashboard;

import static dukecooks.testutil.dashboard.TypicalDashboard.TASK1;
import static dukecooks.testutil.dashboard.TypicalDashboard.TASK2;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.dashboard.components.Dashboard;
import dukecooks.testutil.dashboard.DashboardBuilder;

public class DashboardTest {

    @Test
    public void isSameDashboard() {
        // same object -> returns true
        assertTrue(TASK1.isSameDashboard(TASK1));

        // null -> returns false
        assertFalse(TASK1.isSameDashboard(null));

        // different name -> returns false
        Dashboard editeDashboard = new DashboardBuilder(TASK1)
                .withDashboardName(CommandTestUtil.VALID_DASHBOARDNAME_YOGA).build();
        assertFalse(TASK1.isSameDashboard(editeDashboard));

        // same name, different attributes -> returns true
        editeDashboard = new DashboardBuilder(TASK1)
                .withDashboardName(CommandTestUtil.VALID_DASHBOARDNAME_BAKE).build();
        assertTrue(TASK1.isSameDashboard(editeDashboard));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Dashboard aliceCopy = new DashboardBuilder(TASK1).build();
        assertTrue(TASK1.equals(aliceCopy));

        // same object -> returns true
        assertTrue(TASK1.equals(TASK1));

        // null -> returns false
        assertFalse(TASK1.equals(null));

        // different type -> returns false
        assertFalse(TASK1.equals(5));

        // different dashboard -> returns false
        assertFalse(TASK1.equals(TASK2));

        // different name -> returns false
        Dashboard editeDashboard = new DashboardBuilder(TASK1)
                .withDashboardName(CommandTestUtil.VALID_DASHBOARDNAME_YOGA).build();
        assertFalse(TASK1.equals(editeDashboard));

        // different date -> returns false
        editeDashboard = new DashboardBuilder(TASK1).withTaskDate(CommandTestUtil.VALID_TASKDATE2).build();
        assertFalse(TASK1.equals(editeDashboard));
    }
}
