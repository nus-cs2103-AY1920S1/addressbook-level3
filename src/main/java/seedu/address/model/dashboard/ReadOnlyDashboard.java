package seedu.address.model.dashboard;

import javafx.collections.ObservableList;
import seedu.address.model.dashboard.components.Dashboard;

/**
 * Unmodifiable view of Duke Cooks
 */
public interface ReadOnlyDashboard {

    /**
     * Returns an unmodifiable view of the dashboard list.
     * This list will not contain any duplicate dashboards.
     */
    ObservableList<Dashboard> getDashboardList();

}
