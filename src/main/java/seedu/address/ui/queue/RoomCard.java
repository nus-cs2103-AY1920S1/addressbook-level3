//@@author SakuraBlossom
package seedu.address.ui.queue;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Person} in consultation with a {@code Person}.
 */
public class RoomCard extends UiPart<Region> {

    private static final String FXML = "queue/RoomListCard.fxml";
    private final Person doctor;
    private final Optional<Person> patient;

    @FXML
    private VBox roomCard;
    @FXML
    private HBox doctorPane;
    @FXML
    private HBox patientPane;
    @FXML
    private Label doctorName;
    @FXML
    private Label patientName;

    public RoomCard(Person doctor, Optional<Person> patient, int displayedIndex, boolean isReadyToServe) {
        super(FXML);
        this.doctor = doctor;
        this.patient = patient;
        if (isReadyToServe) {
            doctorName.setText(" " + displayedIndex + ". " + doctor.getName().toString());
            doctorPane.setStyle("-fx-background-color: #34495e;");
        } else {
            doctorName.setText(" " + displayedIndex + ". " + doctor.getName().toString() + " [BREAK]");
            doctorPane.setStyle("-fx-background-color: #c0392b;");
        }

        patientName.setText(patient.map(p -> " " + p.getName().toString()).orElse(" Not serving any patients"));
    }
}
