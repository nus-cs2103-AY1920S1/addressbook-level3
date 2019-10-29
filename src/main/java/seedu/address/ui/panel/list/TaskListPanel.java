package seedu.address.ui.panel.list;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.ui.UiPart;

/**
 * Represents the a Task List to be displayed to the user.
 */
public class TaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListPanel.fxml";

    @FXML
    private VBox taskList;

    public TaskListPanel() {
        super(FXML);
    }

}
