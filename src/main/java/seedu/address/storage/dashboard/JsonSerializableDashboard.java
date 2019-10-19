package seedu.address.storage.dashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.dashboard.DashboardRecords;
import seedu.address.model.dashboard.ReadOnlyDashboard;
import seedu.address.model.dashboard.components.Dashboard;

/**
 * An Immutable Dashboard Catalogue that is serializable to JSON format.
 */
@JsonRootName(value = "dashboardcatalogue")
class JsonSerializableDashboard {

    public static final String MESSAGE_DUPLICATE_DASHBOARD = "Dashboard list contains duplicate dashboard(s).";

    private final List<JsonAdaptedDashboard> dashboards = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableRecipeBook} with the given dashboards.
     */
    @JsonCreator
    public JsonSerializableDashboard(@JsonProperty("dashboards") List<JsonAdaptedDashboard> dashboards) {
        this.dashboards.addAll(dashboards);
    }

    /**
     * Converts a given {@code ReadOnlyDashboard} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableDashboard}.
     */
    public JsonSerializableDashboard(ReadOnlyDashboard source) {
        dashboards.addAll(source.getDashboardList().stream().map(JsonAdaptedDashboard::new).collect(Collectors.toList()));
    }

    /**
     *  Converts this Dashboard Catalogue into the model's {@code DashboardRecord} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public DashboardRecords toModelType() throws IllegalValueException {
        DashboardRecords dashboardRecords = new DashboardRecords();
        for (JsonAdaptedDashboard jsonAdaptedDashboard : dashboards) {
            Dashboard dashboard = jsonAdaptedDashboard.toModelType();
            if (dashboardRecords.hasDashboard(dashboard)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DASHBOARD);
            }
            dashboardRecords.addDashboard(dashboard);
        }
        return dashboardRecords;
    }
}
