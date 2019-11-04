package seedu.jarvis.ui.planner;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.ui.UiPart;

/**
 * Represents the schedule component of the Planner - shows the tasks in the planner
 * that coincides with the given day and week
 */
public class ScheduleDisplay extends UiPart<Region> {
    public static final String FXML = "ScheduleDisplay.fxml";

    @FXML
    private ListView<Task> day;
    @FXML
    private Label headerDay;
    @FXML
    private Label headerWeek;
    @FXML
    private ListView<Task> week;

    public ScheduleDisplay(ObservableList<Task> day, ObservableList<Task> week) {
        super(FXML);
        this.day.setItems(day);
        this.week.setItems(week);

        headerDay.setText("   Tasks for today:");
        headerWeek.setText("   Tasks for the week:");

        this.day.setCellFactory(listView -> new ScheduleListViewCell());
        this.week.setCellFactory(listView -> new ScheduleListViewCell());

    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Task} using a
     * {@code TaskCard}
     */
    class ScheduleListViewCell extends ListCell<Task> {

        @Override
        public void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskCard(task, getIndex() + 1).getRoot());
            }
        }
    }

}
