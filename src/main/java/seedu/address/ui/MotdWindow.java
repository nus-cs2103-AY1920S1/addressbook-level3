package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Reminder;

/**
 * Controller for a motd page
 */
public class MotdWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(MotdWindow.class);
    private static final String FXML = "MotdWindow.fxml";

    @FXML
    private Label motdMessage;

    /**
     * Creates a new MotdWindow.
     *
     * @param root Stage to use as the root of the MotdWindow.
     */
    public MotdWindow(Stage root) {
        super(FXML, root);
        String reminders = Reminder.getReminders();
        motdMessage.setText(reminders);
    }

    /**
     * Creates a new MotdWindow.
     */
    public MotdWindow() {
        this(new Stage());
    }

    /**
     * Shows the motd window.
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
        logger.fine("Showing motd.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the motd window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the motd window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the motd window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

}
