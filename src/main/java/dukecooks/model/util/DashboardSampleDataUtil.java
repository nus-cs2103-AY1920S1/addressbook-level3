package dukecooks.model.util;

import dukecooks.model.dashboard.DashboardRecords;
import dukecooks.model.dashboard.ReadOnlyDashboard;
import dukecooks.model.dashboard.components.Dashboard;
import dukecooks.model.dashboard.components.DashboardName;
import dukecooks.model.dashboard.components.TaskDate;
import dukecooks.model.dashboard.components.TaskStatus;

/**
 * Contains utility methods for populating {@code DashboardRecords} with sample data.
 */
public class DashboardSampleDataUtil {
    public static Dashboard[] getSampleDashboards() {
        return new Dashboard[] {
            new Dashboard(new DashboardName("Cook chicken rice"), new TaskDate("21/11/2019"),
                    new TaskStatus("NOT COMPLETE")),
            new Dashboard(new DashboardName("Write diary entry"), new TaskDate("22/11/2019"),
                    new TaskStatus("NOT COMPLETE")),
            new Dashboard(new DashboardName("Exercise"), new TaskDate("22/11/2019"),
                    new TaskStatus("NOT COMPLETE")),
            new Dashboard(new DashboardName("Try the new vegan diet recipe"), new TaskDate("24/11/2019"),
                    new TaskStatus("NOT COMPLETE")),
            new Dashboard(new DashboardName("Doctor appointment at polyclinic"), new TaskDate("27/11/2019"),
                    new TaskStatus("NOT COMPLETE")),
            new Dashboard(new DashboardName("Bake a cake"), new TaskDate("27/11/2019"),
                    new TaskStatus("NOT COMPLETE")),
            new Dashboard(new DashboardName("Bake cupcakes"), new TaskDate("28/11/2019"),
                    new TaskStatus("NOT COMPLETE")),
            new Dashboard(new DashboardName("Project meeting at COM1"), new TaskDate("27/11/2019"),
                    new TaskStatus("NOT COMPLETE")),
            new Dashboard(new DashboardName("Make diary free ice cream"), new TaskDate("14/11/2019"),
                    new TaskStatus("NOT COMPLETE")),
            new Dashboard(new DashboardName("Project meeting"), new TaskDate("27/11/2019"),
                    new TaskStatus("NOT COMPLETE")),
            new Dashboard(new DashboardName("Project meeting at Utown"), new TaskDate("25/11/2019"),
                    new TaskStatus("NOT COMPLETE")),
            new Dashboard(new DashboardName("CCA meeting"), new TaskDate("14/12/2019"),
                    new TaskStatus("NOT COMPLETE")),
            new Dashboard(new DashboardName("Stats HW"), new TaskDate("14/11/2019"),
                    new TaskStatus("NOT COMPLETE")),
            new Dashboard(new DashboardName("Project meeting at COM1"), new TaskDate("24/11/2019"),
                    new TaskStatus("NOT COMPLETE")),
            new Dashboard(new DashboardName("Lunch with friends"), new TaskDate("26/11/2019"),
                    new TaskStatus("NOT COMPLETE")),
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
