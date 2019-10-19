package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.dashboard.components.Dashboard;
import seedu.address.model.diary.components.Diary;

/**
 * Panel containing the list of persons.
 */
public class DashboardListPanel extends UiPart<Region> {
    private static final String FXML = "DashboardListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DiaryListPanel.class);

    @FXML
    private ListView<Dashboard> dashboardListView;

    public DashboardListPanel(ObservableList<Dashboard> dashboardList) {
        super(FXML);
        dashboardListView.setItems(dashboardList);
        dashboardListView.setCellFactory(listView -> new DashboardListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Diary} using a {@code DiaryCard}.
     */
    class DashboardListViewCell extends ListCell<Dashboard> {
        @Override
        protected void updateItem(Dashboard dashboard, boolean empty) {
            super.updateItem(dashboard, empty);

            if (empty || dashboard == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DashboardCard(dashboard, getIndex() + 1).getRoot());
            }
        }
    }

}
