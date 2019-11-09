package seedu.address.ui.panel.list;

import java.util.HashMap;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import seedu.address.model.events.EventSource;
import seedu.address.model.tasks.TaskSource;
import seedu.address.ui.UiPart;

//@@author Kyzure
/**
 * An Ui that stores the logged feedback from the program to the user.
 */
public class ListPanel extends UiPart<Region> {

    private static final String FXML = "ListPanel.fxml";

    private EventListPanel eventListPanel;
    private TaskListPanel taskListPanel;

    @FXML
    private GridPane listPanelGrid;

    @FXML
    private StackPane eventListPlaceholder;

    @FXML
    private StackPane taskListPlaceholder;

    /**
     * Constructor for ListPanel
     */
    public ListPanel() {
        super(FXML);
        eventListPanel = new EventListPanel();
        taskListPanel = new TaskListPanel();

        eventListPlaceholder.getChildren().add(eventListPanel.getRoot());
        taskListPlaceholder.getChildren().add(taskListPanel.getRoot());
    }

    public void onEventListChange(List<EventSource> events, HashMap<EventSource, Integer> eventHash) {
        eventListPanel.onEventListChange(events, eventHash);
    }

    public void onTaskListChange(List<TaskSource> tasks, HashMap<TaskSource, Integer> taskHash) {
        taskListPanel.onTaskListChange(tasks, taskHash);
    }
}
