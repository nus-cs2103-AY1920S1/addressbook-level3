package tagline.ui.contact;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import tagline.model.contact.Contact;
import tagline.ui.UiPart;

/**
 * A UI component that displays information of a {@code Contact}.
 */
public class ContactListCard extends UiPart<Region> {

    private static final String FXML = "ContactListCard.fxml";
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Contact contact;

    @FXML
    private VBox contactListInternalPane;
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
    private Label description;

    public ContactListCard(Contact contact) {
        super(FXML);
        this.contact = contact;
        name.setText(contact.getName().fullName);
        id.setText("ID: " + contact.getContactId());

        setLabelText(phone, contact.getPhone().value);
        setLabelText(address, contact.getAddress().value);
        setLabelText(email, contact.getEmail().value);
        setLabelText(description, contact.getDescription().value);
    }

    /**
     * Sets the content in a {@code label} to {@code text}.
     * If {@code text} is empty, hides the {@code label}.
     */
    private void setLabelText(Label label, String text) {
        if (text.isEmpty()) {
            label.setVisible(false);
            contactListInternalPane.getChildren().remove(label);
        } else {
            label.setText(text);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContactListCard)) {
            return false;
        }

        // state check
        ContactListCard card = (ContactListCard) other;
        return contact.equals(card.contact);
    }
}
