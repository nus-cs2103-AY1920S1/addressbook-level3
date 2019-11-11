package seedu.exercise.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import seedu.exercise.model.resource.Schedule;

/**
 * Panel containing the list of schedules.
 */
public class ScheduleListPanel extends ResourceListPanel {
    private static final String FXML = "ScheduleListPanel.fxml";

    @FXML
    private ListView<Schedule> scheduleListView;

    public ScheduleListPanel(ObservableList<Schedule> scheduleList) {
        super(FXML, scheduleList);
        scheduleListView.setItems(scheduleList);
        scheduleListView.setCellFactory(listView -> new ScheduleListViewCell());
        scheduleListView.getFocusModel().focusedItemProperty().addListener(getDefaultListViewListener());
    }

    @Override
    public void resetListSelection() {
        scheduleListView.getSelectionModel().clearSelection();
    }

    @Override
    public ListView<Schedule> getResourceListView() {
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
