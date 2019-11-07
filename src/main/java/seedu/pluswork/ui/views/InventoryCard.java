package seedu.pluswork.ui.views;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.pluswork.model.inventory.Inventory;
import seedu.pluswork.ui.UiPart;

/**
 * An UI component that displays information of a {@code Inventory}.
 */
public class InventoryCard extends UiPart<Region> {
    private static final String FXML = "InventoryListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on ProjectDashboard level 4</a>
     */

    public final Inventory inventory;

    @FXML
    private HBox cardPane;
    @FXML
    private Label InvName;
    @FXML
    private Label id;
    @FXML
    private Label Price;
    /*
    @FXML
    private Label MemberName;
    */

    public InventoryCard(Inventory inventory, int displayedIndex) {
        super(FXML);
        this.inventory = inventory;
        id.setText(displayedIndex + ". ");
        InvName.setText(inventory.getName().fullName);
        Price.setText(inventory.getPrice().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        InventoryCard card = (InventoryCard) other;
        return id.getText().equals(card.id.getText())
                && inventory.equals(card.inventory);
    }
}
