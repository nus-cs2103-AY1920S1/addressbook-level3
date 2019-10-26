package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person} in the bin.
 */
public class BinPersonCard extends PersonCard {

    private static final String FXML = "BinPersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private Label dateDeleted;

    @FXML
    private Label expiryDate;

    public BinPersonCard(Person person, int displayedIndex, String dateDeleted, String expiryDate) {
        super(person, displayedIndex, FXML);
        this.dateDeleted.setText("Deleted on " + dateDeleted);
        this.expiryDate.setText("Expires on " + expiryDate);
    }

}
