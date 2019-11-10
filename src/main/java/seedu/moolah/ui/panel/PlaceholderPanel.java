package seedu.moolah.ui.panel;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * Placeholder Panel for to represent empty Panel instances.
 */
public class PlaceholderPanel extends Panel {

    private static final String PLACEHOLDER_PANEL_MESSAGE = "There is nothing to display here yet!";
    @FXML
    private StackPane panelPlaceholder;

    public PlaceholderPanel() {
        super(SinglePanelView.FXML);
    }

    @Override
    public void view() {
        if (panelPlaceholder.getChildren().size() < 1) {
            panelPlaceholder.getChildren().add(new Label(PLACEHOLDER_PANEL_MESSAGE));
        }
        getRoot().setVisible(true);
        getRoot().setDisable(false);
    }

    @Override
    public void hide() {
        getRoot().setVisible(false);
        getRoot().setDisable(true);
    }
}
