package seedu.tarence.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.tarence.model.student.Student;

/**
 * An UI component that displays information of a {@code Student}.
 */
public class StudentCard extends UiPart<Region> {

    private static final String FXML = "StudentListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     */

    public final Student student;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label email;
    @FXML
    private Label matric;
    @FXML
    private Label nusnetId;
    @FXML
    private FlowPane tags;

    public StudentCard(Student student, int displayedIndex) {
        super(FXML);
        this.student = student;
        id.setText(displayedIndex + ". ");
        name.setText(student.getName().fullName);
        email.setText("Email: " + student.getEmail().value);

        if (student.getMatricNum().isPresent()) {
            matric.setText("Matric number: " + student.getMatricNum().get().toString());
        } else {
            matric.setText(null);
        }

        if (student.getNusnetId().isPresent()) {
            nusnetId.setText("NUS ID: " + student.getNusnetId().get().toString());
        } else {
            nusnetId.setText(null);
        }

        tags.getChildren().add(new Label(student.getModCode().toString()));
        tags.getChildren().add(new Label(student.getTutName().toString()));
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
        StudentCard card = (StudentCard) other;
        return id.getText().equals(card.id.getText())
                && student.equals(card.student);
    }
}
