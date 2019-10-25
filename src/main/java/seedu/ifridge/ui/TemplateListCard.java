package seedu.ifridge.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.ifridge.model.food.UniqueTemplateItems;

/**
 * An UI component that displays information of a {@code Template}.
 */
public class TemplateListCard extends UiPart<Region> {

    private static final String FXML = "TemplateListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on GroceryList level 4</a>
     */

    public final UniqueTemplateItems template;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;

    public TemplateListCard(UniqueTemplateItems template, int displayedIndex) {
        super(FXML);
        this.template = template;
        id.setText(displayedIndex + ". ");
        name.setText(template.getName().fullName);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TemplateListCard)) {
            return false;
        }

        // state check
        TemplateListCard card = (TemplateListCard) other;
        return id.getText().equals(card.id.getText())
                && template.equals(card.template);
    }
}
