package seedu.mark.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.mark.commons.core.LogsCenter;
import seedu.mark.model.bookmark.Url;
import seedu.mark.model.reminder.Note;

import java.util.logging.Logger;

/**
 * Controller for a reminder page
 */
public class ReminderWindow extends UiPart<Stage> {

    public final String reminderUrl;
    public final String reminder_Message;

    private static final Logger logger = LogsCenter.getLogger(ReminderWindow.class);
    private static final String FXML = "ReminderWindow.fxml";

    @javafx.fxml.FXML
    private Button copyButton;

    @FXML
    private Label reminderMessage;

    /**
     * Creates a new ReminderWindow.
     *
     * @param root Stage to use as the root of the ReminderWindow.
     */
    public ReminderWindow(Stage root, Url url, Note note) {
        super(FXML, root);
        reminderUrl = url.toString();
        reminder_Message  = "This reminder is about to due: " + note.toString();
        reminderMessage.setText(reminder_Message);
    }

    /**
     * Creates a new ReminderWindow.
     */
    public ReminderWindow(Url url, Note note) {
        this(new Stage(), url, note);
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
        logger.fine("Showing reminder page about the application.");
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
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(reminderUrl);
        clipboard.setContent(url);
    }
}

