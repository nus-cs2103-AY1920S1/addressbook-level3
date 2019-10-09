package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Objects;


/**
 * Visit Report object with name, date, medication, diagnosis and remarks field.
 */
public class VisitReport {

    public static final String MESSAGE_CONSTRAINTS = "Visit date should follow dd/mm/yyyy format";
    public static final String VALIDATION_REGEX = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";

    public final String date;

    private String medication;
    private String diagnosis;
    private String remarks;
    private Name name;

    public VisitReport(String value) {
        requireNonNull(value);
        date = value;
        name = new Name("null");
    }

    public String getName() {
        return this.name.toString();
    }

    public String getMedication() {
        return medication;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getRemarks() {
        return this.remarks;
    }

    @Override
    public String toString() {
        return date;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidVisitDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VisitReport // instanceof handles nulls
                && date.equals(((VisitReport) other).date))
                && medication.equals(((VisitReport) other).medication)
                && diagnosis.equals(((VisitReport) other).diagnosis)
                && remarks.equals(((VisitReport) other).remarks); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, medication, diagnosis, remarks);
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setDetails(String prescription, String disease, String comment) {
        this.medication = prescription;
        this.diagnosis = disease;
        this.remarks = comment;
    }


}

