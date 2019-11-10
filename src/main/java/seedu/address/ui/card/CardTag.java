package seedu.address.ui.card;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

//@@author Kyzure
/**
 * Represents a tag of a card
 */
public class CardTag extends UiPart<Region> {

    private static final String FXML = "CardTag.fxml";
    private static final double LETTER_LENGTH = 1.0;

    private double tagWidth = 0;

    private String tag;

    @FXML
    private Label cardTag;

    public CardTag(String tag) {
        super(FXML);
        this.tag = tag;
        cardTag.setText(tag);
    }

    /**
     * Change the color - used for tasks - both undone and done.
     * @param color The given color.
     */
    public void changeColor(String color) {
        cardTag.setStyle("-fx-background-color: " + color);
    }

    public double getWidth() {
        return tag.length() * LETTER_LENGTH;
    }
}
