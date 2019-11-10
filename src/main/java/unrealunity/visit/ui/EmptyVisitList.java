package unrealunity.visit.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import unrealunity.visit.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class EmptyVisitList extends UiPart<Stage> {

    public static final String EMPTY_MESSAGE = "Patient has no past records!";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "EmptyVisitListWindow.fxml";

    @FXML
    private Button backButton;

    @FXML
    private Label message;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public EmptyVisitList(Stage root) {
        super(FXML, root);
        message.setText(EMPTY_MESSAGE);
    }

    /**
     * Creates a new HelpWindow.
     */
    public EmptyVisitList() {
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
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void back() {
        this.hide();
    }
}
