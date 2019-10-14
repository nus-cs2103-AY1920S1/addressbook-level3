package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.entity.fridge.Fridge;

/**
 * An UI component that displays information of a {@code Body}.
 */
public class FridgeCard extends UiPart<Region> {

    private static final String FXML = "FridgeListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Fridge fridge;

    @FXML
    private HBox cardPane;
    @FXML
    private Label status;
    @FXML
    private Label id;
    @FXML
    private Label fridgeId;

    public FridgeCard(Fridge fridge, int displayedIndex) {
        super(FXML);
        this.fridge = fridge;
        id.setText(displayedIndex + ". ");
        fridgeId.setText(fridge.getIdNum().toString());
        status.setText(fridge.getFridgeStatus().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BodyCard)) {
            return false;
        }

        // state check
        FridgeCard card = (FridgeCard) other;
        return id.getText().equals(card.id.getText())
                && fridge.equals(card.fridge);
    }
}
