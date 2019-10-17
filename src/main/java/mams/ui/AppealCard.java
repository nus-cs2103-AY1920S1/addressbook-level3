package mams.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import mams.model.appeal.Appeal;

/**
 * An UI component that displays information of an {@code Appeal}.
 */
public class AppealCard extends UiPart<Region> {

    private static final String FXML = "AppealListCard.fxml";

    private static final String STATUS_RESOLVED = "\u2713" + " Resolved";
    private static final String STATUS_UNRESOLVED = "\u2718" + " Unresolved";

    // TODO define own style classes as extensions so that future changes to prefix don't affect this in regression
    private static final String UNRESOLVED_STYLE_CLASS = "prefix_pink";
    private static final String RESOLVED_STYLE_CLASS = "prefix_green";

    public final Appeal appeal;

    @FXML
    private HBox cardPane;
    @FXML
    private Label appealId;
    @FXML
    private Label id;
    @FXML
    private Label appealType;
    @FXML
    private Label studentId;
    @FXML
    private Label academicYear;
    @FXML
    private Label status;

    public AppealCard(Appeal appeal, int displayedIndex) {
        super(FXML);
        this.appeal = appeal;
        id.setText(displayedIndex + ". ");
        appealId.setText(appeal.getAppealId());
        studentId.setText(appeal.getStudentId());
        appealType.setText(appeal.getAppealType());
        academicYear.setText(appeal.getAcademicYear());
        setStatusDisplay(status, appeal.isResolved());
    }

    private static void setStatusDisplay(Label status, boolean isResolved) {
        if (isResolved) {
            status.setText(STATUS_RESOLVED);
            status.getStyleClass().add(RESOLVED_STYLE_CLASS);
        } else {
            status.setText(STATUS_UNRESOLVED);
            status.getStyleClass().add(UNRESOLVED_STYLE_CLASS);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppealCard)) {
            return false;
        }

        // state check
        AppealCard card = (AppealCard) other;
        return id.getText().equals(card.id.getText())
                && appeal.equals(card.appeal);
    }
}
