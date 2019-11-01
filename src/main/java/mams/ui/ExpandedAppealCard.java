package mams.ui;

import static mams.ui.AppealCard.setApprovalStatusDisplay;
import static mams.ui.AppealCard.setResolvedStatusDisplay;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import mams.model.appeal.Appeal;

/**
 * A UI component that displays the full expanded information of an {@code Appeal}.
 * Does not inherit from {@code AppealCard} as it uses a different FXML file -
 * this is to ensure minimal complications in future development, where fx:id's
 * and layouts of various components in the FXML file of {@code ExpandedAppealCard}
 * may diverge from the FXML file used in the {@code AppealCard}. Furthermore,
 * the {@code id} is also no longer used in the expanded view.
 */
public class ExpandedAppealCard extends UiPart<Region> {

    private static final String FXML = "ExpandedAppealListCard.fxml";

    public final Appeal appeal;

    @FXML
    private HBox cardPane;
    @FXML
    private Label appealId;
    @FXML
    private Label appealType;
    @FXML
    private Label studentId;
    @FXML
    private Label academicYear;
    @FXML
    private Label requestedWorkload;
    @FXML
    private Label moduleToDrop;
    @FXML
    private Label requestedModule;
    @FXML
    private Label appealDescription;
    @FXML
    private Label approvalIcon;
    @FXML
    private Label approvalStatus;
    @FXML
    private Label resolvedStatus;
    @FXML
    private Label adminRemarks;

    public ExpandedAppealCard(Appeal appeal) {
        super(FXML);
        this.appeal = appeal;
        appealId.setText(appeal.getAppealId());
        studentId.setText(appeal.getStudentId());
        appealType.setText(appeal.getAppealType());
        academicYear.setText(appeal.getAcademicYear());
        requestedWorkload.setText(Integer.toString(appeal.getStudentWorkload()));
        moduleToDrop.setText(appeal.getModuleToDrop());
        requestedModule.setText(appeal.getModuleToAdd());
        appealDescription.setText(appeal.getAppealDescription());
        adminRemarks.setText(appeal.getRemark());
        setResolvedStatusDisplay(resolvedStatus, appeal.isResolved());
        setApprovalStatusDisplay(approvalIcon, approvalStatus, appeal.isResolved(),
                appeal.getResult().equals("APPROVED"));
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
        return appeal.equals(card.appeal);
    }
}
