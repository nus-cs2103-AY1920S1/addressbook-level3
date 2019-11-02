package dukecooks.model.dashboard.components;

import static dukecooks.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a dashboard in Duke Cooks.
 * Guarantees: details are present and not null, field values validated and immutable.
 */
public class Dashboard {

    // Identity fields
    private final DashboardName dashboardName;

    // Data fields
    private final TaskDate taskDate;
    private final TaskStatus taskCheck;

    /**
     * Every field must be present and not null.
     */
    public Dashboard(DashboardName dashboardName, TaskDate taskDate, TaskStatus taskCheck) {
        requireAllNonNull(dashboardName, taskDate, taskCheck);
        this.dashboardName = dashboardName;
        this.taskDate = taskDate;
        this.taskCheck = taskCheck;
    }

    public DashboardName getDashboardName() {
        return dashboardName;
    }

    public TaskDate getTaskDate() {
        return taskDate;
    }

    public TaskStatus getTaskStatus() {
        return taskCheck;
    }

    LocalDate getLocalDate() {
        TaskDate td = getTaskDate();
        return LocalDate.of(Integer.parseInt(td.year), Integer.parseInt(td.month), Integer.parseInt(td.day));
    }

    public boolean isValidDashboard() {
        return true;
    }

    /**
     * Returns true if both tasks of the same DashboardName have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two tasks.
     */
    public boolean isSameDashboard(Dashboard otherDashboard) {

        if (otherDashboard == this) {
            return true;
        }

        return otherDashboard != null
                && otherDashboard.getDashboardName().equals(getDashboardName())
                && otherDashboard.getTaskDate().toString().equals(getTaskDate().toString());
    }


    /**
     * Returns true if both dashboards have the same identity and data fields.
     * This defines a stronger notion of equality between two dashboards.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Dashboard)) {
            return false;
        }

        Dashboard otherDashboard = (Dashboard) other;
        return otherDashboard.getDashboardName().equals(getDashboardName())
                && otherDashboard.getTaskDate().equals(getTaskDate());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(dashboardName, taskDate);
    }

    @Override
    public String toString() {
        return getDashboardName() +
                " Date: " + getTaskDate() +
                " Status: " + getTaskStatus();
    }

}
