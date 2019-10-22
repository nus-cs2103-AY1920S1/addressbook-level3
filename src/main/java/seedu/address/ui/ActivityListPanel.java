package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.itineraryitem.activity.Activity;

/**
 * Panel containing the list of activities.
 */
public class ActivityListPanel extends UiPart<Region> {
    private static final String FXML = "ActivityListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ActivityListPanel.class);

    @FXML
    private ListView<Activity> activityListView;

    public ActivityListPanel(ObservableList<Activity> activityList) {
        super(FXML);
        activityListView.setItems(activityList);
        activityListView.setCellFactory(listView -> new ActivityListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Activity} using a
     * {@code ActivityCardSmall}.
     */
    class ActivityListViewCell extends ListCell<Activity> {
        @Override
        protected void updateItem(Activity activity, boolean empty) {
            super.updateItem(activity, empty);

            if (empty || activity == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ActivityCardSmall(activity, getIndex() + 1).getRoot());
            }
        }
    }

}
