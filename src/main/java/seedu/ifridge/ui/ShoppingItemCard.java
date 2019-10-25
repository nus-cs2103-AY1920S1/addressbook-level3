package seedu.ifridge.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.ifridge.model.food.ShoppingItem;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class ShoppingItemCard extends UiPart<Region> {

    private static final String FXML = "ShoppingListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on GroceryList level 4</a>
     */

    public final ShoppingItem shoppingItem;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label amount;
    @FXML
    private Label id;

    public ShoppingItemCard(ShoppingItem shoppingItem, int displayedIndex) {
        super(FXML);
        this.shoppingItem = shoppingItem;
        id.setText(displayedIndex + ". ");
        name.setText(shoppingItem.getName().fullName);
        amount.setText(shoppingItem.getAmount().fullAmt);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ShoppingItemCard)) {
            return false;
        }

        // state check
        ShoppingItemCard card = (ShoppingItemCard) other;
        return id.getText().equals(card.id.getText())
                && shoppingItem.equals(card.shoppingItem);
    }
}
