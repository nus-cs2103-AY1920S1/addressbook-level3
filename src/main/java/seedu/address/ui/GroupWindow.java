package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.group.ListOfGroups;
import seedu.address.model.student.Student;

/**
 * Represents the window to display a group of students.
 */
public class GroupWindow extends UiPart<Stage> {
    private static final String FXML = "GroupWindow.fxml";
    private static final Logger logger = LogsCenter.getLogger(GroupWindow.class);

    @FXML
    private Label groupId;
    @FXML
    private ListView<Student> studentListView;

    public GroupWindow(Stage root) {
        super(FXML, root);
        //root.setFullScreen(true);
        groupId.setText(ListOfGroups.getCurrentlyQueriedGroup());
        // Set keyboard listener
        root.getScene().addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                root.close();
            }
        });

    }


    /**
     * Creates a new HelpWindow.
     */
    public GroupWindow() {
        this(new Stage());
    }

    public void setStudentsInGroup(ObservableList<Student> studentList) {
        studentListView.setItems(studentList);
        studentListView.setCellFactory(listView -> new StudentListViewCell());
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
     * Shows the help window.
     *
     * @throws IllegalStateException <ul>
     *                               <li>
     *                               if this method is called on a thread other than the JavaFX Application Thread.
     *                               </li>
     *                               <li>
     *                               if this method is called during animation or layout processing.
     *                               </li>
     *                               <li>
     *                               if this method is called on the primary stage.
     *                               </li>
     *                               <li>
     *                               if {@code dialogStage} is already showing.
     *                               </li>
     *                               </ul>
     */
    public void show() {
        logger.fine("Show slideshow.");
        getRoot().show();
        getRoot().centerOnScreen();
        //getRoot().setFullScreen(true);
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the slideshow window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the slideshow window.
     */
    public void focus() {
        getRoot().requestFocus();
    }


}
