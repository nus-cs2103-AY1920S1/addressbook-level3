package seedu.ifridge.ui;

import static seedu.ifridge.model.food.ShoppingItem.isCompletelyBought;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.food.ShoppingItem;

/**
 * An UI component that displays information of a {@code ShoppingItem}.
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
    @FXML
    private FlowPane urgentTag;
    @FXML
    private FlowPane boughtTag;

    public ShoppingItemCard(ShoppingItem shoppingItem, int displayedIndex, ObservableList<GroceryItem> boughtList) {
        super(FXML);
        this.shoppingItem = shoppingItem;
        id.setText(displayedIndex + ". ");
        name.setText(shoppingItem.getName().fullName);
        amount.setText(shoppingItem.getAmount().fullAmt);
        name.setWrapText(true);
        id.setWrapText(true);
        amount.setWrapText(true);
        Text urgentText = new Text("Urgent!");
        urgentText.setFill(Color.RED);
        if (shoppingItem.isUrgent()) {
            urgentTag.getChildren().add(urgentText);
        }
        Text boughtText = new Text();
        if (shoppingItem.isBought()) {
            if (isCompletelyBought(shoppingItem, boughtList)) {
                boughtText = new Text("Fully Bought! (" + shoppingItem.getAmountBought(boughtList) + " Bought)");
                boughtText.setFill(Color.DARKGREEN);
            } else {
                boughtText = new Text("Partially Bought. (" + shoppingItem.getAmountBought(boughtList) + " Bought)");
                boughtText.setFill(Color.PURPLE);
            }
            boughtTag.getChildren().add(boughtText);
        }
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
