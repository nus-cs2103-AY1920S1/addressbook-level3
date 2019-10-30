package seedu.jarvis.ui.course;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import seedu.jarvis.logic.Logic;
import seedu.jarvis.model.Model;
import seedu.jarvis.ui.MainWindow;
import seedu.jarvis.ui.template.View;

/**
 * Contains the Course Planner in Jarvis.
 */
public class CoursePlannerWindow extends View<AnchorPane> {
    private static final String FXML = "CoursePlannerWindow.fxml";

    private CourseListPanel courseListPanel;
    private CourseTextDisplay courseTextDisplay;

    @FXML
    private SplitPane courseSplitPane;

    public CoursePlannerWindow(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
    }

    @Override
    public void fillPage() {
        courseListPanel = new CourseListPanel(logic.getUnfilteredCourseList());

        courseTextDisplay = new CourseTextDisplay(logic);

        courseSplitPane.getItems().addAll(
            courseListPanel.getRoot(),
            courseTextDisplay.getRoot()
        );
    }
}
