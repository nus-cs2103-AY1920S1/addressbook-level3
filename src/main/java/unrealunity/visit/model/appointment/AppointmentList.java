package unrealunity.visit.model.appointment;

import static java.util.Objects.requireNonNull;
import static unrealunity.visit.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import unrealunity.visit.model.appointment.exceptions.AppointmentNotFoundException;
import unrealunity.visit.model.appointment.exceptions.DuplicateAppointmentException;

/**
 * AppointmentList facilitates the storing of Reminders and Follow-ups for UI
 * and performs operations dealing with Appointments.
 *
 * Runs in parallel with AppointmentTable, which handles JSON.
 * This is BY DESIGN and an intentional break of OOP.
 */
public class AppointmentList implements Iterable<Appointment> {

    /**
     * List that contains Appointment objects to be displayed.
     */
    private ObservableList<Appointment> internalList;

    /**
     * Unmodifiable Observable List that watches the internalList.
     */
    private ObservableList<Appointment> internalUnmodifiableList;

    /**
     * Initializes new AppointmentList object.
     */
    public AppointmentList(ObservableList<Appointment> apptList) {
        requireNonNull(apptList);
        internalList = apptList;
        internalUnmodifiableList = FXCollections.unmodifiableObservableList(internalList);
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

    // getDefaultAppointments equivalent unnecessary.

    // getAppointmentList equivalent unnecessary.

    /**
     * Adds a new Appointment.
     *
     * @param type The type of appointment. 0 = Reminder, 1 = Follow-Up.
     * @param description The description of the Appointment.
     * @param days How many days the Appointment has remaining.
     */
    public void addAppointment(int type, String description, int days) {
        requireNonNull(description);
        requireNonNull(days);
        if (type == Appointment.Type.FOLLOWUP) {
            Appointment toAdd = new Appointment("[F] " + description, days);
            internalAddWithCheck(toAdd, description);
        } else {
            Appointment toAdd = new Appointment("[R] " + description, days);
            internalAddWithCheck(toAdd, description);
        }
    }

    /**
     * Adds the appointment to the Internal List after checking if there are duplicates.
     *
     * @param toAdd The appointment to be added.
     * @param description The description of the Appointment to check against.
     */
    public void internalAddWithCheck(Appointment toAdd, String description) {
        if (antiDuplicate(toAdd)) {
            if (!antiDuplicate(description)) {
                for (int i = 0; i < internalList.size(); i++) {
                    if (internalList.get(i).getDescriptionRaw().equals(description)) {
                        setAppointment(internalList.get(i), toAdd);
                        break;
                    }
                }
            } else {
                internalList.add(toAdd);
            }
        } else {
            return;
        }
    }

    /**
     * Deletes an appointment from VISIT.
     *
     * @param description The description of the appointment to delete.
     * @param days Optional number of days to specifically target the exact appointment to delete.
     */
    public void deleteAppointment(String description, int days) {
        requireNonNull(description);
        requireNonNull(days);
        if (days == -1) {
            internalList.removeIf(appointment -> appointment.getDescriptionRaw().equals(description));
        } else {
            internalList.removeIf(appointment -> appointment.getDescriptionRaw().equals(description)
                    && appointment.getDays() == days);
        }
    }

    /**
     * Checks if the Appointment already exists.
     *
     * @param toCheck The Appointment to check against.
     *
     * @return True if there is no duplicate, false if there is a duplicate.
     */
    public boolean antiDuplicate(Appointment toCheck) {
        requireNonNull(toCheck);
        return !internalList.stream().anyMatch(toCheck::isSameAppointment);
    }

    /**
     * Checks if the Appointment already exists.
     *
     * @param description The description to try and find in the list.
     *
     * @return True if there is no duplicate, false if there is a duplicate.
     */
    public boolean antiDuplicate(String description) {
        requireNonNull(description);
        return !internalList.stream().anyMatch(appointment -> appointment.isSameAppointment(description));
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

        if (!target.isSameAppointment(editedAppointment) && !antiDuplicate(editedAppointment)) {
            throw new DuplicateAppointmentException();
        }

        internalList.set(index, editedAppointment);
    }

    /**
     * Sorts the list of appointments by days remaining, then name.
     */
    public void sortAppointments() {
        Comparator<Appointment> compareByType = Comparator.comparing(Appointment::getType);
        Comparator<Appointment> compareByValue = Comparator.comparing(Appointment::getDays);
        Comparator<Appointment> compareByKey = Comparator.comparing(Appointment::getDescription);
        Comparator<Appointment> compareByTypeThenValueThenKey = compareByType.thenComparing(compareByValue)
                .thenComparing(compareByKey);
        Collections.sort(internalList, compareByTypeThenValueThenKey);
    }

    /**
     * Reset Appointment Data completely.
     */
    public void resetAppointments() {
        internalList.clear();
    }

    // cascadeDay equivalent unnecessary.

    // outputAppointments equivalent unnecessary.

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

}
