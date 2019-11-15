package dukecooks.model.dashboard;

import dukecooks.model.dashboard.components.Dashboard;
import javafx.collections.ObservableList;

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
