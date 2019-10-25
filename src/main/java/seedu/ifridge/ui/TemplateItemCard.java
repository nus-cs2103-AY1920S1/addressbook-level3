package seedu.ifridge.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.ifridge.model.food.TemplateItem;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class TemplateItemCard extends UiPart<Region> {

    private static final String FXML = "TemplateItemCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on GroceryList level 4</a>
     */

    public final TemplateItem templateItem;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label amount;
    @FXML
    private Label id;

    public TemplateItemCard(TemplateItem templateItem, int displayedIndex) {
        super(FXML);
        this.templateItem = templateItem;
        id.setText(displayedIndex + ". ");
        name.setText(templateItem.getName().fullName);
        amount.setText(templateItem.getAmount().fullAmt);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TemplateItemCard)) {
            return false;
        }

        // state check
        TemplateItemCard card = (TemplateItemCard) other;
        return id.getText().equals(card.id.getText())
                && templateItem.equals(card.templateItem);
    }
}
