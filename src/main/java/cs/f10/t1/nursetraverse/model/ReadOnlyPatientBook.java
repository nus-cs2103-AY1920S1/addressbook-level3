package cs.f10.t1.nursetraverse.model;

import cs.f10.t1.nursetraverse.model.patient.Patient;
import javafx.collections.ObservableList;
import javafx.util.Pair;

/**
 * Unmodifiable view of an patient book
 */
public interface ReadOnlyPatientBook {

    /**
     * Returns an unmodifiable view of the patients list.
     * This list will not contain any duplicate patients.
     */
    ObservableList<Patient> getPatientList();

    /**
     * Returns a {@code Pair<Integer, Integer>} containing the
     * PatientIndex and AppointmentIndex respectively to indicate the ongoing visit.
     * If there is no ongoing visit, returns {@code new Pair<>(-1,-1)}.
     */
    Pair<Integer, Integer> getIndexPairOfOngoingPatientAndVisit();

    /**
     * Returns a deep copy of this {@code readOnlyPatientBook}. Changes made to the copy will not affect this object
     * and vice versa.
     */
    ReadOnlyPatientBook deepCopy();
}
