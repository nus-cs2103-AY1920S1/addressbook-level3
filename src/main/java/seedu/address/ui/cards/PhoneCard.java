package seedu.address.ui.cards;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.phone.Phone;
import seedu.address.ui.UiPart;


/**
 * An UI component that displays information of a {@code Phone}
 */
public class PhoneCard extends UiPart<Region> {

    private static final String FXML = "PhoneListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Phone phone;

    // No price displayed here for phone
    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label colour;
    @FXML
    private Label cost;
    @FXML
    private Label capacity;
    @FXML
    private Label brand;
    @FXML
    private Label id;
    @FXML
    private FlowPane tags;
    @FXML
    private Label identityNumber;
    @FXML
    private Label serialNumber;

    public PhoneCard(Phone phone, int displayedIndex) {
        super(FXML);
        this.phone = phone;
        id.setText(displayedIndex + ". ");
        brand.setText(phone.getBrand().value);
        capacity.setText(phone.getCapacity().value);
        colour.setText(phone.getColour().value);
        cost.setText(phone.getCost().value);
        name.setText(phone.getPhoneName().fullName);
        phone.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        identityNumber.setText(phone.getIdentityNumber().value);
        serialNumber.setText(phone.getSerialNumber().value);

    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PhoneCard)) {
            return false;
        }

        // state check
        PhoneCard card = (PhoneCard) other;
        return id.getText().equals(card.id.getText())
                && phone.equals(card.phone);
    }

}
