package seedu.guilttrip.ui;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.guilttrip.commons.core.LogsCenter;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {
    private static final String FXML = "ResultDisplay.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        logger.info("Sending feedback: " + feedbackToUser);
        logger.info("filling inner parts");
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }

}
