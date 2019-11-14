package cs.f10.t1.nursetraverse.model;

import cs.f10.t1.nursetraverse.model.appointment.Appointment;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an appointment book
 */
public interface ReadOnlyAppointmentBook {

    /**
     * Returns an unmodifiable view of the appointments list.
     * This list will not contain any duplicate appointments.
     */
    ObservableList<Appointment> getAppointmentList();

    /**
     * Returns a deep copy of this {@code readOnlyAppointmentBook}. Changes made to the copy will not affect this object
     * and vice versa.
     */
    ReadOnlyAppointmentBook deepCopy();
}

