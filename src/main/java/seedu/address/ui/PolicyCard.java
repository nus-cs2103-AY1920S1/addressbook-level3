package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.policy.EndAge;
import seedu.address.model.policy.Policy;

/**
 * An UI component that displays information of a {@code Policy}.
 */
public class PolicyCard extends UiPart<Region> {

    private static final String FXML = "PolicyListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Policy policy;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label coverage;
    @FXML
    private Label price;
    @FXML
    private Label startAge;
    @FXML
    private Label endAge;
    @FXML
    private FlowPane criteria;
    @FXML
    private FlowPane tags;

    public PolicyCard(Policy policy, int displayedIndex) {
        super(FXML);
        this.policy = policy;
        id.setText(displayedIndex + ". ");
        name.setText(policy.getName().policyName);
        description.setText(policy.getDescription().description);
        coverage.setText(policy.getCoverage().toString());
        price.setText(policy.getPrice().price);
        if (!policy.getStartAge().age.equals("0")) {
            startAge.setText(policy.getStartAge().age);
        }
        if (!policy.getEndAge().age.equals(EndAge.INFINITY)) {
            endAge.setText(policy.getEndAge().age);
        }
        policy.getCriteria().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        policy.getTags().stream()
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
        if (!(other instanceof PolicyCard)) {
            return false;
        }

        // state check
        PolicyCard card = (PolicyCard) other;
        return id.getText().equals(card.id.getText())
                && policy.equals(card.policy);
    }
}
