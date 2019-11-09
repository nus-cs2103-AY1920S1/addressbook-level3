package unrealunity.visit.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import unrealunity.visit.model.person.VisitReport;

/**
 * An UI component that displays information of a {@code VisitReport} on the {@code ProfileWindow}.
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
        profileVisitDate.setText("Visitation Report on [" + date + "]");

        // Set Diagnosis, Medication and Remark data
        setVisitText(profileVisitDiagnosis, diagnosis);
        setVisitText(profileVisitMedication, medication);
        setVisitText(profileVisitRemarks, remarks);
    }

    /**
     * Sets a label in the {@code ProfileVisitCard} with a specified {@code String} detailing
     * the appropriate description.
     *
     * @param label {@code Label} instance to display the {@code String} content
     * @param reportDetail {@code String} detailing the description for {@code Label} to display
     */
    private void setVisitText(Label label, String reportDetail) {
        // Guard function for null label
        if (label == null) {
            return;
        }

        if (reportDetail == null || reportDetail.isBlank()) {
            label.setText("-");
        } else {
            label.setText(reportDetail);
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
