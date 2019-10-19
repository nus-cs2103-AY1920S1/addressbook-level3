package seedu.address.ui;

import java.util.Comparator;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;

/**
 * Displays information selected by the user.
 */
public class DisplayPanel extends UiPart<Region> {
    private static final String FXML = "DisplayPanel.fxml";

    public final String policyHeader = "Policies";

    private final Logger logger = LogsCenter.getLogger(DisplayPanel.class);

    private Person person;

    private Policy policy;

    private boolean isPerson;

    private boolean isPolicy;

    @FXML
    private VBox informationHolder;

    public DisplayPanel() {
        super(FXML);
    }

    public DisplayPanel(Person person) {
        super(FXML);
        this.person = person;
        this.isPerson = true;
        this.isPolicy = false;
        setInfo();
    }

    public DisplayPanel(Policy policy) {
        super(FXML);
        this.policy = policy;
        isPerson = false;
        isPolicy = true;
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
