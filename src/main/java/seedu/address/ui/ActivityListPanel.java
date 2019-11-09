package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.activity.Activity;

/**
 * Panel containing the list of activities.
 */
public class ActivityListPanel extends UiPart<Region> {
    private static final String FXML = "ListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ActivityListPanel.class);

    @FXML
    private ListView<Activity> listView;

    public ActivityListPanel(ObservableList<Activity> activityList) {
        super(FXML);
        logger.info("Created ActivityListPanel to list activity entries.");
        listView.setItems(activityList);
        listView.setCellFactory(listView -> new ListViewCell());
    }

    /**
     * Forces a refresh of the contents rendered by the {@code ListView} component of this
     * {@ActivityListPanel}.
     */
    public void refreshView() {
        this.listView.refresh();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of an {@code Activity} entry with an {@code ActivityCard}.
     */
    class ListViewCell extends ListCell<Activity> {
        @Override
        protected void updateItem(Activity activity, boolean empty) {
            super.updateItem(activity, empty);

            if (empty || activity == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ActivityCard(activity, getIndex() + 1).getRoot());
            }
        }
    }
}
