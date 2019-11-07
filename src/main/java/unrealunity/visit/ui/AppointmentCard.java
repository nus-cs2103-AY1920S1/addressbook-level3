package unrealunity.visit.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import unrealunity.visit.model.appointment.Appointment;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class AppointmentCard extends UiPart<Region> {

    private static final String FXML = "AppointmentListCard.fxml";

    public final Appointment appointment;

    @FXML
    private HBox appointmentCardPane;
    @FXML
    private ImageView icon;
    @FXML
    private Label title;
    @FXML
    private Label date;


    public AppointmentCard(Appointment appointment) {
        super(FXML);
        this.appointment = appointment;

        String description = appointment.getDescription();

        // Add icon based on whether reminder or followup
        if (description.startsWith("[R] ")) {
            icon.setImage(new Image("/images/reminder.png"));
            title.setText(description.substring(4));
        } else {
            icon.setImage(new Image("/images/follow_up.png"));
            title.setText(description.substring(4));
        }

        date.setText("Days left: " + appointment.getDaysString());
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
        return appointment.equals(card.appointment);
    }
}
