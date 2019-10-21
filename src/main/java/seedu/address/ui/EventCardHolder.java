package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * An UI component that displays information of a {@code Event}.
 */
public class EventCardHolder extends UiPart<Region> {

    private static final String FXML = "EventCardHolder.fxml";

    private EventCard eventCard;

    @FXML
    private VBox timeBox;

    /**
     * Constructor for the EventCardHolder.
     */

    public EventCardHolder() {
        super(FXML);
    }

    public void addEvent(EventCard eventCard) {
        this.timeBox.getChildren().add(eventCard.getRoot());
    }

    public void removeEventCards() {
        this.timeBox.getChildren().clear();
    }

    public Double getHeight() {
        return timeBox.getHeight();
    }
}
