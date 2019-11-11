package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A display when there is no active study plan.
 */
public class NoActiveStudyPlanDisplay extends UiPart<Region> {
    private static final String FXML = "NoActiveStudyPlanDisplay.fxml";
    private static final String NO_ACTIVE_STUDY_PLAN = "You can create a new study plan with the command:\n"
            + "- newplan\n"
            + "- newplan <title>";

    @FXML
    private TextArea noActiveStudyPlanString;

    public NoActiveStudyPlanDisplay() {
        super(FXML);
        noActiveStudyPlanString.setText(NO_ACTIVE_STUDY_PLAN);
    }

}
