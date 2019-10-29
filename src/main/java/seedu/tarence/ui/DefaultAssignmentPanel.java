package seedu.tarence.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.tarence.commons.core.LogsCenter;

/**
 * Panel containing default panel for assignment.
 */
public class DefaultAssignmentPanel extends UiPart<Region> {
    private static final String FXML = "DefaultAssignmentPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DefaultAssignmentPanel.class);
    private String defaultMessage = "Welcome to T.A.rence \uD83D\uDE0A\n"
            + "To see all user commands, type \"help\"\n";

    @FXML
    private Pane defaultPanel;

    public DefaultAssignmentPanel() {
        super(FXML);
        this.defaultPanel = new StackPane(setPane(defaultMessage));
    }

    /**
     * Initiates pane to display default instructions to user.
     * @param text - text to display
     */
    private Pane setPane(String text) {
        VBox pane = new VBox();
        pane.getChildren().addAll(new Label(text));
        return pane;
    }

    public Pane getPane() {
        return this.defaultPanel;
    }
}
