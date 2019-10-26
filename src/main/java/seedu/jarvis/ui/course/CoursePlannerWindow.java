package seedu.jarvis.ui.course;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.jarvis.logic.Logic;
import seedu.jarvis.ui.UiPart;

public class CoursePlannerWindow extends UiPart<Region> {
    private static final String FXML = "CoursePlannerWindow.fxml";

    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private CourseListPanel courseListPanel;
    private CourseTextDisplay courseTextDisplay;

    @FXML
    private StackPane courseContainer;

    @FXML
    private AnchorPane courseListPanelContainer;

    @FXML
    private AnchorPane courseTextDisplayContainer;

    public CoursePlannerWindow(Logic logic) {
        super(FXML);
        this.logic = logic;
    }

    void fillInnerParts() {
        courseListPanel = new CourseListPanel(logic.getUnfilteredCourseList());
        courseListPanelContainer.getChildren().add(courseListPanel.getRoot());

        courseTextDisplay = new CourseTextDisplay();
        courseTextDisplay.setFeedbackToUser(logic.getCourseTextDisplay());
        courseTextDisplayContainer.getChildren().add(courseTextDisplay.getRoot());

        courseContainer.getChildren().add(courseListPanelContainer);
        courseContainer.getChildren().add(courseTextDisplayContainer);
    }
}
