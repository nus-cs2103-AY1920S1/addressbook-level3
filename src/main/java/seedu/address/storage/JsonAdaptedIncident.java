package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.incident.CallerNumber;
import seedu.address.model.incident.Description;
import seedu.address.model.incident.Incident;
import seedu.address.model.incident.IncidentDateTime;
import seedu.address.model.incident.IncidentId;
import seedu.address.model.person.Person;
import seedu.address.model.vehicle.District;
import seedu.address.model.vehicle.Vehicle;

/**
 * Jackson-friendly version of {@link Incident}.
 */
class JsonAdaptedIncident {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Incident's %s field is missing!";

    private final JsonAdaptedPerson operator;
    private final int districtNum;
    private final String dateTime;
    private final String incidentId;
    private final String callerNumber;
    private final String description;
    private final String status;
    private final JsonAdaptedVehicle vehicle;

    /**
     * Constructs a {@code JsonAdaptedIncident} with the given incident details.
     */
    @JsonCreator
    public JsonAdaptedIncident(@JsonProperty("operator") JsonAdaptedPerson operator,
                              @JsonProperty("districtNum") int districtNum,
                              @JsonProperty("dateTime") String dateTime,
                              @JsonProperty("incidentId") String incidentId,
                              @JsonProperty("callerNumber") String callerNumber,
                              @JsonProperty("description") String description,
                              @JsonProperty("status") String status,
                              @JsonProperty("vehicle") JsonAdaptedVehicle vehicle) {
        this.operator = operator;
        this.districtNum = districtNum;
        this.dateTime = dateTime;
        this.incidentId = incidentId;
        this.callerNumber = callerNumber;
        this.description = description;
        this.status = status;
        this.vehicle = vehicle;
    }

    /**
     * Converts a given {@code Incident} into this class for Jackson use.
     */
    public JsonAdaptedIncident(Incident source) {
        operator = new JsonAdaptedPerson(source.getOperator());
        districtNum = source.getDistrict().getDistrictNum();
        dateTime = source.getDateTime().toString();
        incidentId = source.getIncidentId().getId();
        status = source.getStatus().name(); // do not use toString() because it has been overridden

        // uninitialised for new incidents
        CallerNumber sourceC = source.getCallerNumber();
        Description sourceD = source.getDesc();

        if (sourceC == null) {
            callerNumber = "00000000";
        } else {
            callerNumber = sourceC.toString();
        }

        if (sourceD == null) {
            description = "[TO BE FILLED]";
        } else {
            description = sourceD.toString();
        }

        vehicle = new JsonAdaptedVehicle(
                source.getVehicle().getVehicleType().toString(),
                source.getVehicle().getVehicleNumber().toString(),
                source.getVehicle().getDistrict().getDistrictNum(),
                source.getVehicle().getAvailability().toString()
        );
    }

    /**
     * Converts this Jackson-friendly adapted incident object into the model's {@code Incident} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted incident.
     */
    public Incident toModelType() throws IllegalValueException {
        final Person modelOperator = operator.toModelType();

        if (districtNum == 0) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    District.class.getSimpleName()));
        }
        if (!District.isValidDistrict(districtNum)) {
            throw new IllegalValueException(District.MESSAGE_CONSTRAINTS);
        }
        final District modelDistrict = new District(districtNum);

        if (dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    IncidentDateTime.class.getSimpleName()));
        }
        if (!IncidentDateTime.isValidIncidentDateTimeFormat(dateTime)) {
            throw new IllegalValueException(IncidentDateTime.MESSAGE_CONSTRAINTS);
        }
        final IncidentDateTime modelDateTime = new IncidentDateTime(dateTime);

        // no need to run checks for incidentId as it is generated from IncidentDateTime
        final IncidentId modelIncidentId = new IncidentId(modelDateTime.getMonth(),
                modelDateTime.getYear());

        if (callerNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    CallerNumber.class.getSimpleName()));
        }
        if (!CallerNumber.isValidPhone(callerNumber)) {
            throw new IllegalValueException(CallerNumber.MESSAGE_CONSTRAINTS);
        }
        final CallerNumber modelCallerNumber = new CallerNumber(callerNumber);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Incident.Status.class.getSimpleName()));
        }

        try {
            Incident.Status.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(Incident.Status.MESSAGE_CONSTRAINTS);
        }
        final Incident.Status modelStatus = Incident.Status.valueOf(status);

        // no need check for vehicle validity because vehicle must alr be created to be added.
        assert(this.vehicle != null);
        final Vehicle modelVehicle = this.vehicle.toModelType();

        return new Incident(modelOperator, modelDistrict, modelDateTime, modelIncidentId, modelCallerNumber,
                modelDescription, modelStatus, modelVehicle);
    }
}
