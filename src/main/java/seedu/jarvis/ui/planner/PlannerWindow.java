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
public class PlannerWindow extends View<AnchorPane> {
    private static final String FXML = "PlannerWindow.fxml";

    private TaskListView taskListView;

    @FXML
    private SplitPane taskSplitPane;

    public PlannerWindow(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
    }

    @Override
    public void fillPage() {
        while(!taskSplitPane.getItems().isEmpty()) {
            taskSplitPane.getItems().remove(0);
        }
        taskListView = new TaskListView(logic.getFilteredTaskList());
        taskSplitPane.getItems().addAll(taskListView.getRoot());
    }
}

