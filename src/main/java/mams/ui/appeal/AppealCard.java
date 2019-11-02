package mams.ui.appeal;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import mams.model.appeal.Appeal;
import mams.ui.UiPart;

/**
 * An UI component that displays information of an {@code Appeal}.
 */
public class AppealCard extends UiPart<Region> {
    public static final String STATUS_APPROVED = "Approved";
    public static final String STATUS_REJECTED = "Rejected";

    public static final String APPROVED_ICON = "\uD83D\uDC4D";
    public static final String REJECTED_ICON = "\uD83D\uDC4E";

    public static final String STATUS_RESOLVED = "\u2713" + " Resolved";
    public static final String STATUS_UNRESOLVED = "\u2718" + " Unresolved";

    public static final String STATUS_BASE_STYLE_CLASS = "prefix-label";
    public static final String REJECTED_STYLE_CLASS = "prefix_red";
    public static final String APPROVED_STYLE_CLASS = "prefix_sky_blue";

    public static final String UNRESOLVED_STYLE_CLASS = "prefix_pink";
    public static final String RESOLVED_STYLE_CLASS = "prefix_green";

    private static final String FXML = "AppealListCard.fxml";

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
    private Label approvalIcon;
    @FXML
    private Label approvalStatus;
    @FXML
    private Label resolvedStatus;

    public AppealCard(Appeal appeal, int displayedIndex) {
        super(FXML);
        this.appeal = appeal;
        id.setText(displayedIndex + ". ");
        appealId.setText(appeal.getAppealId());
        studentId.setText(appeal.getStudentId());
        appealType.setText(appeal.getAppealType());
        academicYear.setText(appeal.getAcademicYear());
        // TODO change once aaron implements getter method
        setApprovalStatusDisplay(approvalIcon, approvalStatus, appeal.isResolved(),
                appeal.getResult().equals("APPROVED"));
        setResolvedStatusDisplay(resolvedStatus, appeal.isResolved());
    }

    public static void setResolvedStatusDisplay(Label resolvedStatus, boolean isResolved) {
        if (isResolved) {
            resolvedStatus.setText(STATUS_RESOLVED);
            resolvedStatus.getStyleClass().add(RESOLVED_STYLE_CLASS);
        } else {
            resolvedStatus.setText(STATUS_UNRESOLVED);
            resolvedStatus.getStyleClass().add(UNRESOLVED_STYLE_CLASS);
        }
    }

    public static void setApprovalStatusDisplay(Label approvalIcon, Label approvalStatus,
                                                boolean isResolved, boolean isApproved) {
        if (isResolved) {
            if (isApproved) {
                approvalIcon.setText(APPROVED_ICON);
                approvalStatus.setText(STATUS_APPROVED);
                approvalStatus.getStyleClass().addAll(STATUS_BASE_STYLE_CLASS, APPROVED_STYLE_CLASS);
            } else {
                approvalIcon.setText(REJECTED_ICON);
                approvalStatus.setText(STATUS_REJECTED);
                approvalStatus.getStyleClass().addAll(STATUS_BASE_STYLE_CLASS, REJECTED_STYLE_CLASS);
            }
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
