package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.student.Student;

/**
 * Panel containing the list of students.
 */
public class CombinedListPanel extends UiPart<Region> {
    private static final String FXML = "CombinedListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CombinedListPanel.class);

    @FXML
    private ListView<Student> studentListView;
    @FXML
    private ListView<Assignment> assignmentListView;

    public CombinedListPanel(ObservableList<Student> studentList, ObservableList<Assignment> assignmentList) {
        super(FXML);
        studentListView.setItems(studentList);
        studentListView.setCellFactory(listView -> new StudentListViewCell());
        assignmentListView.setItems(assignmentList);
        assignmentListView.setCellFactory(listView -> new AssignmentListViewCell());
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Student} using a {@code StudentCard}.
     */
    class StudentListViewCell extends ListCell<Student> {
        @Override
        protected void updateItem(Student student, boolean empty) {
            super.updateItem(student, empty);

            if (empty || student == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new StudentCard(student, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of an {@code Assignment} using a {@code AssignmentCard}.
     */
    class AssignmentListViewCell extends ListCell<Assignment> {
        @Override
        protected void updateItem(Assignment assignment, boolean empty) {
            super.updateItem(assignment, empty);

            if (empty || assignment == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AssignmentCard(assignment, getIndex() + 1).getRoot());
            }
        }
    }

}
