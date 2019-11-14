package cs.f10.t1.nursetraverse.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import cs.f10.t1.nursetraverse.commons.exceptions.IllegalValueException;
import cs.f10.t1.nursetraverse.model.AppointmentBook;
import cs.f10.t1.nursetraverse.model.ReadOnlyAppointmentBook;
import cs.f10.t1.nursetraverse.model.appointment.Appointment;

/**
 * An Immutable AppointmentBook that is serializable to JSON format.
 */
@JsonRootName(value = "appointmentbook")
public class JsonSerializableAppointmentBook {

    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "Appointments list contains duplicate appointment(s).";

    private final List<JsonAdaptedAppointment> appointments = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablePatientBook} with the given patients.
     */
    @JsonCreator
    public JsonSerializableAppointmentBook(
            @JsonProperty("appointments") List<JsonAdaptedAppointment> appointments) {
        this.appointments.addAll(appointments);
    }

    /**
     * Converts a given {@code ReadOnlyAppointmentBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAppointmentBook}.
     */
    public JsonSerializableAppointmentBook(ReadOnlyAppointmentBook source) {
        appointments.addAll(source.getAppointmentList()
                    .stream()
                    .map(JsonAdaptedAppointment::new)
                    .collect(Collectors.toList()));
    }

    /**
     * Converts this appointment book into the model's {@code AppointmentBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AppointmentBook toModelType() throws IllegalValueException {
        AppointmentBook appointmentBook = new AppointmentBook();
        for (int i = 0; i < appointments.size(); i++) {
            JsonAdaptedAppointment jsonAdaptedAppointment = appointments.get(i);
            Appointment appointment = jsonAdaptedAppointment.toModelType();
            if (appointmentBook.hasAppointment(appointment)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_APPOINTMENT);
            }
            appointmentBook.addAppointment(appointment);
        }
        return appointmentBook;
    }
}
