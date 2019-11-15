package dukecooks.testutil.dashboard;

import dukecooks.model.dashboard.DashboardRecords;
import dukecooks.model.dashboard.components.Dashboard;

/**
 * A utility class to help with building DashboardRecord objects.
 */
public class DashboardRecordBuilder {

    private DashboardRecords dashboardRecord;

    public DashboardRecordBuilder() {
        dashboardRecord = new DashboardRecords();
    }

    public DashboardRecordBuilder(DashboardRecords dashboardRecord) {
        this.dashboardRecord = dashboardRecord;
    }

    /**
     * Adds a new {@code Dashboard} to the {@code DashboardRecords} that we are building.
     */
    public DashboardRecordBuilder withDashboard(Dashboard dashboard) {
        dashboardRecord.addDashboard(dashboard);
        return this;
    }

    public DashboardRecords build() {
        return dashboardRecord;
    }
}
