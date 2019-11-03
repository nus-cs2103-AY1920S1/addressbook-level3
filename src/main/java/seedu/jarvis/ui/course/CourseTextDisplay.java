package seedu.jarvis.ui.course;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import seedu.jarvis.logic.Logic;
import seedu.jarvis.ui.UiPart;

/**
 * Represents the simple text box for output for the Course Planner.
 */
public class CourseTextDisplay extends UiPart<Region> {
    private static final String FXML = "CourseTextDisplay.fxml";
    private static final String PATH_TO_FONT = "/fonts/Menlo-Regular.ttf";

    @FXML
    private TextArea courseTextDisplay;

    public CourseTextDisplay(Logic logic) {
        super(FXML);
        courseTextDisplay.textProperty().bind(logic.getCourseTextDisplay());

        // get custom font
        final Font f = Font.loadFont(this.getClass().getResource(PATH_TO_FONT).toExternalForm(), 15);
        courseTextDisplay.setFont(f);
    }
}
