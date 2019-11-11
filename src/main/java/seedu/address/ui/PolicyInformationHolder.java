package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.policy.EndAge;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.StartAge;

/**
 * An UI component that displays information of a {@code policy} in the display panel.
 */
public class PolicyInformationHolder extends UiPart<Region> {

    private static final String FXML = "PolicyInformationHolder.fxml";

    private final Policy policy;

    private String nameHeader = "Name: ";
    private String descriptionHeader = "Description: ";
    private String coverageHeader = "Coverage: ";
    private String priceHeader = "Price: ";
    private String startAgeHeader = "Start Age: ";
    private String endAgeHeader = "End Age: ";

    @FXML
    private Label name;
    @FXML
    private Label description;
    @FXML
    private Label coverage;
    @FXML
    private Label price;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane criteria;
    @FXML
    private VBox informationHolder;

    public PolicyInformationHolder(Policy policy) {
        super(FXML);
        this.policy = policy;
        name.setText(nameHeader + policy.getName().policyName);
        description.setText(descriptionHeader + policy.getDescription().description);
        coverage.setText(coverageHeader + policy.getCoverage().coverage);
        price.setText(priceHeader + policy.getPrice().price);
        if (!policy.getStartAge().age.equals(StartAge.AGE_ZERO)) {
            informationHolder.getChildren().add(new Label(startAgeHeader + policy.getStartAge().age));
        } else {
            informationHolder.getChildren().add(new Label(StartAge.MESSAGE_NO_MINIMUM_AGE));
        }
        if (!policy.getEndAge().age.equals(EndAge.AGE_INFINITY)) {
            informationHolder.getChildren().add(new Label(endAgeHeader + policy.getEndAge().age));
        } else {
            informationHolder.getChildren().add(new Label(EndAge.MESSAGE_NO_MAXIMUM_AGE));
        }
        policy.getCriteria().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> criteria.getChildren().add(new Label(tag.tagName)));
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
        if (!(other instanceof PolicyInformationHolder)) {
            return false;
        }

        // state check
        PolicyInformationHolder card = (PolicyInformationHolder) other;
        return policy.equals(card.policy);
    }
}
