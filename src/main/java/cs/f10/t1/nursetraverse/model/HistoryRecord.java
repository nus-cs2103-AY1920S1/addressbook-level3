package cs.f10.t1.nursetraverse.model;

import cs.f10.t1.nursetraverse.logic.commands.MutatorCommand;

/**
 * Record of the command and data of one atomic commit in the command history. It consists of the
 * {@link MutatorCommand} responsible for the commit and the state of the {@link PatientBook} before the commit.
 */
public class HistoryRecord {
    private final MutatorCommand command;
    private final PatientBook patientBook;

    /**
     * Constructs a HistoryRecord representing the specified command and state. The state is deeply copied and
     * decoupled from the given reference.
     *
     * @param command the command that caused the change in state
     * @param patientBook the state before the execution of the command
     */
    public HistoryRecord(MutatorCommand command, PatientBook patientBook) {
        this.command = command;
        this.patientBook = patientBook.deepCopy();
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

    @Override
    public String toString() {
        return command.getClass().getSimpleName() + ", " + patientBook.toString()
                + " " + patientBook.hashCode();
    }
}
