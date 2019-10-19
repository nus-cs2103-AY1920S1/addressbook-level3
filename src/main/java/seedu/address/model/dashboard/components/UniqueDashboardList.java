package seedu.address.model.dashboard.components;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.dashboard.exceptions.DashboardNotFoundException;
import seedu.address.model.dashboard.exceptions.DuplicateDashboardException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A dashboard is considered unique by comparing using {@code Dashboard#isSameDashboard(Dashboard)}. As such, adding and updating of
 * persons uses Dashboard#isSameDashboard(Dashboard) for equality so as to ensure that the diary being added or updated is
 * unique in terms of identity in the UniqueTodoList. However, the removal of a to-do uses Dashboard#equals(Object) so
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
    }

    public void setDashboards(UniqueDashboardList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
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
     * Returns true if {@code diaries} contains only unique diaries.
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
