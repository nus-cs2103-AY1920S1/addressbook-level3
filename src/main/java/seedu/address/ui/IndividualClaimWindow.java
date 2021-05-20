package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.claim.Claim;

//@@author{lawncegoh}
/**
 * Controller for a individual claimHelpWindow.fxml
 */
public class IndividualClaimWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(IndividualClaimWindow.class);
    private static final String FXML = "IndividualClaimWindow.fxml";

    private static Claim claim;

    @FXML
    private Label name;

    @FXML
    private Label date;

    @FXML
    private Label amount;

    @FXML
    private Label description;

    @FXML
    private VBox box;

    @FXML
    private Stage root;

    /**
     * Creates a new Window.
     *
     * @param root Stage to use as the root of the IndividualClaimWindow.
     */
    public IndividualClaimWindow(Stage root, Claim claim) {
        super(FXML, root);
        windowSetup();
        name.setText("Name: " + claim.getName().toString());
        date.setText("Date: " + claim.getDate().toString());
        amount.setText("Amount: $" + claim.getAmount().toString());
        description.setText("Description: " + claim.getDescription().toString());
    }

    /**
     * Creates a new IndividualClaimWindow.
     */
    public IndividualClaimWindow(Claim claim) {
        this(new Stage(), claim);
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
     * Sets up settings for the individual claim window
     */
    public void windowSetup() {
        name.setAlignment(Pos.CENTER);
        date.setAlignment(Pos.CENTER);
        amount.setAlignment(Pos.CENTER);
        description.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color:POWDERBLUE");
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
     * Closes the window
     */
    @FXML
    private void closeAction() {
        root.close();
    }


}
