package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seedu.address.model.policy.Policy;

/**
 * An UI component that displays information of a {@code Person} in the bin.
 */
public class BinPolicyCard extends PolicyCard {

    private static final String FXML = "BinPolicyListCard.fxml";

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

    public BinPolicyCard(Policy policy, int displayedIndex, String dateDeleted, String expiryDate) {
        super(policy, displayedIndex, FXML);
        this.dateDeleted.setText(dateDeleted);
        this.expiryDate.setText(expiryDate);
    }

}
