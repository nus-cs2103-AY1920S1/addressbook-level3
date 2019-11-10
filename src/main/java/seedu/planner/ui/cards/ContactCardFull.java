package seedu.planner.ui.cards;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.planner.model.contact.Contact;
import seedu.planner.ui.UiPart;

//@@author 1nefootstep
/**
 * An UI component that displays information of a {@code Contact}.
 */
public class ContactCardFull extends UiPart<Region> {

    private static final String FXML = "ContactListCardFull.fxml";

    public final Contact contact;

    @FXML
    private HBox cardPane;
    @FXML
    private VBox labelPane1;
    @FXML
    private Label description;
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

    public ContactCardFull(Contact contact, int displayedIndex, String description) {
        super(FXML);
        this.contact = contact;

        id.setText(displayedIndex + ". ");
        name.setText(contact.getName().toString());
        phone.setText("Phone: " + contact.getPhone().value);

        if (description.equals("")) {
            hideLabel(this.description);
        } else {
            this.description.setText(description);
        }

        if (contact.getAddress().isPresent()) {
            address.setText("Address: " + contact.getAddress().get().value);
        } else {
            hideLabel(address);
        }

        if (contact.getEmail().isPresent()) {
            email.setText("Email: " + contact.getEmail().get().value);
        } else {
            hideLabel(email);
        }
        contact.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    private void hideLabel(Control control) {
        labelPane1.getChildren().remove(control);
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
