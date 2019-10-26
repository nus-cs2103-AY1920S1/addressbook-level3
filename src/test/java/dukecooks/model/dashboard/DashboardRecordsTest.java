package dukecooks.model.dashboard;

import static dukecooks.testutil.dashboard.TypicalDashboard.TASK1;
import static dukecooks.testutil.dashboard.TypicalDashboard.getTypicalDashboardRecords;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import dukecooks.model.dashboard.components.Dashboard;
import dukecooks.testutil.Assert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DashboardRecordsTest {

    private final DashboardRecords dashboardRecords = new DashboardRecords();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), dashboardRecords.getDashboardList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> dashboardRecords.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyDashboardRecord_replacesData() {
        DashboardRecords newData = getTypicalDashboardRecords();
        dashboardRecords.resetData(newData);
        assertEquals(newData, dashboardRecords);
    }

    @Test
    public void hasDashboard_nullDashboard_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> dashboardRecords.hasDashboard(null));
    }

    @Test
    public void hasDashboard_dashboardNotInDashboardRecords_returnsFalse() {
        assertFalse(dashboardRecords.hasDashboard(TASK1));
    }

    @Test
    public void hasDashboard_dashboardInDashboardRecords_returnsTrue() {
        dashboardRecords.addDashboard(TASK1);
        assertTrue(dashboardRecords.hasDashboard(TASK1));
    }

    @Test
    public void getDashboardList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> dashboardRecords.getDashboardList()
                .remove(0));
    }

    /**
     * A stub ReadOnlyDashboard whose dashboards list can violate interface constraints.
     */
    private static class DashboardRecordStub implements ReadOnlyDashboard {
        private final ObservableList<Dashboard> dashboards = FXCollections.observableArrayList();

        DashboardRecordStub(Collection<Dashboard> dashboards) {
            this.dashboards.setAll(dashboards);
        }

        @Override
        public ObservableList<Dashboard> getDashboardList() {
            return dashboards;
        }
    }

}
