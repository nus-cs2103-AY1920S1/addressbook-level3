package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.contact.Contact;

/**
 * An UI component that displays information of a {@code Contact}.
 */
public class ContactCardFull extends UiPart<Region> {

    private static final String FXML = "ContactListCardFull.fxml";

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
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    public ContactCardFull(Contact contact, int displayedIndex) {
        super(FXML);
        this.contact = contact;
        id.setText(displayedIndex + ". ");
        name.setText(contact.getName().toString());
        phone.setText(contact.getPhone().value);
        address.setText(contact.getAddress().isPresent() ? contact.getAddress().get().value : "");
        email.setText(contact.getEmail().isPresent() ? contact.getEmail().get().value : "");
        contact.getTags().stream()
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
        if (!(other instanceof ContactCardFull)) {
            return false;
        }

        // state check
        ContactCardFull card = (ContactCardFull) other;
        return id.getText().equals(card.id.getText())
                && contact.equals(card.contact);
    }
}
