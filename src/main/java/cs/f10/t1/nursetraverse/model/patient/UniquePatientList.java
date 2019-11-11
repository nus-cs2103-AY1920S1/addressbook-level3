package cs.f10.t1.nursetraverse.model.patient;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import cs.f10.t1.nursetraverse.commons.util.CollectionUtil;
import cs.f10.t1.nursetraverse.model.patient.exceptions.DuplicatePatientException;
import cs.f10.t1.nursetraverse.model.patient.exceptions.PatientNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of patients that enforces uniqueness between its elements and does not allow nulls.
 * A patient is considered unique by comparing using {@code Patient#isSamePatient(Patient)}. As such, adding and
 * updating of patients uses Patient#isSamePatient(Patient) for equality so as to ensure that the patient being added
 * or updated is unique in terms of identity in the UniquePatientList. However, the removal of a patient uses
 * Patient#equals(Object) so as to ensure that the patient with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Patient#isSamePatient(Patient)
 */
public class UniquePatientList implements Iterable<Patient> {

    private final ObservableList<Patient> internalList = FXCollections.observableArrayList();
    private final ObservableList<Patient> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent patient as the given argument.
     */
    public boolean contains(Patient toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePatient);
    }

    /**
     * Adds a patient to the list.
     * The patient must not already exist in the list.
     */
    public void add(Patient toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePatientException();
        }
        internalList.add(toAdd);
    }

    /**
     * Returns the index of a patient from the list.
     */
    public int indexOf(Patient toFind) {
        requireNonNull(toFind);
        return internalList.indexOf(toFind);
    }

    /**
     * Replaces the patient {@code target} in the list with {@code editedPatient}.
     * {@code target} must exist in the list.
     * The patient identity of {@code editedPatient} must not be the same as another existing patient in the list.
     */
    public void setPatient(Patient target, Patient editedPatient) {
        CollectionUtil.requireAllNonNull(target, editedPatient);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PatientNotFoundException();
        }

        List<Patient> samePatients = getSamePatients(editedPatient);
        if (samePatients.size() > 1) {
            throw new DuplicatePatientException();
        }
        if (!target.isSamePatient(editedPatient) && !samePatients.isEmpty()) {
            throw new DuplicatePatientException();
        }

        internalList.set(index, editedPatient);
    }

    /**
     * Removes the equivalent patient from the list.
     * The patient must exist in the list.
     */
    public void remove(Patient toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PatientNotFoundException();
        }
    }

    public void setPatients(UniquePatientList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code patients}.
     * {@code patients} must not contain duplicate patients.
     */
    public void setPatients(List<Patient> patients) {
        CollectionUtil.requireAllNonNull(patients);
        if (!patientsAreUnique(patients)) {
            throw new DuplicatePatientException();
        }

        internalList.setAll(patients);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Patient> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Patient> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePatientList // instanceof handles nulls
                        && internalList.equals(((UniquePatientList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code patients} contains only unique patients.
     */
    private boolean patientsAreUnique(List<Patient> patients) {
        for (int i = 0; i < patients.size() - 1; i++) {
            for (int j = i + 1; j < patients.size(); j++) {
                if (patients.get(i).isSamePatient(patients.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns a list of patients with the same identity as {@code toCheck}. This is possible because
     * {@link Patient#isSamePatient(Patient)} is not transitive (e.g. toCheck.isSamePatient(b) and
     * toCheck.isSamePatient(c) but !b.isSamePatient(c))
     */
    private ObservableList<Patient> getSamePatients(Patient toCheck) {
        ObservableList<Patient> samePatients = FXCollections.observableArrayList();
        for (Patient patient : internalList) {
            if (patient.isSamePatient(toCheck)) {
                samePatients.add(patient);
            }
        }
        return FXCollections.unmodifiableObservableList(samePatients);
    }

    /**
     * Get a patient by index (Optional object).
     */
    public Optional<Patient> getByIndex(int key) {
        if (key >= 0 && key < internalList.size()) {
            return Optional.of(internalList.get(key));
        }
        return Optional.empty();
    }

    /**
     * Returns size of the list
     */
    public int size() {
        return internalList.size();
    }
}
