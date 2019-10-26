package seedu.jarvis.ui.course;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.jarvis.ui.UiPart;

/**
 * Represents the simple text box for output for the Course Planner.
 */
public class CourseTextDisplay extends UiPart<Region> {

    private static final String FXML = "CourseTextDisplay.fxml";

    @FXML
    private TextArea courseTextDisplay;

    public CourseTextDisplay() {
        super(FXML);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        courseTextDisplay.setText(feedbackToUser);
    }
}
