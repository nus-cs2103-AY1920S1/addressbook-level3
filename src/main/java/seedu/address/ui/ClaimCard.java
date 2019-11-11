package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.claim.Claim;

//@@author{weigenie}
/**
 * An UI component that displays information of a {@code FinSec}.
 */
public class ClaimCard extends UiPart<Region> {

    private static final String FXML = "ClaimListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on FinSec level 4</a>
     */

    public final Claim claim;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label claimId;
    @FXML
    private Label date;
    @FXML
    private Label status;
    @FXML
    private Label description;
    @FXML
    private Label amount;
    @FXML
    private Label name;
    @FXML
    private FlowPane tags;

    public ClaimCard(Claim claim, int displayedIndex) {
        super(FXML);
        this.claim = claim;
        setId(displayedIndex + ". ");
        setClaimId(claim.getId().toString());
        setDate(claim.getDate().text);
        setStatus(claim.getStatus().toString());
        setDescription(claim.getDescription().text);
        setAmount(claim.getAmount().value);
        setName(claim.getName().fullName);
        setTags();
    }

    void setId(String text) {
        id.setText(text);
    }

    void setClaimId(String text) {
        claimId.setText("Claim ID: #" + text);
    }

    void setDate(String text) {
        date.setText(text);
    }

    void setStatus(String text) {
        status.setText(text);
    }

    void setDescription(String text) {
        description.setText(text);
    }

    void setAmount(String text) {
        amount.setText("Amount: $" + text);
    }

    void setName(String text) {
        name.setText("Claimant: " + text);
    }

    void setTags() {
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
