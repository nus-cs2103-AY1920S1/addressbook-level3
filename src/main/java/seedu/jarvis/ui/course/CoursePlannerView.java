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
public class CoursePlannerView extends View<AnchorPane> {
    private static final String FXML = "CoursePlannerView.fxml";

    private CourseListPanel courseListPanel;
    private CourseTextDisplay courseTextDisplay;

    @FXML
    private SplitPane courseSplitPane;

    public CoursePlannerView(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
        courseSplitPane.setDividerPositions(0.35f);
    }

    @Override
    public void fillPage() {
        while (!courseSplitPane.getItems().isEmpty()) {
            courseSplitPane.getItems().remove(0);
        }
        courseListPanel = new CourseListPanel(logic.getUnfilteredCourseList());

        courseTextDisplay = new CourseTextDisplay(logic);

        courseSplitPane.getItems().addAll(
            courseListPanel.getRoot(),
            courseTextDisplay.getRoot()
        );
    }
}
