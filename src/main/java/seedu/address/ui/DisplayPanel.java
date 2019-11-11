package seedu.address.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.model.visual.DisplayIndicator;

// TODO: Remove policy pop from displaypanel
/**
 * Displays information selected by the user.
 */
public class DisplayPanel extends UiPart<Region> {
    private static final String FXML = "DisplayPanel.fxml";

    public final String policyHeader = "Policies";

    private final Logger logger = LogsCenter.getLogger(DisplayPanel.class);

    private Person person;

    private Policy policy;

    private DisplayIndicator displayIndicator;

    private boolean isPerson;

    private boolean isPolicy;

    private boolean isDisplayIndicator;

    @FXML
    private VBox informationHolder;

    public DisplayPanel(Person person) {
        super(FXML);
        this.person = person;
        this.isPerson = true;
        this.isPolicy = false;
        this.isDisplayIndicator = false;
        setInfo();
    }

    public DisplayPanel(Policy policy) {
        super(FXML);
        this.policy = policy;
        isPerson = false;
        isPolicy = true;
        isDisplayIndicator = false;
        setInfo();
    }

    public DisplayPanel() {
        super(FXML);
        this.policy = policy;
        isPerson = false;
        isPolicy = false;
        isDisplayIndicator = false;
        setInfo();
    }

    private void setInfo() {
        if (isPerson) {
            informationHolder.getChildren().add(new PersonInformationHolder(person).getRoot());
            if (person.getPolicies().size() != 0) {
                person.getPolicies().stream()
                    .sorted(Comparator.comparing(policy -> policy.getName().policyName))
                    .forEach(policy -> {
                        PolicyInformationHolder policyInfo = new PolicyInformationHolder(policy);
                        informationHolder.getChildren().add(policyInfo.getRoot());
                    });
            }
        } else if (isPolicy) {
            informationHolder.getChildren().add(new PolicyInformationHolder(policy).getRoot());
            // todo: add eligible persons
        }
    }

}
