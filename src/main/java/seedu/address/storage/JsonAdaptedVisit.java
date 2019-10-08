package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.person.VisitReport;


/**
 * Jackson-friendly version of {@link VisitReport}.
 */
class JsonAdaptedVisit {

    private final String visitDate;
    private final String name;
    private final String medicine;
    private final String diagnosis;
    private final String remarks;

    /**
     * Constructs a {@code JsonAdaptedVisit} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedVisit(@JsonProperty("name") String name,@JsonProperty("date") String date) {
        this.visitDate = date;
        this.name = name;
        this.medicine = "meds";
        this.diagnosis = "chemo";
        this.remarks = "dying soon";
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedVisit(VisitReport source) {
        visitDate = source.date;
        name = source.name.toString();
        medicine = source.getMedication();
        diagnosis = source.getDiagnosis();
        remarks = source.getRemarks();
    }

    /*
    @JsonValue
    public String getVisitationRecord() {
        return visitDate;
    }
     */

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public VisitReport toModelType() throws IllegalValueException {
        if (!VisitReport.isValidVisitDate(visitDate)) {
            throw new IllegalValueException(VisitReport.MESSAGE_CONSTRAINTS);
        }
        VisitReport report = new VisitReport(visitDate);
        report.setName(new Name(name));
        return report;
    }

}
