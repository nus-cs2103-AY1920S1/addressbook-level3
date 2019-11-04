package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.VisitReport;

/**
 * An UI component that displays information of a {@code VisitList}.
 */
public class ProfileVisitCard extends UiPart<Region> {

    private static final String FXML = "ProfileVisitCard.fxml";

    public final VisitReport report;
    public final String date;
    private String diagnosis;
    private String medication;
    private String remarks;

    @FXML
    private HBox profileVisitCardPane;
    @FXML
    private Label profileVisitDate;
    @FXML
    private Label profileVisitDiagnosis;
    @FXML
    private Label profileVisitMedication;
    @FXML
    private Label profileVisitRemarks;

    public ProfileVisitCard(VisitReport report) {
        super(FXML);
        this.report = report;
        this.date = report.date;
        this.diagnosis = report.getDiagnosis();
        this.medication = report.getMedication();
        this.remarks = report.getRemarks();

        // Set date
        profileVisitDate.setText("Visitation Report on  [" + date + "]");

        // Set Diagnosis
        if (diagnosis == null || diagnosis.isEmpty()) {
            profileVisitDiagnosis.setText("-");
            diagnosis = "-";
        } else {
            profileVisitDiagnosis.setText(report.getDiagnosis());
        }

        // Set Medication
        if (report.getMedication() == null || report.getMedication().isEmpty()) {
            profileVisitMedication.setText("-");
            medication = "-";
        } else {
            profileVisitMedication.setText(report.getMedication());
        }

        // Set Remarks
        if (report.getRemarks() == null || report.getRemarks().isEmpty()) {
            profileVisitRemarks.setText("-");
            remarks = "-";
        } else {
            profileVisitRemarks.setText(report.getRemarks());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProfileVisitCard)) {
            return false;
        }

        // state check
        ProfileVisitCard card = (ProfileVisitCard) other;
        return this.date.equals(card.date)
                && report.equals(card.report);
    }
}
