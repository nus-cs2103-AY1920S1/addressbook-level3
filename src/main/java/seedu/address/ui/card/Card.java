package seedu.address.ui.card;

import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

//@@author Kyzure
/**
 * Represents a Card to show an event or task.
 */
public abstract class Card extends UiPart<Region> {
    /**
     * Constructor for the Card.
     * @param fxml The FXML of the EventCard or TaskCard.
     */
    public Card(String fxml) {
        super(fxml);
    }
}
