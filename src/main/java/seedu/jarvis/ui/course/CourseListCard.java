package seedu.jarvis.ui.course;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.jarvis.model.course.Course;
import seedu.jarvis.ui.UiPart;

/**
 * Represents a course in the course list.
 */
public class CourseListCard extends UiPart<Region> {
    private static final String FXML = "CourseListCard.fxml";

    public final Course course;

    @FXML
    private HBox courseCardPane;
    @FXML
    private Label courseId;
    @FXML
    private Label courseCode;
    @FXML
    private Label courseTitle;
    @FXML
    private Label courseFaculty;

    public CourseListCard(Course course, int displayedIndex) {
        super(FXML);
        this.course = course;
        courseId.setText(displayedIndex + ". ");
        courseCode.setText(course.getCourseCode().toString()
            + String.format(" (%s MCs)", course.getCourseCredit().toString()));
        courseTitle.setText(course.getTitle().toString());
        courseFaculty.setText("Offered by: " + course.getFaculty().toString());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof CourseListCard)) {
            return false;
        }
        CourseListCard card = (CourseListCard) o;
        return courseId.getText().equals(card.courseId.getText())
                && course.equals(card.course);
    }
}
