package seedu.address.ui.panel;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * Placeholder Panel for to represent null Panel instances.
 */
public class PlaceholderPanel extends Panel {

    private static final String PLACEHOLDER_PANEL_MESSAGE = "This panel is empty!";
    @FXML
    private StackPane panelPlaceholder;

    public PlaceholderPanel() {
        super(SinglePanelView.FXML);
    }

    @Override
    public void view() {
        getRoot().setVisible(true);
        getRoot().setDisable(false);
        panelPlaceholder.getChildren().add(new Label("This is not the Panel you are looking for."));
    }

    @Override
    public void hide() {
        panelPlaceholder.getChildren().clear();
        getRoot().setVisible(false);
        getRoot().setDisable(true);
    }
}
