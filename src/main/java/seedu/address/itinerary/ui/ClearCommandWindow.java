package seedu.address.itinerary.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import seedu.address.commons.core.LogsCenter;
import seedu.address.itinerary.model.Model;
import seedu.address.ui.UiPart;

/**
 * Opens up a window with a warning whether the user wants to clear the whole list.
 */
public class ClearCommandWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(ClearCommandWindow.class);
    private static final String FXML = "ClearCommandWindow.fxml";
    private Model model = null;

    Notifications notificationBuilder;

    Node graphic;

    String clearSuccess = "Done! We have wiped off all your events from the face of this Earth! ( ﾟヮﾟ)";

    @FXML
    private Label clearMessage;

    @FXML
    private Button proceed;

    /**
     * Creates a new ClearWindow.
     *
     * @param root Stage to use as the root of the ClearWindow.
     */
    public ClearCommandWindow(Stage root) {
        super(FXML, root);

        String warning = "Warning! You are about the delete all the events that you have inserted"
                + " into your itinerary list. This action cannot be undone. Would you like to proceed"
                + " in clearing your whole event list?";

        clearMessage.setText(warning);
    }

    /**
     * Creates a new ClearWindow.
     */
    public ClearCommandWindow() {
        this(new Stage());
    }

    public void setModel(Model model) {
        this.model = model;
    }

    private void notification(Pos pos, Node graphic, String Text) {
        notificationBuilder = Notifications.create()
                .title("Clear Complete!")
                .text(Text)
                .graphic(graphic)
                .hideAfter(Duration.seconds(5))
                .position(pos)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Notification is clicked");
                    }
                });
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleClear() {
        model.clearEvent();
        getRoot().hide();
        notification(Pos.CENTER, graphic, clearSuccess);
        notificationBuilder.showInformation();
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
