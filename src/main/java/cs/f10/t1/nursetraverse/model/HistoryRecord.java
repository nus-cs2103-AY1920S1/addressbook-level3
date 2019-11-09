//@@author gabrielchao

package cs.f10.t1.nursetraverse.model;

import static cs.f10.t1.nursetraverse.commons.util.CollectionUtil.requireAllNonNull;

import cs.f10.t1.nursetraverse.logic.commands.MutatorCommand;

/**
 * Record of the command and data of one atomic commit in the command history. It consists of the
 * {@link MutatorCommand} responsible for the commit and the state of the {@link PatientBook} and
 * {@link AppointmentBook} before the commit.
 */
public class HistoryRecord {
    private final MutatorCommand command;
    private final PatientBook patientBook;
    private final AppointmentBook appointmentBook;

    /**
     * Constructs a HistoryRecord representing the specified command and state. The state is deeply copied and
     * decoupled from the given reference.
     *
     * @param command the command that caused the change in state
     * @param patientBook the state before the execution of the command
     * @param appointmentBook the state before the execution of the command
     */
    public HistoryRecord(MutatorCommand command, PatientBook patientBook, AppointmentBook appointmentBook) {
        requireAllNonNull(command, patientBook, appointmentBook);
        this.command = command;
        this.patientBook = patientBook.deepCopy();
        this.appointmentBook = appointmentBook.deepCopy();
    }

    /** Returns the command stored in this record */
    public MutatorCommand getCommand() {
        return command;
    }

    /**
     * Returns a read-only reference to the state stored in this record. This method is preferred over
     * {@link #getCopyOfPatientBook()} if the state does not need to be modified as it avoids copying the entire state.
     */
    public ReadOnlyPatientBook getReadOnlyPatientBook() {
        return patientBook;
    }

    /**
     * Returns a deep copy of the state stored in this record. If the state does not need to be modified, it is
     * recommended to use {@link #getReadOnlyPatientBook()} instead.
     */
    public PatientBook getCopyOfPatientBook() {
        return patientBook.deepCopy();
    }

    /**
     * Returns a read-only reference to the state stored in this record. This method is preferred over
     * {@link #getCopyOfAppointmentBook()} if the state does not need to be modified as it avoids copying the entire
     * state.
     */
    public ReadOnlyAppointmentBook getReadOnlyAppointmentBook() {
        return appointmentBook;
    }

    /**
     * Returns a deep copy of the state stored in this record. If the state does not need to be modified, it is
     * recommended to use {@link #getReadOnlyAppointmentBook()} instead.
     */
    public AppointmentBook getCopyOfAppointmentBook() {
        return appointmentBook.deepCopy();
    }

    @Override
    public String toString() {
        return command.getClass().getSimpleName() + ", " + patientBook.toString()
                + " " + patientBook.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof HistoryRecord)) {
            return false;
        }

        // state check
        HistoryRecord other = (HistoryRecord) obj;
        return command.equals(other.command)
                && patientBook.equals(other.patientBook)
                && appointmentBook.equals(other.appointmentBook);
    }
}
