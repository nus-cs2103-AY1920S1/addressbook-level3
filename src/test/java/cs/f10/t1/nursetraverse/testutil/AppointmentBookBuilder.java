package cs.f10.t1.nursetraverse.testutil;

import cs.f10.t1.nursetraverse.model.AppointmentBook;
import cs.f10.t1.nursetraverse.model.appointment.Appointment;

/**
 * A utility class to help with building AppointmentBook objects.
 */
public class AppointmentBookBuilder {

    private AppointmentBook appointmentBook;

    public AppointmentBookBuilder() {
        appointmentBook = new AppointmentBook();
    }

    public AppointmentBookBuilder(AppointmentBook appointmentBook) {
        this.appointmentBook = appointmentBook;
    }

    /**
     * Adds a new {@code Appointment} to the {@code AppointmentBook} that we are building.
     */
    public AppointmentBookBuilder withAppointment(Appointment appointment) {
        appointmentBook.addAppointment(appointment);
        return this;
    }

    public AppointmentBook build() {
        return appointmentBook;
    }
}

