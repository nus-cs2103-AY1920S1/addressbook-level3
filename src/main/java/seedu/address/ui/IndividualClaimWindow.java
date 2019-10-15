package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.claim.Claim;

import java.util.logging.Logger;

/**
 * Controller for a individual claimHelpWindow.fxml
 */
public class IndividualClaimWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(IndividualClaimWindow.class);
    private static final String FXML = "IndividualClaimWindow.fxml";

    public final Claim claim;

    @FXML
    private Label name;

    @FXML
    private Label contact;

    @FXML
    private Label amount;

    @FXML
    private Label description;

    /**
     * Creates a new Window.
     *
     * @param root Stage to use as the root of the IndividualClaimWindow.
     */
    public IndividualClaimWindow(Stage root, Claim claim) {
        super(FXML, root);
        this.claim = claim;
        name.setText();
        contact.setText();
        amount.setText();
        description.setText();
    }

    /**
     * Creates a new IndividualClaimWindow.
     */
    public IndividualClaimWindow() {
        this(new Stage());
    }

    /**
     * Shows the IndividualClaim window.
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
        logger.fine("Showing the individual claim card.");

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
