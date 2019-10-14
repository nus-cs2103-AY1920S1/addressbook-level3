package mams.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import mams.model.appeal.Appeal;

/**
 * An UI component that displays information of an {@code Appeal}.
 */
public class AppealCard extends UiPart<Region> {

    private static final String FXML = "AppealListCard.fxml";
    private static final String APPEAL_TYPE_LABEL_PREFIX = "Appeal Type: ";
    private static final String MATRIC_LABEL_PREFIX = "Matric No.: ";
    private static final String ACAD_YEAR_LABEL_PREFIX = "Academic Year: ";
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

    // TODO fix FXML fields to be specific to appeal

    @FXML
    private FlowPane tags;

    public AppealCard(Appeal appeal, int displayedIndex) {
        super(FXML);
        this.appeal = appeal;
        id.setText(displayedIndex + ". ");
        appealId.setText(appeal.getAppealId());
        studentId.setText(MATRIC_LABEL_PREFIX + appeal.getStudentId());
        appealType.setText(APPEAL_TYPE_LABEL_PREFIX + appeal.getAppealType());
        academicYear.setText(ACAD_YEAR_LABEL_PREFIX + appeal.getAcademicYear());
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
