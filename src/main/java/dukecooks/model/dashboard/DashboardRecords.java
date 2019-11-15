package dukecooks.model.dashboard;

import static java.util.Objects.requireNonNull;

import java.util.List;

import dukecooks.model.dashboard.components.Dashboard;
import dukecooks.model.dashboard.components.UniqueDashboardList;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the dashboard level
 * Duplicates are not allowed (by .isSameDashboard comparison)
 */
public class DashboardRecords implements ReadOnlyDashboard {

    private final UniqueDashboardList dashboards;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        dashboards = new UniqueDashboardList();
    }

    public DashboardRecords() {}

    /**
     * Creates a DashboardRecords using the Dashboards in the {@code toBeCopied}
     */
    public DashboardRecords(ReadOnlyDashboard toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the dashboard list with {@code dashboards}.
     * {@code diaries} must not contain duplicate diaries.
     */
    public void setDashboards(List<Dashboard> dashboard) {
        this.dashboards.setDashboards(dashboard);
    }

    /**
     * Resets the existing data of this {@code DashboardRecords} with {@code newData}.
     */
    public void resetData(ReadOnlyDashboard newData) {
        requireNonNull(newData);

        setDashboards(newData.getDashboardList());
    }

    //// dashboard-level operations

    /**
     * Returns true if a dashboard with the same identity as {@code dashboard} exists in Duke Cooks.
     */
    public boolean hasDashboard(Dashboard dashboard) {
        requireNonNull(dashboard);
        return dashboards.contains(dashboard);
    }

    /**
     * Returns true if there is 5 new completed task.
     */
    public boolean checkDashboard(List<Dashboard> l) {
        return dashboards.doneFive(l);
    }

    /**
     * Adds a dashboard to Duke Cooks.
     * The dashboard must not already exist in Duke Cooks.
     */
    public void addDashboard(Dashboard p) {
        dashboards.add(p);
    }

    /**
     * Replaces the given dashboard {@code target} in the list with {@code editedDashboard}.
     * {@code target} must exist in Duke Cooks.
     * The dashboard identity of {@code editedDashboard} must not be the same as
     * another existing dashboard in Duke Cooks.
     */
    public void setDashboard(Dashboard target, Dashboard editedDashboard) {
        requireNonNull(editedDashboard);

        dashboards.setDashboard(target, editedDashboard);
    }

    /**
     * Replaces the given dashboard and updates the task as done.
     */
    public void doneDashboard(Dashboard key) {
        dashboards.done(key);
    }

    /**
     * Replaces recently completed dashboard and updates them as completed.
     */

    public void changeDashboard(List<Dashboard> l) {
        dashboards.changeDone(l);
    }

    /**
     * Removes {@code key} from this {@code DashboardRecords}.
     * {@code key} must exist in Duke Cooks.
     */
    public void removeDashboard(Dashboard key) {
        dashboards.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return dashboards.asUnmodifiableObservableList().size() + " dashboards";
        // TODO: refine later
    }


    @Override
    public ObservableList<Dashboard> getDashboardList() {
        return dashboards.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DashboardRecords // instanceof handles nulls
                && dashboards.equals(((DashboardRecords) other).dashboards));
    }

    @Override
    public int hashCode() {
        return dashboards.hashCode();
    }

}
