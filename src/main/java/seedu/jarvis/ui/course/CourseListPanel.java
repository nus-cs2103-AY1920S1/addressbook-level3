package seedu.jarvis.ui.course;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.jarvis.commons.core.LogsCenter;
import seedu.jarvis.model.course.Course;
import seedu.jarvis.ui.UiPart;

/**
 * Represents the wrapper panel for the course list.
 */
public class CourseListPanel extends UiPart<Region> {
    private static final String FXML = "CourseListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CourseListPanel.class);

    @FXML
    private ListView<Course> courseListView;

    public CourseListPanel(ObservableList<Course> courseList) {
        super(FXML);
        courseListView.setItems(courseList);
        courseListView.setCellFactory(listView -> new CourseListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Course}
     * using a {@code CourseListCard}.
     */
    class CourseListViewCell extends ListCell<Course> {
        @Override
        protected void updateItem(Course course, boolean empty) {
            super.updateItem(course, empty);

            if (empty || course == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CourseListCard(course, getIndex() + 1).getRoot());
                courseListView.scrollTo(getIndex() + 1);
            }
        }
    }
}
