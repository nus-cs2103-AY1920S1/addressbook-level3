package seedu.address.ui.card;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;


/**
 * Represents a tag of a card
 */
public class CardTag extends UiPart<Region> {

    private static final String FXML = "CardTag.fxml";

    @FXML
    private Label cardTag;

    public CardTag(String tag) {
        super(FXML);
        cardTag.setText(tag);
    }
}
