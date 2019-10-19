package seedu.exercise.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.model.resource.Schedule;

/**
 * Panel containing the list of schedules.
 */
public class ScheduleListPanel extends UiPart<Region> {
    private static final String FXML = "ScheduleListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ScheduleListPanel.class);

    @FXML
    private ListView<Schedule> scheduleListView;

    public ScheduleListPanel(ObservableList<Schedule> scheduleList) {
        super(FXML);
        scheduleListView.setItems(scheduleList);
        scheduleListView.setCellFactory(listView -> new ScheduleListViewCell());
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
