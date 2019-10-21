package seedu.address.ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.entity.body.Body;

/**
 * An UI component that displays information of a {@code Body}.
 */
public class BodyCardSelected extends UiPart<Region> {

    private static final String FXML = "SelectedBodyCard.fxml";

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
    private ImageView sexIcon;
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
                return;
            }
            selectedBodyName.setText(newValue.getName().fullName);
            selectedBodyId.setText(newValue.getIdNum().toString());
            selectedBodySex.setText(newValue.getSex().toString());
            selectedBodyNric.setText(newValue.getNric().toString());
            selectedBodyReligion.setText(newValue.getReligion().toString());
            selectedBodyDateOfAdmission.setText(newValue.getDateOfAdmission().toString());
            selectedBodyDateOfDeath.setText(newValue.getDateOfDeath().toString());
            selectedBodyDateOfBirth.setText(newValue.getDateOfBirth().toString());
            selectedBodyNokName.setText(newValue.getNextOfKin().toString());
            selectedBodyNokRelationship.setText(newValue.getRelationship().toString());
            selectedBodyNokPhone.setText(newValue.getKinPhoneNumber().toString());
            selectedBodyCauseOfDeath.setText(newValue.getCauseOfDeath().toString());

            newValue.getOrgansForDonation().orElse(new ArrayList<>()).stream()
                .sorted(Comparator.comparing(organ -> organ))
                .forEach(organ -> organs.getChildren().add(new Label(organ)));
        });
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
