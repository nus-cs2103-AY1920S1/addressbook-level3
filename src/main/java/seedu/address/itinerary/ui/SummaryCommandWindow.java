package seedu.address.itinerary.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.itinerary.model.Itinerary;
import seedu.address.ui.UiPart;

import java.util.logging.Logger;

public class SummaryCommandWindow extends UiPart<Stage> {
    Itinerary itinerary = new Itinerary();

    public final String SUMMARY_MESSAGE = "You current have " + itinerary.getEventList().size()
            + " events in your list.";

    private static final Logger logger = LogsCenter.getLogger(SummaryCommandWindow.class);
    private static final String FXML = "SummaryCommandWindow.fxml";

    @FXML
    private Label summaryMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public SummaryCommandWindow(Stage root) {
        super(FXML, root);
        summaryMessage.setText(SUMMARY_MESSAGE);
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
