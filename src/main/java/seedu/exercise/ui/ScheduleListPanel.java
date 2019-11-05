package seedu.exercise.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.model.resource.Schedule;

/**
 * Panel containing the list of schedules.
 */
public class ScheduleListPanel extends ResourceListPanel {
    private static final String FXML = "ScheduleListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ScheduleListPanel.class);

    @FXML
    private ListView<Schedule> scheduleListView;

    public ScheduleListPanel(ObservableList<Schedule> scheduleList) {
        super(FXML, scheduleList);
        scheduleListView.setItems(scheduleList);
        scheduleListView.setCellFactory(listView -> new ScheduleListViewCell());
        scheduleListView.getFocusModel().focusedItemProperty().addListener(getDefaultListViewListener());
    }

    @Override
    protected void selectGivenIndex(int index) {
        if (index >= 0) {
            /*
                An extremely hacky way to get the list to select, focus and scroll to the newly changed item.
                Without this method, when any add/edit commands are supplied, the ListChangeListener attached to
                ObservableList is called first without the list actually changing its structure. So when the index
                is provided, the listview is not updated and thus cannot be focused on.
                So the solution is to make this focusing operation be done at a slightly later time when the
                list view has been updated to reflect the commands changes
             */
            Platform.runLater(() -> selectFocusAndScrollTo(scheduleListView, index));
        }
    }

    @Override
    protected void resetListSelection() {
        scheduleListView.getSelectionModel().clearSelection();
    }

    @Override
    protected ListView<Schedule> getResourceListView() {
        return scheduleListView;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Schedule} using a {@code ScheduleCard}.
     */
    class ScheduleListViewCell extends ListCell<Schedule> {
        @Override
        protected void updateItem(Schedule schedule, boolean empty) {
            super.updateItem(schedule, empty);

            if (empty || schedule == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ScheduleCard(schedule, getIndex() + 1).getRoot());
            }
        }
    }

}
