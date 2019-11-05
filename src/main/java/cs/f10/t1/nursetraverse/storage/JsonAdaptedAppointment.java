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
import cs.f10.t1.nursetraverse.model.patient.Patient;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
@JsonPropertyOrder({"startDateTime", "endDateTime", "frequency", "patientIndex", "patient", "description"})
public class JsonAdaptedAppointment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";
    public static final String END_DATE_EARLIER_THAN_START_DATE = "Appointment's start date is earlier "
            + "than its end date";

    @JsonProperty("startDateTime")
    private final String startDateTime;
    @JsonProperty("endDateTime")
    private final String endDateTime;
    @JsonProperty("frequency")
    private final Long[] frequency;
    @JsonProperty("patientIndex")
    private final Integer patientIndex;
    @JsonProperty("patient")
    private JsonAdaptedPatient patient;
    @JsonProperty("description")
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given {@code appointment}.
     */
    @JsonCreator
    public JsonAdaptedAppointment(@JsonProperty("startDateTime") String startDateTime,
                                  @JsonProperty("endDateTime") String endDateTime,
                                  @JsonProperty("frequency") Long[] frequency,
                                  @JsonProperty("patientIndex") Integer patientIndex,
                                  @JsonProperty("patient") JsonAdaptedPatient patient,
                                  @JsonProperty("description") String description) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.frequency = frequency;
        this.patientIndex = patientIndex;
        this.patient = patient;
        this.description = description;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        startDateTime = source.getStartDateTime().toJacksonJsonString();
        endDateTime = source.getEndDateTime().toJacksonJsonString();
        frequency = source.getFrequency().getFreqArray();
        patientIndex = source.getPatientIndex().getOneBased();
        patient = new JsonAdaptedPatient(source.getPatient());
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
        if (!StartDateTime.isAfterSystemDateTime(startDateTime)) {
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
        final RecurringDateTime modelFrequency = new RecurringDateTime(frequency);

        if (patientIndex == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Index.class.getSimpleName()));
        }
        final Index modelPatientIndex = Index.fromOneBased(patientIndex);

        if (patient == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Patient.class.getSimpleName()));
        }
        final Patient modelPatient = patient.toModelType();

        final String modelDescription = description;

        Appointment modelAppointment = new Appointment(modelStartDateTime, modelEndDateTime, modelFrequency,
                modelPatientIndex, modelDescription);
        modelAppointment.setPatient(modelPatient);

        return modelAppointment;
    }

}

