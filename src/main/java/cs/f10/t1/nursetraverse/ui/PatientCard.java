package cs.f10.t1.nursetraverse.ui;

import java.util.Comparator;

import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.model.visit.Visit;
import cs.f10.t1.nursetraverse.model.visittodo.VisitTodo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * An UI component that displays information of a {@code Patient}.
 */
public class PatientCard extends UiPart<Region> {

    private static final String FXML = "PatientListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/patientBook-level4/issues/336">The issue on PatientBook level 4</a>
     */

    public final Patient patient;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label noVisitTodosLabel;
    @FXML
    private Label noVisitsLabel;
    @FXML
    private FlowPane tags;
    @FXML
    private VBox visitTodos;
    @FXML
    private VBox visits;
    @FXML
    private TitledPane visitTodosTitledPane;
    @FXML
    private TitledPane visitsTitledPane;

    /**
     * Display UI for a patient, taking into account their displayed index.
     */
    public PatientCard(Patient patient, int displayedIndex) {
        super(FXML);
        this.patient = patient;
        id.setText(displayedIndex + ". ");
        name.setText(patient.getName().fullName);
        phone.setText(patient.getPhone().value);
        address.setText(patient.getAddress().value);
        email.setText(patient.getEmail().value);
        patient.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        // TODO: Make this more beautiful or extend list/find with a prefix
        // that optionally shows these fields
        int visitTodoIndex = 1;
        if (!patient.getVisitTodos().isEmpty()) {
            noVisitTodosLabel.setVisible(false);
            for (VisitTodo visitTodo : patient.getVisitTodos()) {
                visitTodos.getChildren().add(
                        new Label(visitTodoIndex + ". " + visitTodo.getDescription()));
                visitTodoIndex++;
            }
        }

        //Keep panes closed by default
        visitTodosTitledPane.setExpanded(false);
        visitsTitledPane.setExpanded(false);
        int visitIndex = 1;
        if (!patient.getVisits().isEmpty()) {
            noVisitsLabel.setVisible(false);
            for (Visit visit : patient.getVisits()) {
                visits.getChildren().add(
                        new Label(visitIndex + ". " + visit.toString()));
                visitIndex++;
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PatientCard)) {
            return false;
        }

        // state check
        PatientCard card = (PatientCard) other;
        return id.getText().equals(card.id.getText())
                && patient.equals(card.patient);
    }
}
