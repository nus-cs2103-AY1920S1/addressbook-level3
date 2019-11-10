package unrealunity.visit.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Objects;


/**
 * Visit Report object with name, date, medication, diagnosis and remarks field.
 */
public class VisitReport implements Comparable<VisitReport> {

    public static final String MESSAGE_CONSTRAINTS = "Visit date should follow dd/mm/yyyy format";

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
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidVisitDate(String test) {

        //make sure month and day are valid and year is 2xxx or 19xx
        DateTimeFormatter dateFormatter1 =
                DateTimeFormatter.ofPattern("dd/MM/2uuu").withResolverStyle(ResolverStyle.STRICT);

        DateTimeFormatter dateFormatter2 =
                DateTimeFormatter.ofPattern("dd/MM/19uu").withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate.parse(test, dateFormatter1);
        } catch (DateTimeParseException e) {
            try {
                LocalDate.parse(test, dateFormatter2);
            } catch (DateTimeParseException e2) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof VisitReport)) {
            return false;
        }

        VisitReport otherReport = (VisitReport) other;
        return date.equals(otherReport.date)
                && name.equals(otherReport.name)
                && medication.equals(otherReport.medication)
                && diagnosis.equals(otherReport.diagnosis)
                && remarks.equals(otherReport.remarks);
    }

    @Override
    public int compareTo(VisitReport otherReport) {
        String[] reportDate = this.date.split("/");
        String[] otherDate = otherReport.date.split("/");
        int reportYear = Integer.parseInt(reportDate[2]);
        int otherYear = Integer.parseInt(otherDate[2]);
        int reportMth = Integer.parseInt(reportDate[1]);
        int otherMth = Integer.parseInt(otherDate[1]);
        int reportDay = Integer.parseInt(reportDate[0]);
        int otherDay = Integer.parseInt(otherDate[0]);

        if (reportYear != otherYear) {
            return otherYear - reportYear;
        } else if (reportMth != otherMth) {
            return otherMth - reportMth;
        } else {
            return otherDay - reportDay;
        }
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

