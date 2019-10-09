package seedu.address.testutil;

import seedu.address.model.person.Name;
import seedu.address.model.person.VisitReport;

/**
 * A utility class to build Visit Reports for tests.
 */
public class VisitReportBuilder {
    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_DATE = "31/12/2012";
    public static final String DEFAULT_PRESCRIPTION = "2 day MC";
    public static final String DEFAULT_DIAGNOSIS = "Flu";
    public static final String DEFAULT_REMARK = "Your're going to die.";

    private String name;
    private String date;
    private String prescription;
    private String diagnosis;
    private String remark;


    public VisitReportBuilder() {
        name = DEFAULT_NAME;
        date = DEFAULT_DATE;
        prescription = DEFAULT_PRESCRIPTION;
        diagnosis = DEFAULT_DIAGNOSIS;
        remark = DEFAULT_REMARK;

    }

    /**
     * Initializes the VisitReportBuilder with the data of {@code ToCopyToCopy}.
     */
    public VisitReportBuilder(VisitReport reportToCopy) {
        name = reportToCopy.getName();
        date = reportToCopy.date;
        prescription = reportToCopy.getMedication();
        diagnosis = reportToCopy.getDiagnosis();
        remark = reportToCopy.getRemarks();
    }

    /**
     * Sets the {@code Name} of the {@code VisitReport} that we are building.
     */
    public VisitReportBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the {@code date} of the {@code VisitReport} that we are building.
     */
    public VisitReportBuilder withDate(String date) {
        this.date = date;
        return this;
    }

    /**
     * Sets the {@code prescription} of the {@code VisitReport} that we are building.
     */
    public VisitReportBuilder withPrescription(String prescription) {
        this.prescription = prescription;
        return this;
    }

    /**
     * Sets the {@code diagnosis} of the {@code VisitReport} that we are building.
     */
    public VisitReportBuilder withDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
        return this;
    }

    /**
     * Sets the {@code remark} of the {@code VisitReport} that we are building.
     */
    public VisitReportBuilder withRemark(String remark) {
        this.remark = remark;
        return this;
    }

    /**
     * Builds new report.
     */
    public VisitReport build() {
        VisitReport report = new VisitReport(date);
        report.setName(new Name(name));
        report.setDetails(prescription, diagnosis, remark);
        return report;
    }
}
