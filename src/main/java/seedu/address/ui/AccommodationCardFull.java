package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.contact.Contact;
import seedu.address.model.itineraryitem.accommodation.Accommodation;

import java.util.Comparator;
import java.util.Optional;

/**
 * An UI component that displays information of a {@code Contact}.
 */
public class AccommodationCardFull extends UiPart<Region> {

    private static final String FXML = "AccommodationListCardFull.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Planner level 4</a>
     */

    public final Accommodation accommodation;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private FlowPane tags;
    @FXML
    private Label description;
    @FXML
    private Label address;
    @FXML
    private Label contactName;
    @FXML
    private Label contactPhone;
    @FXML
    private Label contactAddress;
    @FXML
    private Label contactEmail;

    public AccommodationCardFull(Accommodation accommodation, int displayedIndex, String description) {
        super(FXML);
        this.accommodation = accommodation;
        this.description.setText(description);
        id.setText(displayedIndex + ". ");
        name.setText(accommodation.getName().toString());
        address.setText("Address: " + accommodation.getAddress());
        accommodation.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        setTextForContact();
    }

    private void setTextForContact() {
        Optional<Contact> optionalContact = accommodation.getContact();
        if (optionalContact.isPresent()) {
            setTextForCompulsoryFieldsOfContact(optionalContact.get());
            setTextForOptionalFieldsOfContact(optionalContact.get());
        } else {
            setEmptyTextForEntireContact();
        }
    }

    private void setTextForCompulsoryFieldsOfContact(Contact contact) {
        contactName.setText("Contact name: " + contact.getName().toString());
        contactPhone.setText("Contact phone: " + contact.getPhone().toString());
    }

    private void setTextForOptionalFieldsOfContact(Contact contact) {
        contact.getAddress().ifPresentOrElse(a -> contactAddress.setText("Contact address: " + a.toString()),
                this::setEmptyTextForContactAddress);
        contact.getEmail().ifPresentOrElse(e -> contactEmail.setText("Contact email: " + e.toString()),
                this::setEmptyTextForContactEmail);
    }

    private void setEmptyTextForEntireContact() {
        contactName.setText("");
        contactPhone.setText("");
        setEmptyTextForContactAddress();
        setEmptyTextForContactEmail();
    }

    private void setEmptyTextForContactAddress() {
        contactAddress.setText("");
    }

    private void setEmptyTextForContactEmail() {
        contactEmail.setText("");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AccommodationCardFull)) {
            return false;
        }

        // state check
        AccommodationCardFull card = (AccommodationCardFull) other;
        return id.getText().equals(card.id.getText())
                && accommodation.equals(card.accommodation);
    }
}
