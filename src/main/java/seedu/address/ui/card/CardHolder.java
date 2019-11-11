package seedu.address.ui.card;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.ui.UiPart;

//@@author Kyzure
/**
 * An UI component that displays information of a {@code Event}.
 */
public class CardHolder extends UiPart<Region> {

    private static final String FXML = "CardHolder.fxml";

    @FXML
    private VBox timeBox;

    /**
     * Constructor for the EventCardHolder.
     */

    public CardHolder() {
        super(FXML);
    }

    public void addCard(Card card) {
        this.timeBox.getChildren().add(card.getRoot());
    }

    public void removeCards() {
        this.timeBox.getChildren().clear();
    }

    public Double getHeight() {
        return timeBox.getHeight();
    }
}
