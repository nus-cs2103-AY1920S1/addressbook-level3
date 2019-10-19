package seedu.address.model.util;

import seedu.address.model.dashboard.DashboardRecords;
import seedu.address.model.dashboard.ReadOnlyDashboard;
import seedu.address.model.dashboard.components.Dashboard;
import seedu.address.model.dashboard.components.DashboardName;

/**
 * Contains utility methods for populating {@code DashboardRecords} with sample data.
 */
public class DashboardSampleDataUtil {
    public static Dashboard[] getSampleDashboards() {
        return new Dashboard[] {
            new Dashboard(new DashboardName("Asian Cuisines")),
            new Dashboard(new DashboardName("Healthy Living")),
            new Dashboard(new DashboardName("Meat Lovers")),
            new Dashboard(new DashboardName("Vegan Diet")),
            new Dashboard(new DashboardName("One Week Slimming")),
            new Dashboard(new DashboardName("Core Exercises")),
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
