package dukecooks.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import dukecooks.commons.core.LogsCenter;
import dukecooks.model.dashboard.components.Dashboard;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of dashboards.
 */
public class DashboardListPanel extends UiPart<Region> {

    private static final String FXML = "DashboardListPanel.fxml";

    public final ObservableList<Dashboard> dashboardList;

    private final Logger logger = LogsCenter.getLogger(DashboardListPanel.class);

    private double value;

    @FXML
    private ListView<Dashboard> dashboardListView;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label todayDate;

    public DashboardListPanel(ObservableList<Dashboard> dashboardList) {
        super(FXML);
        this.dashboardList = dashboardList;
        this.value = taskLeft(dashboardList);
        dashboardListView.setItems(dashboardList);
        dashboardListView.setCellFactory(listView -> new DashboardListViewCell());
    }

    /**
     * Gets the value of task left for the progress bar. The use of Strings is to round the value to one decimal place.
     */
    public double taskLeft(List<Dashboard> l) {
        long num = l.stream().filter(i -> i.getTaskStatus().getDoneStatus()).count();
        String s1 = num + ".0";
        String s2 = l.size() + ".0";
        return Double.parseDouble(s1) / Double.parseDouble(s2);
    }

    public String updateDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(date);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Dashboard} using a {@code DashboardCard}.
     * Updates progress bar, whenever the user adds or deletes a task.
     */
    class DashboardListViewCell extends ListCell<Dashboard> {
        @Override
        protected void updateItem(Dashboard dashboard, boolean empty) {
            super.updateItem(dashboard, empty);
            value = taskLeft(dashboardList);
            progressBar.setProgress(value);
            todayDate.setText(updateDate());
            if (empty || dashboard == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DashboardCard(dashboard, getIndex() + 1).getRoot());
            }
        }
    }
}
