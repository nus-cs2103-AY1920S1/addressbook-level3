package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.classroom.Classroom;

/**
 * Panel containing the list of classrooms.
 */
public class ClassroomListPanel extends UiPart<Region> {
    private static final String FXML = "ClassroomListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ClassroomListPanel.class);

    @FXML
    private ListView<Classroom> classroomListView;

    public ClassroomListPanel(ObservableList<Classroom> classroomList) {
        super(FXML);
        classroomListView.setItems(classroomList);
        classroomListView.setCellFactory(listView -> new ClassroomListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Classroom} using a {@code ClassroomCard}.
     */
    class ClassroomListViewCell extends ListCell<Classroom> {
        @Override
        protected void updateItem(Classroom classroom, boolean empty) {
            super.updateItem(classroom, empty);

            if (empty || classroom == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ClassroomCard(classroom).getRoot());
            }
        }
    }

}
