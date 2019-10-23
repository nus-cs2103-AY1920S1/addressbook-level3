package seedu.address.ui.card;

import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

public abstract class Card extends UiPart<Region> {
    public Card(String fxml) {
        super(fxml);
    }
}
