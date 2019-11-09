package seedu.address.ui;

import java.util.Comparator;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.entity.body.Body;

//@@author shaoyi1997
/**
 * An UI component that displays information of a {@code Body}.
 */
public class BodyCardSelected extends UiPart<Region> {

    private static final String FXML = "SelectedBodyCard.fxml";
    private static final String NO_INPUT_DISPLAY = "-";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Body body = null;

    @FXML
    private Label selectedBodyId;
    @FXML
    private Label selectedBodyName;
    @FXML
    private Label selectedBodySex;
    @FXML
    private Label selectedBodyNric;
    @FXML
    private Label selectedBodyReligion;
    @FXML
    private Label selectedBodyDateOfAdmission;
    @FXML
    private Label selectedBodyDateOfDeath;
    @FXML
    private Label selectedBodyDateOfBirth;
    @FXML
    private FlowPane organs;
    @FXML
    private Label selectedBodyNokName;
    @FXML
    private Label selectedBodyNokRelationship;
    @FXML
    private Label selectedBodyNokPhone;
    @FXML
    private Label selectedBodyCauseOfDeath;


    public BodyCardSelected(ObservableValue<Body> selectedBody) {
        super(FXML);
        // Load body card when selected card changes.
        selectedBody.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                setUnselectedDetails();
                return;
            }
            setSelectedBodyDetails(newValue);
        });
    }

    private void setUnselectedDetails() {
        selectedBodyName.setText("Name");
        selectedBodyId.setText("Body ID");
        selectedBodySex.setText("Sex");
        selectedBodyDateOfAdmission.setText("Date of Admission:");
        selectedBodyDateOfDeath.setText("Date of Death:");
        selectedBodyDateOfBirth.setText("Date of Birth");
        selectedBodyNric.setText("NRIC");
        selectedBodyReligion.setText("Religion");
        selectedBodyNokName.setText("NOK Name");
        selectedBodyNokRelationship.setText("NOK Relationship");
        selectedBodyNokPhone.setText("NOK Phone");
        selectedBodyCauseOfDeath.setText("COD");
        organs.getChildren().clear();
    }

    private void setSelectedBodyDetails(Body newValue) {
        selectedBodyName.setText(newValue.getName().toString());
        selectedBodyId.setText(newValue.getIdNum().toString());
        selectedBodySex.setText(newValue.getSex().toString());
        selectedBodyDateOfAdmission.setText(newValue.getDateOfAdmission().toString());
        selectedBodyDateOfDeath.setText(toStringNullable(newValue.getDateOfDeath().orElse(null)));
        selectedBodyDateOfBirth.setText(toStringNullable(newValue.getDateOfBirth().orElse(null)));
        selectedBodyNric.setText(toStringNullable(newValue.getNric().orElse(null)));
        selectedBodyReligion.setText(toStringNullable(newValue.getReligion().orElse(null)));
        selectedBodyNokName.setText(toStringNullable(newValue.getNextOfKin().orElse(null)));
        selectedBodyNokRelationship.setText(toStringNullable(newValue.getRelationship().orElse(null)));
        selectedBodyNokPhone.setText(toStringNullable(newValue.getKinPhoneNumber().orElse(null)));
        selectedBodyCauseOfDeath.setText(toStringNullable(newValue.getCauseOfDeath().orElse(null)));

        organs.getChildren().clear();
        newValue.getOrgansForDonation().stream()
            .sorted(Comparator.comparing(String::toString))
            .forEach(organ -> organs.getChildren().add(new Label(organ)));
    }

    /**
     * Returns {@code NO_INPUT_DISPLAY} ("-") when the field is not available.
     */
    private String toStringNullable(Object o) {
        if (o == null) {
            return NO_INPUT_DISPLAY;
        } else {
            return o.toString();
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BodyCardSelected)) {
            return false;
        }

        // state check
        BodyCardSelected card = (BodyCardSelected) other;
        return selectedBodyId.getText().equals(card.selectedBodyId.getText())
                && body.equals(card.body);
    }
}
//@@author
