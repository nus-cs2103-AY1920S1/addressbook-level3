package cs.f10.t1.nursetraverse.model;

import static cs.f10.t1.nursetraverse.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.commons.exceptions.CopyError;
import cs.f10.t1.nursetraverse.commons.exceptions.IllegalValueException;
import cs.f10.t1.nursetraverse.model.appointment.Appointment;
import cs.f10.t1.nursetraverse.model.appointment.UniqueAppointmentList;
import cs.f10.t1.nursetraverse.model.datetime.EndDateTime;
import cs.f10.t1.nursetraverse.model.datetime.RecurringDateTime;
import cs.f10.t1.nursetraverse.model.datetime.StartDateTime;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.storage.JsonSerializableAppointmentBook;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the appointment-book level
 * Duplicates are not allowed (by .isSameAppointment comparison)
 */
public class AppointmentBook implements ReadOnlyAppointmentBook {

    private final UniqueAppointmentList appointments;
    private final UniqueAppointmentList finishedAppointments;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        appointments = new UniqueAppointmentList();
        finishedAppointments = new UniqueAppointmentList();
    }

    public AppointmentBook() {}

    /**
     * Creates an AppointmentBook using the Appointments in the {@code toBeCopied}
     */
    public AppointmentBook(ReadOnlyAppointmentBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the appointment list with {@code appointments}.
     * {@code appointments} must not contain duplicate appointments.
     */
    public void setAppointments(List<Appointment> appointments) {
        this.appointments.setAppointments(appointments);
    }

    /**
     * Replaces the contents of the finishedAppointment list with {@code appointments}.
     * {@code appointments} must not contain duplicate appointments.
     */
    public void setFinishedAppointments(List<Appointment> appointments) {
        if (appointments.isEmpty()) {
            List<Appointment> empty = new ArrayList<>();
            this.finishedAppointments.setAppointments(empty);
        } else {
            this.finishedAppointments.setAppointments(appointments);
        }
    }

    /**
     * Resets the existing data of this {@code AppointmentBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAppointmentBook newData) {
        requireNonNull(newData);
        List<Appointment> newAppointmentList = newData.getAppointmentList();

        // Predicates and Functions to map
        Predicate<Appointment> toKeep = appt -> EndDateTime.isAfterSystemDateTime(appt.getEndDateTime().toString())
                                                || appt.getFrequency().isRecurringFrequency();
        Predicate<Appointment> finished = appt -> !EndDateTime.isAfterSystemDateTime(appt.getEndDateTime().toString());
        Function<Appointment, Appointment> toRecur = appt -> {
            Appointment next =  appt;
            while (!EndDateTime.isAfterSystemDateTime(next.getEndDateTime().toString())) {
                if (appt.getFrequency().isRecurringFrequency()) {
                    next = getRecurredAppointment(next);
                }
            }
            return next;
        };

        // Appointment lists created from predicates and functions to map
        List<Appointment> toSetTempAppointmentList = newAppointmentList.stream().filter(toKeep).map(toRecur)
                                                 .collect(Collectors.toList());
        List<Appointment> toSetFinishedAppointmentList = newAppointmentList.stream().filter(finished)
                                                         .collect(Collectors.toList());

        // Set appointments to the appointment lists gotten
        setAppointments(toSetTempAppointmentList);
        setFinishedAppointments(toSetFinishedAppointmentList);

        // Check clashes with recurred appointments and other appointments
        Function<Appointment, Appointment> fixClashRecurring = appt -> {
            Appointment next = appt;
            if (appt.getFrequency().isRecurringFrequency()) {
                removeRecurringAppointment(appt);
                while (hasClashingAppointment(next)) {
                    next = getRecurredAppointment(next);
                }
                addAppointment(next);
            }
            return next;
        };
        List<Appointment> toSetPermAppointmentList = toSetTempAppointmentList.stream().map(fixClashRecurring)
                                                    .collect(Collectors.toList());
        setAppointments(toSetPermAppointmentList);
    }

    //// appointment-level operations

    /**
     * Returns true if an appointment with the same identity as {@code appointment} exists in the appointment book.
     */
    public boolean hasAppointment(Appointment appointment) {
        requireNonNull(appointment);
        return appointments.contains(appointment);
    }

    /**
     * Returns true if an appointment with clashing time as {@code appointment} exists in the appointment book.
     */
    public boolean hasClashingAppointment(Appointment appointment) {
        requireNonNull(appointment);
        return appointments.clashes(appointment);
    }

    /**
     * Adds an appointment to the appointment book.
     * The appointment must not already exist in the appointment book.
     */
    public void addAppointment(Appointment a) {
        appointments.add(a);
    }

    /**
     * Replaces the given appointment {@code target} in the list with {@code editedAppointment}.
     * {@code target} must exist in the appointment book.
     * The appointment identity of {@code editedAppointment} must not be the same as another existing appointment in the
     * appointment book.
     */
    public void setAppointment(Appointment target, Appointment editedAppointment) {
        requireNonNull(editedAppointment);

        appointments.setAppointment(target, editedAppointment);
    }

    /**
     * Replaces all appointments with {@code patientToEdit} in the list with {@code editedPatient}.
     */
    public void editAppointments(Patient patientToEdit, Patient editedPatient) {
        requireAllNonNull(patientToEdit, editedPatient);
        List<Appointment> newAppointments = new ArrayList<>();
        for (Appointment appt : appointments) {
            if (appt.getPatient().equals(patientToEdit)) {
                appt.setPatient(editedPatient);
            }
            newAppointments.add(appt);
        }
        setAppointments(newAppointments);
    }

    /**
     * Removes {@code key} from this {@code AppointmentBook}.
     * {@code key} must exist in the appointment book.
     */
    public void removeAppointment(Appointment key) {
        requireNonNull(key);
        appointments.remove(key);

        if (key.getFrequency().isRecurringFrequency()) {
            addRecurringAppointment(key);
        }
    }

    /**
     * Removes {@code key} from this {@code AppointmentBook}.
     * Unlike usual {@code removeAppointment} method, this does not add the next recurring appointment,
     * but permanently deletes it.
     * {@code key} must exist in the appointment book.
     */
    public void removeRecurringAppointment(Appointment key) {
        requireNonNull(key);
        appointments.remove(key);
    }

    /**
     * Removes all appointments with this {@code patient} from this {@code AppointmentBook}.
     */
    public void removeAppointments(Patient patient, Index patientIndex) {
        requireNonNull(patient);
        List<Appointment> keepAppointments = new ArrayList<>();
        for (Appointment appt : appointments) {
            if (!appt.getPatient().equals(patient)) {
                int currPatientIndex = appt.getPatientIndex().getOneBased();
                int targetPatientIndex = patientIndex.getOneBased();

                if (currPatientIndex > targetPatientIndex) {
                    appt.setPatientIndex(Index.fromOneBased(currPatientIndex - 1));
                }
                keepAppointments.add(appt);
            }
        }
        setAppointments(keepAppointments);
    }

    private Appointment getRecurredAppointment(Appointment key) {
        RecurringDateTime frequency = key.getFrequency();
        StartDateTime nextStartDateTime = new StartDateTime(frequency
                .getNextAppointmentDateTime(key.getStartDateTime()));
        EndDateTime nextEndDateTime = new EndDateTime(frequency
                .getNextAppointmentDateTime(key.getEndDateTime()));
        Index patientIndex = key.getPatientIndex();
        Patient patient = key.getPatient();
        String description = key.getDescription();

        Appointment nextAppointment = new Appointment(nextStartDateTime, nextEndDateTime, frequency, patientIndex,
                description);
        nextAppointment.setPatient(patient);
        return nextAppointment;
    }

    /**
     * Adds same appointment with the next date time if previous one was deleted and was recurring.
     * @param key
     */
    public void addRecurringAppointment(Appointment key) {
        Appointment nextAppointment = getRecurredAppointment(key);
        while (hasClashingAppointment(nextAppointment)) {
            nextAppointment = getRecurredAppointment(nextAppointment);
        }
        addAppointment(nextAppointment);
    }

    //// util methods
    @Override
    public AppointmentBook deepCopy() {
        try {
            return new JsonSerializableAppointmentBook(this).toModelType();
        } catch (IllegalValueException e) {
            throw new CopyError("Error copying AppointmentBook");
        }
    }

    @Override
    public String toString() {
        return appointments.asUnmodifiableObservableList().size() + " appointments";
        // TODO: refine later
    }

    @Override
    public ObservableList<Appointment> getAppointmentList() {
        return appointments.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentBook // instanceof handles nulls
                && appointments.equals(((AppointmentBook) other).appointments));
    }

    @Override
    public int hashCode() {
        return appointments.hashCode();
    }

}

