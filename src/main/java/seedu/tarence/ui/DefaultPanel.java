package seedu.tarence.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.tarence.commons.core.LogsCenter;

/**
 * Panel containing default panel for assignment.
 */
public class DefaultPanel extends UiPart<Region> {
    private static final String FXML = "DefaultPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DefaultPanel.class);
    private String defaultMessage = "Welcome to T.A.rence \uD83D\uDE0A\n"
            + "To view a class assignment, type: \n"
            + "\"displayScore i/tutorial_index, f/display_format, n/assignment_name\"\n"
            + "where display format can be \"t\" for table display, or \n"
            + "\"g\" for graphical display";

    @FXML
    private Pane defaultPanel;

    @FXML
    private Label defaultLabel;

    public DefaultPanel() {
        super(FXML);
        this.defaultPanel = new StackPane();
        defaultLabel.setText(defaultMessage);
        defaultPanel.getChildren().addAll(defaultLabel);
    }

    public Pane getPane() {
        return this.defaultPanel;
    }
}
