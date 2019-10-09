package seedu.exercise.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * Controller for panel which has two text area stacked horizontally.
 */
public class LeftRightPanel extends UiPart<Region> {

    private static final String FXML = "LeftRightPanel.fxml";

    @FXML
    private TextArea leftPanel;

    @FXML
    private TextArea rightPanel;

    public LeftRightPanel() {
        super(FXML);
    }

    /**
     * Sets the left panel's text
     * @param text Text to display on left panel
     */
    public void setLeftPanelText(String text) {
        leftPanel.setText(text);
    }

    /**
     * Sets the right panel's text
     * @param text Text to display on right panel
     */
    public void setRightPanelText(String text) {
        rightPanel.setText(text);
    }

    /**
     * Clears text for both left and right text areas
     */
    public void clearAllText() {
        leftPanel.setText("");
        rightPanel.setText("");
    }
}
