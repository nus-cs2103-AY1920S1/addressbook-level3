package seedu.address.itinerary.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.itinerary.model.Itinerary;
import seedu.address.ui.UiPart;

/**
 * Opens up a window with a summary of the events in itinerary.
 */
public class SummaryCommandWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(SummaryCommandWindow.class);
    private static final String FXML = "SummaryCommandWindow.fxml";

    @FXML
    private Label summaryMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    private SummaryCommandWindow(Stage root) {
        super(FXML, root);
        Itinerary itinerary = new Itinerary();
        String summary = "You current have " + itinerary.getEventList().size()
                + " events in your list.";
        summaryMessage.setText(summary);
    }

    /**
     * Creates a new Summary Window.
     */
    public SummaryCommandWindow() {
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
        logger.fine("Showing summary page about the application.");
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
    @SuppressWarnings("unused")
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
