package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.reminder.exceptions.AppointmentNotFoundException;
import seedu.address.model.reminder.exceptions.DuplicateAppointmentException;

/**
 * A list of Appointments that the user has created.
 *
 * Supports a minimal set of list operations.
 *
 * @see Appointment#isSameAppointment(Appointment)
 */
public class AppointmentList implements Iterable<Appointment> {

    private ObservableList<Appointment> internalList;
    private ObservableList<Appointment> internalUnmodifiableList;

    public AppointmentList(ObservableList<Appointment> apptList) {
        internalList = apptList;
        internalUnmodifiableList = FXCollections.unmodifiableObservableList(internalList);
    }
    /**
     * Returns true if the list contains an equivalent Appointment as the given argument.
     */
    public boolean contains(Appointment toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameAppointment);
    }

    /**
     * Adds a Appointment to the list.
     * The Appointment must not already exist in the list.
     */
    public void add(int type, String description, int days) {
        requireNonNull(description);
        requireNonNull(days);
        /*
        if (contains(toAdd)) {
            throw new DuplicateAppointmentException();
        }
         */
        if (type == 1) {
            internalList.add(new Appointment("[F] " + description, days));
        } else {
            internalList.add(new Appointment("[R] " + description, days));
        }
    }

    /**
     * Replaces the Appointment {@code target} in the list with {@code editedAppointment}.
     * {@code target} must exist in the list.
     * The Appointment identity of {@code editedAppointment} must not be the same
     * as another existing Appointment in the list.
     */
    public void setAppointment(Appointment target, Appointment editedAppointment) {
        requireAllNonNull(target, editedAppointment);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AppointmentNotFoundException();
        }

        if (!target.isSameAppointment(editedAppointment) && contains(editedAppointment)) {
            throw new DuplicateAppointmentException();
        }

        internalList.set(index, editedAppointment);
    }

    /**
     * Removes the equivalent Appointment from the list.
     * The Appointment must exist in the list.
     */
    public void remove(Appointment toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new AppointmentNotFoundException();
        }
    }

    public void setAppointments(AppointmentList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code Appointments}.
     * {@code Appointments} must not contain duplicate Appointments.
     */
    public void setAppointments(List<Appointment> appointments) {
        requireAllNonNull(appointments);
        if (!appointmentsAreUnique(appointments)) {
            throw new DuplicateAppointmentException();
        }

        internalList.setAll(appointments);
    }



    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Appointment> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Appointment> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentList // instanceof handles nulls
                        && internalList.equals(((AppointmentList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code Appointments} contains only unique Appointments.
     */
    private boolean appointmentsAreUnique(List<Appointment> appointments) {
        for (int i = 0; i < appointments.size() - 1; i++) {
            for (int j = i + 1; j < appointments.size(); j++) {
                if (appointments.get(i).isSameAppointment(appointments.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
