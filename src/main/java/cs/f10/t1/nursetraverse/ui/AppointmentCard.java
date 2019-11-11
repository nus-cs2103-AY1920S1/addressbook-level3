package cs.f10.t1.nursetraverse.ui;

import cs.f10.t1.nursetraverse.model.appointment.Appointment;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * A UI component that displays information of an {@code Appointment}.
 */
public class AppointmentCard extends UiPart<Region> {

    private static final String FXML = "AppointmentListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/patientBook-level4/issues/336">The issue on PatientBook level 4</a>
     */

    public final Appointment appointment;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private TextFlow startDateTime;
    @FXML
    private TextFlow endDateTime;
    @FXML
    private Label patientName;
    @FXML
    private Label patientPhone;
    @FXML
    private Label patientEmail;
    @FXML
    private Label patientAddress;
    @FXML
    private FlowPane description;
    @FXML
    private FlowPane recurDateTime;
    @FXML
    private FlowPane frequency;

    /**
     * Display UI for an appointment, taking into account its displayed index.
     */
    public AppointmentCard(Appointment appointment, int displayedIndex) {
        super(FXML);
        this.appointment = appointment;
        id.setText(displayedIndex + ". ");

        patientName.setText(appointment.getPatient().getName().fullName);
        patientPhone.setText(appointment.getPatient().getPhone().value);
        patientAddress.setText(appointment.getPatient().getAddress().value);
        patientEmail.setText(appointment.getPatient().getEmail().value);

        if (!appointment.getDescription().equals("")) {
            description.getChildren().add(new Label(appointment.getDescription()));
        }

        if (appointment.getFrequency().isRecurringFrequency()) {
            recurDateTime.getChildren().add(new Label("recurring"));
            frequency.getChildren().add(new Label("Every " + appointment.getFrequency().toString()));
        }

        Text startDateTimePre = new Text("Starts: ");
        startDateTimePre.setStyle("-fx-font-weight: bold");
        Text startDateTimePost = new Text(appointment.getStartDateTime().toUiString());

        Text endDateTimePre = new Text("Ends: ");
        endDateTimePre.setStyle("-fx-font-weight: bold");
        Text endDateTimePost = new Text(appointment.getEndDateTime().toUiString());

        startDateTime.getChildren().addAll(startDateTimePre, startDateTimePost);
        endDateTime.getChildren().addAll(endDateTimePre, endDateTimePost);

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppointmentCard)) {
            return false;
        }

        // state check
        AppointmentCard card = (AppointmentCard) other;
        return id.getText().equals(card.id.getText())
                && appointment.equals(card.appointment);
    }
}
