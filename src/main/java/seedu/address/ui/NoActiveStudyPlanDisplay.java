package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
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

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        noActiveStudyPlanString.setText(feedbackToUser);
    }

}
