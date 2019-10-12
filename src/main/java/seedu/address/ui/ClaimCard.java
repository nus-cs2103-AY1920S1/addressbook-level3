package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.claim.Claim;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class ClaimCard extends UiPart<Region> {

    private static final String FXML = "ClaimListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Claim claim;

    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label amount;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private FlowPane tags;

    public ClaimCard(Claim claim, int displayedIndex) {
        super(FXML);
        this.claim = claim;
        id.setText(displayedIndex + ". ");
        description.setText(claim.getDescription().text);
        amount.setText(claim.getAmount().value);
        name.setText(claim.getName().fullName);
        phone.setText(claim.getPhone().value);
        claim.getTags().stream()
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
        if (!(other instanceof ClaimCard)) {
            return false;
        }

        // state check
        ClaimCard card = (ClaimCard) other;
        return id.getText().equals(card.id.getText())
                && claim.equals(card.claim);
    }
}
