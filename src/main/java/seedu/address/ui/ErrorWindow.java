package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a error window
 */
public class ErrorWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(ErrorWindow.class);
    private static final String FXML = "ErrorWindow.fxml";

    @FXML
    private Label errorMessage;

    /**
     * Creates a new ErrorWindow.
     *
     * @param root Stage to use as the root of the ErrorWindow.
     * @param message of the exception to show in the ErrorWindow.
     */
    public ErrorWindow(Stage root, String message) {
        super(FXML, root);
        errorMessage.setText(message);
    }

    /**
     * Creates a new ErrorWindow.
     */
    public ErrorWindow(String message) {
        this(new Stage(), message);

    }

    /**
     * Shows the error window.
     */
    public void show() {
        logger.fine("Showing error message.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the error window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the error window.
     */
    public void hide() {
        getRoot().hide();
    }



}
