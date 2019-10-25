package seedu.ifridge.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.ifridge.model.food.Food;
import seedu.ifridge.model.food.GroceryItem;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class IsExpiringGroceryCard extends UiPart<Region> {
    private static final String FXML = "IsExpiringGroceryCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on GroceryList level 4</a>
     */

    public final Food food;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label amount;
    @FXML
    private Label expiryDate;
    @FXML
    private FlowPane tags;

    public IsExpiringGroceryCard(Food food, int displayedIndex) {
        super(FXML);
        this.food = food;
        id.setText(displayedIndex + ". ");
        name.setText(food.getName().fullName);
        expiryDate.setText(((GroceryItem) food).getExpiryDate().expiryDate);
        amount.setText(food.getAmount().fullAmt);
        GroceryItem groceryItem = (GroceryItem) food;
        groceryItem.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IsExpiringGroceryCard)) {
            return false;
        }

        // state check
        IsExpiringGroceryCard card = (IsExpiringGroceryCard) other;
        return id.getText().equals(card.id.getText())
                && food.equals(card.food);
    }
}
