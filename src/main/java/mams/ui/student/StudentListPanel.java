package mams.ui.student;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import mams.commons.core.LogsCenter;
import mams.model.student.Student;
import mams.ui.UiPart;

/**
 * Panel containing the list of students.
 */
public class StudentListPanel extends UiPart<Region> {
    private static final String FXML = "ItemListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(StudentListPanel.class);

    private ObservableList<Student> studentList;

    @FXML
    private ListView<Student> itemListView;

    public StudentListPanel(ObservableList<Student> studentList) {
        super(FXML);
        requireNonNull(studentList);
        this.studentList = studentList;
        itemListView.setItems(studentList);
        itemListView.setCellFactory(listView -> new StudentListViewCell());
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
            } else if (studentList.size() == 1) {
                logger.fine("Displaying expanded student card");
                setGraphic(new ExpandedStudentCard(student).getRoot());
            } else {
                setGraphic(new StudentCard(student, getIndex() + 1).getRoot());
            }
        }
    }

}
