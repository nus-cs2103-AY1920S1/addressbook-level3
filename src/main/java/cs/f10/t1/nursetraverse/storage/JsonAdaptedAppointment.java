package cs.f10.t1.nursetraverse.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.commons.exceptions.IllegalValueException;
import cs.f10.t1.nursetraverse.model.appointment.Appointment;
import cs.f10.t1.nursetraverse.model.datetime.EndDateTime;
import cs.f10.t1.nursetraverse.model.datetime.RecurringDateTime;
import cs.f10.t1.nursetraverse.model.datetime.StartDateTime;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
@JsonPropertyOrder({"startDateTime", "endDateTime", "frequency", "patient", "description"})
public class JsonAdaptedAppointment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";
    public static final String END_DATE_EARLIER_THAN_START_DATE = "Appointment's start date is earlier "
            + "than its end date";

    @JsonProperty("startDateTime")
    private final String startDateTime;
    @JsonProperty("endDateTime")
    private final String endDateTime;
    @JsonProperty("frequency")
    private final String frequency;
    @JsonProperty("patient")
    private final Index patientIndex;
    @JsonProperty("description")
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given {@code appointment}.
     */
    @JsonCreator
    public JsonAdaptedAppointment(@JsonProperty("startDateTime") String startDateTime,
                                  @JsonProperty("endDateTime") String endDateTime,
                                  @JsonProperty("frequency") String frequency,
                                  @JsonProperty("patient") Index patientIndex,
                                  @JsonProperty("description") String description) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.frequency = frequency;
        this.patientIndex = patientIndex;
        this.description = description;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        startDateTime = source.getStartDateTime().toJacksonJsonString();
        endDateTime = source.getEndDateTime().toJacksonJsonString();
        frequency = source.getFrequency().toJacksonJsonString();
        patientIndex = source.getPatientIndex();
        description = source.getDescription();
    }

    /**
     * Converts this Jackson-friendly adapted appointment object into the model's {@code Appointment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment.
     */
    public Appointment toModelType() throws IllegalValueException {
        if (startDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StartDateTime.class.getSimpleName()));
        }
        if (!StartDateTime.isValidDateTime(startDateTime)) {
            throw new IllegalValueException(StartDateTime.MESSAGE_CONSTRAINTS);
        }
        final StartDateTime modelStartDateTime = new StartDateTime(startDateTime);

        if (endDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EndDateTime.class.getSimpleName()));
        }
        if (!EndDateTime.isValidDateTime(endDateTime)) {
            throw new IllegalValueException(EndDateTime.MESSAGE_CONSTRAINTS);
        }
        if (!EndDateTime.isValidEndDateTime(startDateTime, endDateTime)) {
            throw new IllegalValueException(END_DATE_EARLIER_THAN_START_DATE);
        }
        final EndDateTime modelEndDateTime = new EndDateTime(endDateTime);

        if (frequency == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    RecurringDateTime.class.getSimpleName()));
        }
        if (!RecurringDateTime.isValidFrequency(frequency)) {
            throw new IllegalValueException(RecurringDateTime.MESSAGE_CONSTRAINTS);
        }
        Long[] freqArray = RecurringDateTime.frequencyStringToLong(frequency);
        final RecurringDateTime modelFrequency = new RecurringDateTime(freqArray);

        if (patientIndex == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Index.class.getSimpleName()));
        }
        final Index modelPatientIndex = patientIndex;

        final String modelDescription = description;

        return new Appointment(modelStartDateTime, modelEndDateTime, modelFrequency, modelPatientIndex,
                modelDescription);
    }

}

