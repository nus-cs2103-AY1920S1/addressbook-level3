package seedu.address.ui.card;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

//@@author Kyzure
/**
 * Represents a tag of a card
 */
public class CardTagline extends UiPart<Region> {

    public static final double MAX_WIDTH = 32.0;
    private static final String FXML = "CardTagline.fxml";

    private double taglineWidth;

    @FXML
    private HBox tagline;

    public CardTagline() {
        super(FXML);
        taglineWidth = 0;
    }

    /**
     * Returns true if CardTag successfully added, and false otherwise.
     *
     * @param cardTag The given CardTag.
     * @return Returns true if CardTag successfully added, and false otherwise.
     */
    public boolean isTagAdded(CardTag cardTag) {
        if (taglineWidth + cardTag.getWidth() < MAX_WIDTH) {
            tagline.getChildren().add(cardTag.getRoot());
            taglineWidth += cardTag.getWidth();
            return true;
        }
        return false;
    }

    public void addSingleTag(CardTag cardTag) {
        tagline.getChildren().add(cardTag.getRoot());
    }

}
