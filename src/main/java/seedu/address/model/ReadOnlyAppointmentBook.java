package seedu.address.model;

import javafx.collections.ObservableList;
import javafx.util.Pair;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;

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

