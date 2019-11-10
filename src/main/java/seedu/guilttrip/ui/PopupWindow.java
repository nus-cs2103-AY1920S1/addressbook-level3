package seedu.guilttrip.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.guilttrip.commons.core.LogsCenter;

/**
 * Controller for a reminder popup.
 */
public class PopupWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(PopupWindow.class);
    private static final String FXML = "PopupWindow.fxml";

    @FXML
    private HBox popupWindowPlaceHolder;

    @FXML
    private VBox leftPlaceHolder;

    @FXML
    private VBox rightPlaceHolder;

    @FXML
    private Label text;

    /**
     * Creates a new PopupWindow.
     *
     * @param root Stage to use as the root of the PopupWindow.
     */
    public PopupWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new PopupWindow.
     */
    public PopupWindow() {
        this(new Stage());
    }


    /**
     * Shows the popup window.
     *
     * @throws IllegalStateException <ul>
     * <li>
     * if this method is called on a thread other than the JavaFX Application Thread.
     * </li>
     * <li>
     * if this method is called during animation or layout processing.
     * </li>
     * <li>
     * if this method is called on the primary stage.
     * </li>
     * <li>
     * if {@code dialogStage} is already showing.
     * </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing reminder popup.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the popup window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the popup window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the popup window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
