package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.classroom.Classroom;

/**
 * An UI component that displays information of a {@code Classroom}, which is the current classroom.
 */
public class CurrentClassroomCard extends UiPart<Region> {

    private static final String FXML = "CurrentClassroomCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Classroom level 4</a>
     */

    public final Classroom classroom;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label studentLength;
    @FXML
    private Label assignmentLength;

    public CurrentClassroomCard(Classroom classroom) {
        super(FXML);
        this.classroom = classroom;

        name.setText(classroom.getClassroomName());
        String numberOfStudents = "Number of students: " + classroom.getStudentLength();
        String numberOfAssignments = "Number of assignments: " + classroom.getAssignmentLength();
        studentLength.setText(numberOfStudents);
        assignmentLength.setText(numberOfAssignments);
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentCard)) {
            return false;
        }

        // state check
        CurrentClassroomCard card = (CurrentClassroomCard) other;
        return name.getText().equals(card.name.getText())
                       && classroom.equals(card.classroom);
    }
}
