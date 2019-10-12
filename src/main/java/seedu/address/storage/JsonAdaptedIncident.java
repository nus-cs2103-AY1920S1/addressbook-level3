package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.incident.Incident;
import seedu.address.model.incident.IncidentId;
//import seedu.address.model.vehicle.Availability;
import seedu.address.model.vehicle.District;
import seedu.address.model.vehicle.Vehicle;
import seedu.address.model.vehicle.VehicleNumber;
import seedu.address.model.vehicle.VehicleType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

        // format LocalDateTime dateTime in specified format
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        dateTime = source.getDateTime().format(formatter);
        operator = source.getOperator().getName().toString();
    }

    /**
     * Converts this Jackson-friendly adapted incident object into the model's {@code Incident} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted incident.
     */
    public Incident toModelType() throws IllegalValueException {
//        if (vehicleNumber == null) {
//            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
//                    VehicleNumber.class.getSimpleName()));
//        }
//        if (!VehicleNumber.isValidVehicleNumber(vehicleNumber)) {
//            throw new IllegalValueException(VehicleNumber.MESSAGE_CONSTRAINTS);
//        }
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime dateTimeInDateTime = LocalDateTime.parse(dateTime, formatter);
        final IncidentId modelIncidentId = new IncidentId(dateTimeInDateTime.getMonthValue(),
                dateTimeInDateTime.getYear());
//
//        if (districtNum == 0) {
//            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
//                    District.class.getSimpleName()));
//        }
//        if (!District.isValidDistrict(districtNum)) {
//            throw new IllegalValueException(District.MESSAGE_CONSTRAINTS);
//        }
        final District modelDistrict = new District(districtNum);

//        if (vehicleType == null) {
//            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
//                    VehicleType.class.getSimpleName()));
//        }
//        if (!VehicleType.isValidVehicleType(vehicleType)) {
//            throw new IllegalValueException(VehicleType.MESSAGE_CONSTRAINTS);
//        }

        final LocalDateTime modelDateTime = dateTimeInDateTime;
//
//        if (availability == null) {
//            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
//                    Availability.class.getSimpleName()));
//        }
//        if (!Availability.isValidAvailability(availability)) {
//            throw new IllegalValueException(Availability.MESSAGE_CONSTRAINTS);
//        }
        final String modelOperator = operator;

        return new Incident(modelIncidentId, modelDistrict, modelDateTime, modelOperator);
    }

}
