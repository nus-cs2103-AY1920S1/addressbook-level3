package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.incident.CallerNumber;
import seedu.address.model.incident.Description;
import seedu.address.model.incident.Incident;

/**
 * An UI component that displays information of an {@code Incident}.
 */
public class IncidentCard extends UiPart<Region> {

    private static final String FXML = "IncidentListCard.fxml";

    /**
     * Note: Certain keywords such as "district" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     */

    public final Incident incident;

    @FXML
    private HBox cardPane;
    @FXML
    private Label operator;
    @FXML
    private Label incidentDistrict;
    @FXML
    private Label dateTime;
    @FXML
    private Label id;
    @FXML
    private Label incidentId;
    @FXML
    private Label callerNumber;
    @FXML
    private Label description;
    @FXML
    private Label status;
    @FXML
    private Label vehicle;

    public IncidentCard(Incident incident, int displayedIndex) {
        super(FXML);
        this.incident = incident;
        id.setText(displayedIndex + ". ");
        incidentId.setText("Incident #" + incident.getIncidentId().getId());
        dateTime.setText("created on " + incident.getIncidentDateTime().toDisplayString()); // get the display format
        operator.setText("Last modified by: " + incident.getOperator().getName().toString() + " ("
                + incident.getOperator().getUsername() + ")");
        incidentDistrict.setText("District: " + incident.getDistrict().toString());
        vehicle.setText("Vehicle: " + incident.getVehicle().toDisplayString());

        // fields not filled for draft
        CallerNumber incidentCallerNumber = incident.getCallerNumber();
        if (incidentCallerNumber == null) {
            callerNumber.setText("");
        } else {
            callerNumber.setText("Caller: " + incidentCallerNumber.toString());
        }

        Description incidentDesc = incident.getDesc();
        if (incidentDesc == null) {
            description.setText("");
        } else {
            description.setText(incidentDesc.toString());
        }

        // status label setup
        String statusString = incident.getStatus().toString();
        status.setText(statusString);
        status.setStyle(getBackgroundColourBasedOnValue(statusString) + "; -fx-font-weight: bold; -fx-border-width: 2.5"
                + "; -fx-border-color: black");
    }

    /**
     * Returns fxml background colour string based on the status of the incident.
     * Incomplete drafts - CRIMSON
     * Complete drafts - SANDYBROWN
     * Submitted reports - DARKGREEN
     * @return
     */
    private String getBackgroundColourBasedOnValue(String targetString) {
        switch(targetString) {
        case "Incomplete Draft":
            return "-fx-background-color:CRIMSON";
        case "Complete Draft":
            return "-fx-background-color:SANDYBROWN";
        case "Submitted":
            return "-fx-background-color:DARKGREEN";
        default:
            return "-fx-background-color:BLACK";
        }

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
