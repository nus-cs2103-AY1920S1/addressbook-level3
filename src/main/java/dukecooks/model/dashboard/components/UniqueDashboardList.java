package dukecooks.model.dashboard.components;

import static dukecooks.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import dukecooks.model.dashboard.exceptions.DashboardNotFoundException;
import dukecooks.model.dashboard.exceptions.DuplicateDashboardException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of dashboards that enforces uniqueness between its elements and does not allow nulls.
 * A dashboard is considered unique by comparing using {@code Dashboard#isSameDashboard(Dashboard)}.
 * As such, adding and updating of dashboards uses Dashboard#isSameDashboard(Dashboard) for equality
 * so as to ensure that the dashboard being added or updated is unique in terms of identity in the UniqueDashboardList.
 * However, the removal of a dashboard uses Dashboard#equals(Object) so
 * as to ensure that the dashboard with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Dashboard#isSameDashboard(Dashboard)
 */
public class UniqueDashboardList implements Iterable<Dashboard> {

    private final ObservableList<Dashboard> internalList = FXCollections.observableArrayList();
    private final ObservableList<Dashboard> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    private final long taskLeft = internalList.stream()
            .filter(i -> i.getTaskStatus().getDoneStatus() == false).count();

    /**
     * Get the number of task left in a String.
     */
    public String getTaskLeft() {
        return Long.toString(taskLeft);
    }

    /**
     * Sorts the list by date.
     */
    public void sortDashboard(List<Dashboard> l) {
        Comparator<Dashboard> comparator = new Comparator<Dashboard>() {
            @Override
            public int compare(final Dashboard o1, Dashboard o2) {
                return o1.getLocalDate().compareTo(o2.getLocalDate());
            }
        };
        Collections.sort(l, comparator);
    }

    /**
     * Counts the number of completed task and returns true if 5 new tasks are completed.
     */
    public boolean doneFive(List<Dashboard> l) {
        l.stream().filter(i -> i.getTaskStatus().getNotDoneStatus());
        int size = l.size();
        if (size % 5 == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if the list contains an equivalent dashboard as the given argument.
     */
    public boolean contains(Dashboard toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameDashboard);
    }

    /**
     * Adds a dashboard to the list.
     * The dashboard must not already exist in the list.
     */
    public void add(Dashboard toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateDashboardException();
        }
        internalList.add(toAdd);
        sortDashboard(internalList);
    }

    /**
     * Replaces the dashboard {@code target} in the list with {@code editedDashboard}.
     * {@code target} must exist in the list.
     * The dashboard identity of {@code editedDashboard} must not be the same as another existing dashboard in the list.
     */
    public void setDashboard(Dashboard target, Dashboard editedDashboard) {
        requireAllNonNull(target, editedDashboard);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new DashboardNotFoundException();
        }

        if (!target.isSameDashboard(editedDashboard) && contains(editedDashboard)) {
            throw new DuplicateDashboardException();
        }

        internalList.set(index, editedDashboard);
        sortDashboard(internalList);
    }

    /**
     * Updates the list with the task marked as complete.
     */
    public void done(Dashboard key) {
        requireAllNonNull(key);

        int index = internalList.indexOf(key);
        if (index == -1) {
            throw new DashboardNotFoundException();
        }

        internalList.set(index, key);
        sortDashboard(internalList);
    }

    /**
     * Removes the equivalent dashboard from the list.
     * The dashboard must exist in the list.
     */
    public void remove(Dashboard toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new DashboardNotFoundException();
        }
        sortDashboard(internalList);
    }

    public void setDashboards(UniqueDashboardList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    public void setDashboards(List<Dashboard> dashboards) {
        requireAllNonNull(dashboards);
        if (!dashboardsAreUnique(dashboards)) {
            throw new DuplicateDashboardException();
        }

        internalList.setAll(dashboards);
        sortDashboard(internalList);
    }


    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Dashboard> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Dashboard> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueDashboardList // instanceof handles nulls
                && internalList.equals(((UniqueDashboardList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code dashboards} contains only unique dashboards.
     */
    private boolean dashboardsAreUnique(List<Dashboard> dashboards) {
        for (int i = 0; i < dashboards.size() - 1; i++) {
            for (int j = i + 1; j < dashboards.size(); j++) {
                if (dashboards.get(i).isSameDashboard(dashboards.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
