package seedu.address.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.claim.Claim;
import seedu.address.model.contact.Contact;

/**
 * Controller for a individual contactWindow.fxml
 */
public class IndividualContactWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(IndividualContactWindow.class);
    private static final String FXML = "IndividualContactWindow.fxml";

    private static Claim claim;

    @FXML
    private Label name;

    @FXML
    private Label number;

    @FXML
    private FlowPane claims;

    @FXML
    private FlowPane tags;


    /**
     * Creates a new Window.
     *
     * @param root Stage to use as the root of the IndividualClaimWindow.
     */
    public IndividualContactWindow(Stage root, Contact contact) {
        super(FXML, root);
        name.setText("Name: " + contact.getName().toString());
        number.setText("Contact: " + contact.getPhone().toString());
        contact.getClaims().stream()
                .forEach(id -> claims.getChildren().add(
                        new Label(String.valueOf(Integer.parseInt(id.value) - 1) + " ")));
        contact.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Creates a new IndividualContactWindow.
     */
    public IndividualContactWindow(Contact contact) {
        this(new Stage(), contact);
    }

    /**
     * Shows the IndividualContact window.
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
        logger.fine("Showing the individual contact card.");

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
