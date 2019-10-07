package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

public class VisitReport {
    public final String value;
    public static final String MESSAGE_CONSTRAINTS = "Visit date should follow dd/mm/yyyy format";
    public static final String VALIDATION_REGEX = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";

    private String medication;
    private String diagnosis;
    private String remarks;
    public Name name;

    public VisitReport(String date) {
        requireNonNull(date);
        value = date;
        name = new Name("null");
    }


    public String getMedication() {
        return medication;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    @Override
    public String toString() {
        return value;
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
                && value.equals(((VisitReport) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setDetails(String prescription, String disease, String comment) {
        this.medication = prescription;
        this.diagnosis = disease;
        this.remarks = comment;
    }
}

