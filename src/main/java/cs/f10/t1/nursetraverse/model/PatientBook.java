package cs.f10.t1.nursetraverse.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.commons.exceptions.CopyError;
import cs.f10.t1.nursetraverse.commons.exceptions.IllegalValueException;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.model.patient.UniquePatientList;
import cs.f10.t1.nursetraverse.model.patient.exceptions.PatientHasOngoingVisitException;
import cs.f10.t1.nursetraverse.model.visit.Visit;
import cs.f10.t1.nursetraverse.storage.JsonSerializablePatientBook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePatient comparison)
 */
public class PatientBook implements ReadOnlyPatientBook {

    private static final Pair<Integer, Integer> NO_ONGOING_PATIENT_AND_VISIT_VAL = new Pair<>(-1, -1);

    private final UniquePatientList patients;
    private Pair<Integer, Integer> pairOfOngoingPatAndVisitIndexes = NO_ONGOING_PATIENT_AND_VISIT_VAL;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        patients = new UniquePatientList();
    }

    public PatientBook() {}

    /**
     * Creates an PatientBook using the Patients in the {@code toBeCopied}
     */
    public PatientBook(ReadOnlyPatientBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the patient list with {@code patients}.
     * {@code patients} must not contain duplicate patients.
     */
    public void setPatients(List<Patient> patients) {
        this.patients.setPatients(patients);
    }

    /**
     * Resets the existing data of this {@code PatientBook} with {@code newData}.
     */
    public void resetData(ReadOnlyPatientBook newData) {
        requireNonNull(newData);

        setPatients(newData.getPatientList());
        setPairOfOngoingPatAndVisitIndexes(newData.getIndexPairOfOngoingPatientAndVisit());
    }

    //// patient-level operations

    /**
     * Returns true if a patient with the same identity as {@code patient} exists in the patient book.
     */
    public boolean hasPatient(Patient patient) {
        requireNonNull(patient);
        return patients.contains(patient);
    }

    /**
     * Returns index of patient.
     */
    public int indexOfPatient(Patient patient) {
        requireNonNull(patient);
        return patients.indexOf(patient);
    }

    /**
     * Adds a patient to the patient book.
     * The patient must not already exist in the patient book.
     */
    public void addPatient(Patient p) {
        patients.add(p);
    }

    /**
     * Replaces the given patient {@code target} in the list with {@code editedPatient}.
     * {@code target} must exist in the patient book.
     * The patient identity of {@code editedPatient} must not be the same as another
     * existing patient in the patient book.
     */
    public void setPatient(Patient target, Patient editedPatient) {
        requireNonNull(editedPatient);

        patients.setPatient(target, editedPatient);
    }

    /**
     * Removes {@code key} from this {@code PatientBook}. Also updates currentPatientAndVisit if needed.
     * {@code key} must exist in the patient book and must not have an ongoing visit.
     */
    public void removePatient(Patient key) {
        requireNonNull(key);
        //If no ongoing visit, just remove
        if (pairOfOngoingPatAndVisitIndexes.equals(NO_ONGOING_PATIENT_AND_VISIT_VAL)) {
            patients.remove(key);
        } else {
            Optional<Visit> optionalVisit = getOngoingVisit();
            assert optionalVisit.isPresent();
            Patient currentPatient = optionalVisit.get().getPatient();

            if (currentPatient.equals(key)) {
                //Code should have prevented this from reaching this stage
                throw new PatientHasOngoingVisitException();
            } else {
                //Remove and update
                patients.remove(key);
                setPairOfOngoingPatAndVisitIndexes(new Pair<>(indexOfPatient(currentPatient),
                        pairOfOngoingPatAndVisitIndexes.getValue()));
            }
        }
    }

    public Patient getPatientByIndex(Index index) {
        requireNonNull(index);
        int integerIndex = index.getZeroBased();
        assert integerIndex <= patients.size();

        return patients.getByIndex(integerIndex).get();
    }

    //// util methods
    @Override
    public PatientBook deepCopy() {
        try {
            return new JsonSerializablePatientBook(this).toModelType();
        } catch (IllegalValueException e) {
            throw new CopyError("Error copying PatientBook", e);
        }
    }

    @Override
    public String toString() {
        return patients.asUnmodifiableObservableList().size() + " patients";
        // TODO: refine later
    }

    @Override
    public ObservableList<Patient> getPatientList() {
        return patients.asUnmodifiableObservableList();
    }

    /** Returns an unmodifiable view of patients at the provided indexes */
    public ObservableList<Patient> getPatientListByIndexes(Set<Index> indexes) {
        ObservableList<Patient> patientList = FXCollections.observableArrayList();
        for (Index index : indexes) {
            int integerIndex = index.getZeroBased();
            assert integerIndex <= patients.size();
            patientList.add(getPatientByIndex(index));
        }
        return FXCollections.unmodifiableObservableList(patientList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PatientBook // instanceof handles nulls
                && patients.equals(((PatientBook) other).patients));
    }

    @Override
    public int hashCode() {
        return patients.hashCode();
    }

    /**
     * Get optional pair of current patient and visit if there is an ongoing visit.
     */
    public Optional<Visit> getOngoingVisit() {
        if (pairOfOngoingPatAndVisitIndexes.getKey() == -1 || pairOfOngoingPatAndVisitIndexes.getValue() == -1) {
            return Optional.empty();
        }
        Optional<Patient> patient = patients.getByIndex(pairOfOngoingPatAndVisitIndexes.getKey());
        if (patient.isEmpty()) {
            return Optional.empty();
        }
        return patient.get().getVisitByIndex(pairOfOngoingPatAndVisitIndexes.getValue());
    }

    /**
     * Verifies that the patient and visit indexes can be obtained from the visit i.e.
     * Returns true if the visit can be found in the data.
     */
    public boolean visitIsInData(Visit visit) {
        requireNonNull(visit);
        Patient patient = visit.getPatient();
        int patientIndex = patients.indexOf(patient);
        if (patientIndex <= -1) {
            return false;
        }
        int visitIndex = patient.indexOfVisit(visit);
        return visitIndex > -1;
    }

    /**
     * Record ongoing visit of patient.
     * This will be saved until the visit is finished.
     */
    public void setOngoingVisit(Visit visit) {
        requireNonNull(visit);
        if (!visitIsInData(visit)) {
            throw new IllegalArgumentException();
        }
        setPairOfOngoingPatAndVisitIndexes(new Pair<>(patients.indexOf(visit.getPatient()),
                visit.getPatient().indexOfVisit(visit)));
    }

    /**
     * Unset current patient and visit
     */
    public void unsetOngoingVisit() {
        this.pairOfOngoingPatAndVisitIndexes = NO_ONGOING_PATIENT_AND_VISIT_VAL;
    }

    @Override
    public Pair<Integer, Integer> getIndexPairOfOngoingPatientAndVisit() {
        return pairOfOngoingPatAndVisitIndexes;
    }

    private void setPairOfOngoingPatAndVisitIndexes(Pair<Integer, Integer> pairOfOngoingPatAndVisitIndexes) {
        this.pairOfOngoingPatAndVisitIndexes = pairOfOngoingPatAndVisitIndexes;
    }
}
