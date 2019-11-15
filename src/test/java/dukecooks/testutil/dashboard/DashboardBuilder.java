package dukecooks.testutil.dashboard;

import dukecooks.model.dashboard.components.Dashboard;
import dukecooks.model.dashboard.components.DashboardName;
import dukecooks.model.dashboard.components.TaskDate;
import dukecooks.model.dashboard.components.TaskStatus;

/**
 * A utility class to help with building Dashboard objects.
 */
public class DashboardBuilder {

    public static final String DEFAULT_TASKNAME = "Make an omelette";
    public static final String DEFAULT_TASKDATE = "12/12/2019";
    public static final String DEFAULT_TASKSTATUS = "NOT COMPLETE";

    private DashboardName name;
    private TaskDate date;
    private TaskStatus status;

    public DashboardBuilder() {
        name = new DashboardName(DEFAULT_TASKNAME);
        date = new TaskDate(DEFAULT_TASKDATE);
        status = new TaskStatus(DEFAULT_TASKSTATUS);
    }

    /**
     * Initializes the DashboardBuilder with the data of {@code dashboardToCopy}.
     */
    public DashboardBuilder(Dashboard dashboardToCopy) {
        name = dashboardToCopy.getDashboardName();
        date = dashboardToCopy.getTaskDate();
        status = dashboardToCopy.getTaskStatus();
    }

    /**
     * Sets the {@code DashboardName} of the {@code Dashboard} that we are building.
     */
    public DashboardBuilder withDashboardName(String name) {
        this.name = new DashboardName(name);
        return this;
    }

    /**
     * Sets the {@code TaskDate} of the {@code Dashboard} that we are building.
     */
    public DashboardBuilder withTaskDate(String date) {
        this.date = new TaskDate(date);
        return this;
    }

    /**
     * Sets the {@code TaskStatus} of the {@code Dashboard} that we are building.
     */
    public DashboardBuilder withTaskStatus(String status) {
        this.status = new TaskStatus(status);
        return this;
    }

    public Dashboard build() {
        return new Dashboard(name, date, status);
    }

}
