package seedu.address.model.util;

import seedu.address.model.dashboard.DashboardRecords;
import seedu.address.model.dashboard.ReadOnlyDashboard;
import seedu.address.model.dashboard.components.Dashboard;
import seedu.address.model.dashboard.components.DashboardName;
import seedu.address.model.dashboard.components.TaskDate;

/**
 * Contains utility methods for populating {@code DashboardRecords} with sample data.
 */
public class DashboardSampleDataUtil {
    public static Dashboard[] getSampleDashboards() {
        return new Dashboard[] {
            new Dashboard(new DashboardName("Cook chicken rice"), new TaskDate("21/10/2019")),
            new Dashboard(new DashboardName("Write diary entry"), new TaskDate("22/10/2019")),
            new Dashboard(new DashboardName("Exercise"), new TaskDate("22/10/2019")),
            new Dashboard(new DashboardName("Try the new vegan diet recipe"), new TaskDate("24/10/2019")),
            new Dashboard(new DashboardName("Doctor appointment at polyclinic"), new TaskDate("27/10/2019")),
            new Dashboard(new DashboardName("Bake a cake"), new TaskDate("27/10/2019")),
        };
    }

    public static ReadOnlyDashboard getSampleDashboardRecords() {
        DashboardRecords sampleDc = new DashboardRecords();
        for (Dashboard sampleDashboard : getSampleDashboards()) {
            sampleDc.addDashboard(sampleDashboard);
        }
        return sampleDc;
    }
}
