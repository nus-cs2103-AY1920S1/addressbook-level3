package cs.f10.t1.nursetraverse.ui;

import java.util.Comparator;

import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.model.visit.Visit;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * An UI component that displays information of a {@code Visit}.
 */
public class OngoingVisitCard extends UiPart<Region> {

    private static final String FXML = "OngoingVisitListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/patientBook-level4/issues/336">The issue on PatientBook level 4</a>
     */

    public final Visit visit;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label startDateTime;
    @FXML
    private Label endDateTime;
    @FXML
    private Label remark;
    @FXML
    private StackPane visitTasksPlaceholder;

    public OngoingVisitCard(Visit visit) {
        super(FXML);
        this.visit = visit;

        Patient patient = this.visit.getPatient();

        name.setText(patient.getName().fullName);
        phone.setText(patient.getPhone().value);
        address.setText(patient.getAddress().value);
        email.setText(patient.getEmail().value);

        patient.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        startDateTime.setText(visit.getStartDateTime().toString());
        if (visit.getEndDateTime().isPresent()) {
            endDateTime.setText(visit.getEndDateTime().get().toString());
        } else {
            endDateTime.setText("Unfinished (Ongoing Visit)");
        }

        String remarks = this.visit.getRemark().remark;
        if (remarks.isEmpty()) {
            remarks = "This visit has no remarks.";
        }
        remark.setText(remarks);

        VisitTaskTableView visitTaskTableView = new VisitTaskTableView(this.visit.getVisitTasks());
        visitTasksPlaceholder.getChildren().add(visitTaskTableView.getRoot());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OngoingVisitCard)) {
            return false;
        }

        // state check
        OngoingVisitCard card = (OngoingVisitCard) other;
        return visit.equals(card.visit);
    }
}
