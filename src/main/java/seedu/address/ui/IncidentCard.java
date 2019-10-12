package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.incident.Incident;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class IncidentCard extends UiPart<Region> {

    private static final String FXML = "IncidentListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Incident incident;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label incidentId;
    @FXML
    private Label incidentlocation;
    @FXML
    private Label dateTime;
    @FXML
    private Label operator;

    public IncidentCard(Incident incident, int displayedIndex) {
        super(FXML);
        this.incident = incident;
        id.setText(displayedIndex + ". ");
        incidentId.setText("Incident ID: " + incident.getIncidentId().getId());
        incidentlocation.setText("District: " + String.valueOf(incident.getLocation().districtNum));
        dateTime.setText("DateTime of Report: " + incident.getDateTime().toString());
        operator.setText("Operator on Call: " + incident.getOperator().getName().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        IncidentCard card = (IncidentCard) other;
        return id.getText().equals(card.id.getText())
                && incident.equals(card.incident);
    }
}
