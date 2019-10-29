package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.card.ExpiringCard;

/**
 * Controller for a ExpiryDisplay page
 */
public class ExpiryDisplay extends UiPart<Stage> {

    private static final String EXPIRY_MESSAGE = "The following card(s) are expiring soon:\n";

    private static final Logger logger = LogsCenter.getLogger(ExpiryDisplay.class);
    private static final String FXML = "ExpiryDisplay.fxml";

    private ObservableList<ExpiringCard> expiringCards;

    @FXML
    private Label expiryMessage;

    @FXML
    private StackPane expiringCardListPlaceholder;

    /**
     * Creates a new ExpiryDisplay.
     *
     * @param root Stage to use as the root of the ExpiryDisplay.
     */
    public ExpiryDisplay(Stage root, ObservableList<ExpiringCard> expiringCards) {
        super(FXML, root);
        this.expiringCards = expiringCards;
        expiryMessage.setText(EXPIRY_MESSAGE);
        ExpiringCardListPanel expiringCardListPanel = new ExpiringCardListPanel(expiringCards);
        expiringCardListPlaceholder.getChildren().addAll(expiringCardListPanel.getRoot());
    }

    /**
     * Creates a new ExpiryDisplay.
     */
    public ExpiryDisplay(ObservableList<ExpiringCard> expiringCards) {
        this(new Stage(), expiringCards);
    }

    public boolean hasCards() {
        return !expiringCards.isEmpty();
    }

    /**
     * Shows the expiry display window.
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
        logger.fine("Displaying card expiry.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the expiry display window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the expiry display window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the expiry display window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}

