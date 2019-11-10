package organice.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import organice.model.person.MatchedPatient;

/**
 * An UI component that displays information of a {@code Patient}.
 */
public class MatchedPatientCard extends UiPart<Region> {

    private static final String FXML = "MatchedPatientCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final MatchedPatient patient;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label nric;
    @FXML
    private Label age;
    @FXML
    private Label priority;
    @FXML
    private Label bloodType;
    @FXML
    private Label tissueType;
    @FXML
    private Label organ;
    @FXML
    private Label doctorInCharge;
    @FXML
    private Label match;

    public MatchedPatientCard(MatchedPatient patient, int displayedIndex) {
        super(FXML);
        this.patient = patient;
        id.setText(displayedIndex + ". ");
        name.setText(patient.getName().fullName);
        phone.setText("Phone Number: " + patient.getPhone().value);
        nric.setText("Nric: " + patient.getNric().value);
        age.setText("Age: " + patient.getAge().value);
        priority.setText("Priority: " + patient.getPriority().value);
        bloodType.setText("Blood type: " + patient.getBloodType().value);
        tissueType.setText("Tissue type: " + patient.getTissueType().value);
        organ.setText("Organ: " + patient.getOrgan().value);
        doctorInCharge.setText("Doctor in Charge: " + patient.getDoctorInCharge().value);
        match.setText("Matches: " + patient.getNumberOfMatches());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MatchedPatientCard)) {
            return false;
        }

        // state check
        MatchedPatientCard card = (MatchedPatientCard) other;
        return id.getText().equals(card.id.getText())
                && patient.equals(card.patient);
    }
}
