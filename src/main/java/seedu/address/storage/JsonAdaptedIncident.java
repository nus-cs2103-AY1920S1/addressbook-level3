package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.incident.Incident;
import seedu.address.model.incident.IncidentDateTime;
import seedu.address.model.incident.IncidentId;
import seedu.address.model.vehicle.District;

/**
 * Jackson-friendly version of {@link Incident}.
 */
class JsonAdaptedIncident {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Incident's %s field is missing!";

    private final String incidentId;
    private final int districtNum;
    private final String dateTime;
    private final String operator;

    /**
     * Constructs a {@code JsonAdaptedIncident} with the given incident details.
     */
    @JsonCreator
    public JsonAdaptedIncident(@JsonProperty("incidentId") String incidentId,
                              @JsonProperty("districtNum") int districtNum,
                              @JsonProperty("dateTime") String dateTime,
                              @JsonProperty("operator") String operator) {
        this.incidentId = incidentId;
        this.districtNum = districtNum;
        this.dateTime = dateTime;
        this.operator = operator;
    }

    /**
     * Converts a given {@code Incident} into this class for Jackson use.
     */
    public JsonAdaptedIncident(Incident source) {
        incidentId = source.getIncidentId().getId();
        districtNum = source.getLocation().districtNum;
        dateTime = source.getDateTime().toString();
        operator = source.getOperator().getName().toString();
    }

    /**
     * Converts this Jackson-friendly adapted incident object into the model's {@code Incident} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted incident.
     */
    public Incident toModelType() throws IllegalValueException {
        if (dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    IncidentDateTime.class.getSimpleName()));
        }
        if (!IncidentDateTime.isValidIncidentDateTime(dateTime)) {
            throw new IllegalValueException(IncidentDateTime.MESSAGE_CONSTRAINTS);
        }
        final IncidentDateTime modelDateTime = new IncidentDateTime(dateTime);

        // no need to run checks for incidentId as it is generated from IncidentDateTime
        final IncidentId modelIncidentId = new IncidentId(modelDateTime.getMonth(),
                modelDateTime.getYear());

        if (districtNum == 0) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    District.class.getSimpleName()));
        }
        if (!District.isValidDistrict(districtNum)) {
            throw new IllegalValueException(District.MESSAGE_CONSTRAINTS);
        }
        final District modelDistrict = new District(districtNum);

        // TODO: Implement Person check for operator when it takes in Person class
        final String modelOperator = operator;

        return new Incident(modelIncidentId, modelDistrict, modelDateTime, modelOperator);
    }

}
