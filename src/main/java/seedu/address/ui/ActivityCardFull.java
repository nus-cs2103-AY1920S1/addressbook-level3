package seedu.address.ui;

import java.util.Comparator;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import javafx.scene.layout.VBox;
import seedu.address.model.activity.Activity;
import seedu.address.model.contact.Contact;

/**
 * An UI component that displays information of a {@code Contact}.
 */
public class ActivityCardFull extends UiPart<Region> {

    private static final String FXML = "ActivityListCardFull.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Planner level 4</a>
     */

    public final Activity activity;

    @FXML
    private VBox labelPane1;
    @FXML
    private HBox labelPane2;
    @FXML
    private VBox labelPane3;
    @FXML
    private Label description;
    @FXML
    private Label name;
    @FXML
    private Label cost;
    @FXML
    private Label priority;
    @FXML
    private Label duration;
    @FXML
    private Label id;
    @FXML
    private FlowPane tags;
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

    public ActivityCardFull(Activity activity, int displayedIndex, String description) {
        super(FXML);
        this.activity = activity;
        id.setText(displayedIndex + ". ");
        name.setText(activity.getName().toString());

        if (description.equals("")) {
            hideLabel(this.description);
        } else {
            this.description.setText(description);
        }
        if (activity.getCost().isPresent()) {
            cost.setText("Cost: $" + activity.getCost().get());
        } else {
            hideLabel(cost);
        }

        priority.setText("Priority: " + activity.getPriority());
        duration.setText("Duration: " + activity.getDuration() + "mins");
        address.setText("Address: " + activity.getAddress());
        activity.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        setTextForContact();
    }

    private void setTextForContact() {
        Optional<Contact> optionalContact = activity.getContact();
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
        hideLabel(contactName);
        hideLabel(contactPhone);
        setEmptyTextForContactAddress();
        setEmptyTextForContactEmail();
    }

    private void setEmptyTextForContactAddress() {
        hideLabel(contactAddress);
    }

    private void setEmptyTextForContactEmail() {
        hideLabel(contactEmail);
    }

    private void hideLabel(Control control) {
        labelPane1.getChildren().remove(control);
        labelPane2.getChildren().remove(control);
        labelPane3.getChildren().remove(control);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ActivityCardFull)) {
            return false;
        }

        // state check
        ActivityCardFull card = (ActivityCardFull) other;
        return id.getText().equals(card.id.getText())
                && activity.equals(card.activity);
    }
}
