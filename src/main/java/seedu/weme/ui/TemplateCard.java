package seedu.weme.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.weme.model.template.Template;

/**
 * An UI component that displays information of a {@code Template}.
 */
public class TemplateCard extends UiPart<Region> {

    private static final String FXML = "TemplateGridCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on address book level 4</a>
     */

    public final Template template;

    @FXML
    private HBox cardPane;
    @FXML
    private ImageView display;
    @FXML
    private Label id;
    @FXML
    private Label name;

    public TemplateCard(Template template, int displayedIndex) {
        super(FXML);
        this.template = template;
        id.setText(displayedIndex + "");
        display.setImage(new Image(template.getImagePath().toUrl().toString(), 200, 200, true, true, true));
        name.setText(template.getName().toString());
    }

    /**
     * Updates the card content except for the template image.
     * @param template the template this card is for
     * @param newIndex the new index of the this card
     */
    public void update(Template template, int newIndex) {
        id.setText(newIndex + "");
        name.setText(template.getName().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TemplateCard)) {
            return false;
        }

        // state check
        TemplateCard card = (TemplateCard) other;
        return id.getText().equals(card.id.getText())
                && template.equals(card.template);
    }
}
