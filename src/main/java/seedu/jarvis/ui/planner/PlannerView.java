package seedu.jarvis.ui.planner;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import seedu.jarvis.logic.Logic;
import seedu.jarvis.model.Model;
import seedu.jarvis.ui.MainWindow;
import seedu.jarvis.ui.template.View;

/**
 * Contains the Planner in JARVIS
 */
public class PlannerView extends View<AnchorPane> {
    private static final String FXML = "PlannerView.fxml";

    private TaskListView taskListView;
    private SortedTasksDisplay sortedTasksDisplay;
    private ScheduleDisplay scheduleDisplay;
    private PulledTasksDisplay pulledTasksDisplay;
    private PlannerUiType type;

    @FXML
    private SplitPane taskSplitPane;

    public PlannerView(MainWindow mainWindow, Logic logic, Model model, PlannerUiType type) {
        super(FXML, mainWindow, logic, model);
        this.type = type;
    }

    @Override
    public void fillPage() {
        while (!taskSplitPane.getItems().isEmpty()) {
            taskSplitPane.getItems().remove(0);
        }
        taskListView = new TaskListView(logic.getUnfilteredTaskList());

        if (type == PlannerUiType.SCHEDULE) {
            scheduleDisplay = new ScheduleDisplay(logic.getTasksToday(), logic.getTasksThisWeek());
            taskSplitPane.getItems().addAll(taskListView.getRoot(),
                    scheduleDisplay.getRoot());
        } else if (type == PlannerUiType.FIND) {
            sortedTasksDisplay = new SortedTasksDisplay(logic.getFilteredTaskList());
            taskSplitPane.getItems().addAll(taskListView.getRoot(),
                    sortedTasksDisplay.getRoot());
        } else {
            pulledTasksDisplay = new PulledTasksDisplay(logic.getFilteredTaskList());
            taskSplitPane.getItems().addAll(taskListView.getRoot(),
                    pulledTasksDisplay.getRoot());
        }
    }
}
