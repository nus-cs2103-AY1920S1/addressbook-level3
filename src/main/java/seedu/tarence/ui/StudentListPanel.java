package seedu.tarence.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.tarence.commons.core.LogsCenter;
import seedu.tarence.model.student.Student;

/**
 * Panel containing the list of persons.
 */
public class StudentListPanel extends UiPart<Region> {
    private static final String FXML = "StudentListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    private int scrollPosition = 0;

    @FXML
    private ListView<Student> studentListView;

    public StudentListPanel(ObservableList<Student> studentList) {
        super(FXML);
        studentListView.setItems(studentList);
        studentListView.setCellFactory(listView -> new StudentListViewCell());
    }

    /**
     * Scrolls through the student list panel in the given direction.
     */

    void scrollPanel(String direction) {
        if (direction.equals("down") && scrollPosition < studentListView.getItems().size() - 1) {
            scrollPosition += 1;
        }
        if (direction.equals("up") && scrollPosition > 0) {
            scrollPosition -= 1;
        }
        studentListView.scrollTo(scrollPosition);
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

}
