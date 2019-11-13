package seedu.planner.ui.cards;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.planner.model.contact.Contact;
import seedu.planner.ui.UiPart;

/**
 * An UI component that displays information of a {@code Contact}.
 */
public class ContactCardSmall extends UiPart<Region> {

    private static final String FXML = "ContactListCardSmall.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Planner level 4</a>
     */

    public final Contact contact;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;

    public ContactCardSmall(Contact contact, int displayedIndex) {
        super(FXML);
        this.contact = contact;
        id.setText(displayedIndex + ". ");
        name.setText(contact.getName().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContactCardSmall)) {
            return false;
        }

        // state check
        ContactCardSmall card = (ContactCardSmall) other;
        return id.getText().equals(card.id.getText())
                && contact.getName().equals(card.contact.getName());
    }
}
