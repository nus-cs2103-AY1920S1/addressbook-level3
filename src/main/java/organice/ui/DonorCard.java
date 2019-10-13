package organice.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import organice.model.person.Donor;

/**
 * An UI component that displays information of a {@code Donor}.
 */
public class DonorCard extends UiPart<Region> {

    private static final String FXML = "DonorListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Donor donor;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label nric;
    @FXML
    private Label type;
    @FXML
    private Label age;

    public DonorCard(Donor donor, int displayedIndex) {
        super(FXML);
        this.donor = donor;
        id.setText(displayedIndex + ". ");
        name.setText(donor.getName().fullName);
        phone.setText("Phone Number: " + donor.getPhone().value);
        nric.setText(donor.getNric().value);
        type.setText(donor.getType().value);
        age.setText("Age: " + donor.getAge().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DonorCard)) {
            return false;
        }

        // state check
        DonorCard card = (DonorCard) other;
        return id.getText().equals(card.id.getText())
            && donor.equals(card.donor);
    }
}
