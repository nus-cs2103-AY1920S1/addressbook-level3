package seedu.revision.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.revision.commons.core.LogsCenter;

import java.net.URL;
import java.util.logging.Logger;

/**
 * Controller for a restore page.
 */
public class RestoreWindow extends UiPart<Stage>{

    public static final String RESTORE_MESSAGE = "Are you sure? This cannot be undone." ;

    private static final Logger logger = LogsCenter.getLogger(RestoreWindow.class);
    private static final String FXML = "RestoreWindow.fxml";

    @FXML
    private Label restoreMessage;

    @FXML
    private Button yes;

    @FXML
    private Button no;

    /**
     * Creates a restore window to confirm restore action.
     *
     * @param root Stage to use as the root for the Restore Window.
     */
    public RestoreWindow(Stage root) {
        super(FXML, root);
        restoreMessage.setText(RESTORE_MESSAGE);
    }

    /**
     * Creates a new HelpWindow.
     */
    public RestoreWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Deletes old answerables json file and replace with default file.
     */
    @FXML
    private void restoreAccept() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();

        clipboard.setContent(url);
    }

    /**
     * Close restore window.
     */
    @FXML
    private void restoreDecline() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();

        clipboard.setContent(url);
    }

}
